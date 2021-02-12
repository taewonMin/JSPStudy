package com.jquery.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jquery.command.SearchCriteria;
import com.jquery.dto.BoardVO;
import com.jquery.dto.NoticeVO;

public interface NoticeDAO {

	List<NoticeVO> selectNoticeCriteria(SqlSession session, SearchCriteria cri) throws SQLException;

	int selectNoticeCriteriaTotalCount(SqlSession session,SearchCriteria cri) throws SQLException;

//	BoardVO selectNoticeByNno(SqlSession session,int nno) throws SQLException;

	void insertNotice(SqlSession session,NoticeVO notice) throws SQLException;

//	void updateNotice(SqlSession session,NoticeVO notice) throws SQLException;

//	void deleteNotice(SqlSession session,int nno) throws SQLException;

	// viewcnt 증가
//	void increaseViewCnt(SqlSession session,int nno) throws SQLException;

	// notice_seq.nextval 가져오기
	int selectNoticeSeqNext(SqlSession session) throws SQLException;

}
