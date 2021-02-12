package com.jquery.service;

import java.sql.SQLException;
import java.util.Map;

import com.jquery.command.SearchCriteria;
import com.jquery.dto.NoticeVO;

public interface NoticeService {
	
	// 목록조회	
	Map<String,Object> getNoticeList(SearchCriteria cri)throws SQLException;
	
	// 상세보기
//	BoardVO getBoard(int bno)throws SQLException;	
//	BoardVO getBoardForModify(int bno)throws SQLException;
//	
	// 등록
	void regist(NoticeVO notice)throws SQLException;
//		
//	// 수정
//	void modify(BoardVO board)throws SQLException;
//	
//	// 삭제
//	void remove(int bno)throws SQLException;
//	
//	
//	// 첨부파일 가져오기
//	List<AttachVO> getAttachList(int bno)throws SQLException;
//	AttachVO getAttachVO(int ano)throws SQLException;
//	void removeAttachAno(int ano)throws SQLException;
}




