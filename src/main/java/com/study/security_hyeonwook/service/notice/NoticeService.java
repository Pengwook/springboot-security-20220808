package com.study.security_hyeonwook.service.notice;

import com.study.security_hyeonwook.web.dto.notice.AddNoticeReqDto;

public interface NoticeService { 
	
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception;
	
}
