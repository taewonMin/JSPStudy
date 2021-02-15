package com.jquery.service;

import java.sql.SQLException;
import java.util.Map;

import com.jquery.command.SearchCriteria;
import com.jquery.dto.NoticeAttachVO;
import com.jquery.dto.NoticeVO;

public interface NoticeService {
	
	// 목록조회	
	Map<String,Object> getNoticeList(SearchCriteria cri)throws SQLException;
	
	// 상세보기
	NoticeVO getNotice(int nno)throws SQLException;	
	NoticeVO getNoticeForModify(int nno)throws SQLException;
	
	// 등록
	void regist(NoticeVO notice)throws SQLException;
		
	// 수정
	void modify(NoticeVO notice)throws SQLException;
	
	// 삭제
	void remove(int nno)throws SQLException;
	
	
//	// 첨부파일 가져오기
//	List<AttachVO> getAttachList(int bno)throws SQLException;
	NoticeAttachVO getNoticeAttachVO(int nano)throws SQLException;
	void removeNoticeAttachNano(int nano)throws SQLException;
}




