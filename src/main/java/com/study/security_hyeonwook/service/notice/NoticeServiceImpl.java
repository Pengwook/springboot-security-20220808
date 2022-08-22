package com.study.security_hyeonwook.service.notice;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.study.security_hyeonwook.web.dto.notice.AddNoticeReqDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Value("${file.path}")	// yml에 file.path의 값을 주입시켜줌
	private String filePath;
	
	@Override
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception {
		String originalFilename = addNoticeReqDto.getFile().get(0).getOriginalFilename();
		
		if(originalFilename.isBlank()) {
			
		}else {
			String tempFilename = UUID.randomUUID().toString().replace("-", "") + "_" + originalFilename;	// 랜덤한 문자열을 만들어줌, 절대 겹치지않는 고유한 키값
			log.info(tempFilename);
		}
		
		
		return 0;
	}
}
