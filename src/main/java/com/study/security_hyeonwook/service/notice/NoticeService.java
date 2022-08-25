package com.study.security_hyeonwook.service.notice;

import java.util.List;

import com.study.security_hyeonwook.web.dto.notice.AddNoticeReqDto;
import com.study.security_hyeonwook.web.dto.notice.GetNoticeListResponseDto;
import com.study.security_hyeonwook.web.dto.notice.GetNoticeResponseDto;

public interface NoticeService { 
	
	public List<GetNoticeListResponseDto> getNoitceList(int page, String searchFlag, String seachValue) throws Exception;
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception;
	public GetNoticeResponseDto getNotice(String flag, int noticeCode) throws Exception;
}
