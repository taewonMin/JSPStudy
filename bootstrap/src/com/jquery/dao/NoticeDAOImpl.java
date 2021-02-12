package com.jquery.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.jquery.command.SearchCriteria;
import com.jquery.dto.BoardVO;
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
	
}
