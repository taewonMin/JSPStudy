package com.jquery.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.jquery.command.SearchCriteria;
import com.jquery.dto.NoticeVO;

public class NoticeDAOImpl implements NoticeDAO{

	
	@Override
	public List<NoticeVO> selectNoticeCriteria(SqlSession session,SearchCriteria cri) throws SQLException {
	
		int offset=cri.getPageStartRowNum();
		int limit=cri.getPerPageNum();		
		RowBounds rowBounds=new RowBounds(offset,limit);		
		
		List<NoticeVO> noticeList=session.selectList("Notice-Mapper.selectSearchNoticeList",cri,rowBounds);
		
		return noticeList;
	}
	
	@Override
	public int selectNoticeCriteriaTotalCount(SqlSession session,SearchCriteria cri) throws SQLException {	
		int count=session.selectOne("Notice-Mapper.selectSearchNoticeListCount",cri);
		return count;
	}
	
	@Override
	public NoticeVO selectNoticeByNno(SqlSession session,int nno) throws SQLException {
		NoticeVO notice=session.selectOne("Notice-Mapper.selectNoticeByNno",nno);
		return notice;
	}
	
	@Override
	public void insertNotice(SqlSession session,NoticeVO notice) throws SQLException {	
		session.update("Notice-Mapper.insertNotice",notice);
	}
	
	@Override
	public void updateNotice(SqlSession session, NoticeVO notice) throws SQLException {
		session.update("Notice-Mapper.updateNotice",notice);		
	}
	
	@Override
	public void deleteNotice(SqlSession session,int nno) throws SQLException {
		session.update("Notice-Mapper.deleteNotice",nno);
	}
	
	@Override
	public void increaseViewCnt(SqlSession session, int nno) throws SQLException {
		session.update("Notice-Mapper.increaseViewCnt",nno);
	}
	
	@Override
	public int selectNoticeSeqNext(SqlSession session) throws SQLException {
		int seq_num=session.selectOne("Notice-Mapper.selectNoticeSeqNext");
		return seq_num;
	}


	
}
