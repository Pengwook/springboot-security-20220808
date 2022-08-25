package com.study.security_hyeonwook.service.notice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.study.security_hyeonwook.domain.notice.Notice;
import com.study.security_hyeonwook.domain.notice.NoticeFile;
import com.study.security_hyeonwook.domain.notice.NoticeRepository;
import com.study.security_hyeonwook.web.dto.notice.AddNoticeReqDto;
import com.study.security_hyeonwook.web.dto.notice.GetNoticeListResponseDto;
import com.study.security_hyeonwook.web.dto.notice.GetNoticeResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{
	
	@Value("${file.path}")	// yml에 file.path의 값을 주입시켜줌
	private String filePath;
	
	private final NoticeRepository noticeRepository;
	
	@Override
	public List<GetNoticeListResponseDto> getNoitceList(int page, String searchFlag, String searchValue) throws Exception {
		int index = (page - 1) * 10;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("search_flag", searchFlag);
		map.put("search_value", searchValue == null ? "" : searchValue);
		
		List<GetNoticeListResponseDto> list = new ArrayList<GetNoticeListResponseDto>();
		
		noticeRepository.getNoticeList(map).forEach(notice -> {
			list.add(notice.toListDto());
		});
		
		return list;
	}
	
	@Override
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception {
		Predicate<String> predicate = (fileName) -> !fileName.isBlank();
		
		Notice notice = null;
		
		notice = Notice.builder()
				.notice_title(addNoticeReqDto.getNoticeTitle())
				.user_code(addNoticeReqDto.getUserCode())
				.notice_content(addNoticeReqDto.getIr1())
				.build();
		
		noticeRepository.saveNotice(notice);	
		
		if(predicate.test(addNoticeReqDto.getFile().get(0).getOriginalFilename())) {
			List<NoticeFile> noticeFiles = new ArrayList<NoticeFile>();;
		
			for(MultipartFile file : addNoticeReqDto.getFile()) {
				String originalFilename = file.getOriginalFilename();
				String tempFilename = UUID.randomUUID().toString().replace("-", "") + "_" + originalFilename;	// 랜덤한 문자열을 만들어줌, 절대 겹치지않는 고유한 키값
				log.info(tempFilename);
				
				Path uploadPath = Paths.get(filePath, "notice/" + tempFilename);
				
				File f = new File(filePath + "notice");	// 파일객체 만듦
				if(!f.exists()) {	// 만약 파일 객체가 존재하지않으면
					f.mkdirs();		// 경로를 만들어라, make directory
				}
		
				try {
					Files.write(uploadPath, file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				noticeFiles.add(NoticeFile.builder().notice_code(notice.getNotice_code()).file_name(tempFilename).build());
			}
			
			noticeRepository.saveNoticeFiles(noticeFiles);
			
		}
		
		
		return notice.getNotice_code();
	}
	
	@Override
	public GetNoticeResponseDto getNotice(String flag, int noticeCode) throws Exception {
		GetNoticeResponseDto getNoticeResponseDto = null;
		
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("flag", flag);
		reqMap.put("notice_code", noticeCode);
		
		noticeRepository.countIncrement(reqMap);
		List<Notice> notices = noticeRepository.getNotice(reqMap);
		if(!notices.isEmpty()) {
			List<Map<String, Object>> downloadFiles = new ArrayList<Map<String,Object>>();
			notices.forEach(notice -> {
				Map<String, Object> fileMap = new HashMap<String, Object>();
				String fileName = notice.getFile_name();
				if(fileName != null) {
					fileMap.put("fileCode", notice.getFile_code());
					fileMap.put("fileOriginName", fileName.substring(fileName.indexOf("_") + 1));	// - 다음부터 짜르기
					fileMap.put("fileTempName", fileName);
				}
				
				downloadFiles.add(fileMap);
			});
			
			Notice firstNotice = notices.get(0);
			
			getNoticeResponseDto = GetNoticeResponseDto.builder()
					.noticeCode(firstNotice.getNotice_code())
					.noticeTitle(firstNotice.getNotice_title())
					.userCode(firstNotice.getUser_code())
					.userId(firstNotice.getUser_id())
					.createDate(firstNotice.getCreate_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
					.noticeCount(firstNotice.getNotice_count())
					.noticeContent(firstNotice.getNotice_content())
					.downloadFiles(downloadFiles)
					.build();
		}
		
		return getNoticeResponseDto;
	}
}
