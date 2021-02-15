package com.jquery.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jquery.dto.NoticeAttachVO;

public interface NoticeAttachDAO {
	
	public List<NoticeAttachVO> selectNoticeAttachesByNno(SqlSession session,int nno)throws SQLException;
	public NoticeAttachVO selectNoticeAttachByNano(SqlSession session,int nano)throws SQLException;
	
	public void insertNoticeAttach(SqlSession session,NoticeAttachVO noticeAttach) throws SQLException;

	public void deleteNoticeAttach(SqlSession session,int nano) throws SQLException;

	public void deleteAllNoticeAttach(SqlSession session,int nno)throws SQLException;
}






