package com.jquery.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jquery.dto.NoticeAttachVO;

public class NoticeAttachDAOImpl implements NoticeAttachDAO{
	
		
	@Override
	public List<NoticeAttachVO> selectNoticeAttachesByNno(SqlSession session, int nno) throws SQLException {
		List<NoticeAttachVO> noticeAttachList=session.selectList("NoticeAttach-Mapper.selectNoticeAttachByNno",nno);
		return noticeAttachList;
	}

	@Override
	public NoticeAttachVO selectNoticeAttachByNano(SqlSession session, int nano) throws SQLException {
		NoticeAttachVO noticeAttach=session.selectOne("NoticeAttach-Mapper.selectNoticeAttachByNano",nano);
		return noticeAttach;
	}

	@Override
	public void insertNoticeAttach(SqlSession session, NoticeAttachVO noticeAttach) throws SQLException {
		session.update("NoticeAttach-Mapper.insertNoticeAttach",noticeAttach);
	}

	@Override
	public void deleteNoticeAttach(SqlSession session, int nano) throws SQLException {
		session.update("NoticeAttach-Mapper.deleteNoticeAttach",nano);
	}

	@Override
	public void deleteAllNoticeAttach(SqlSession session, int nno) throws SQLException {
		session.update("NoticeAttach-Mapper.deleteAllNoticeAttach",nno);			
	}

}
