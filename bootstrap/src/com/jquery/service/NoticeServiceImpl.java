package com.jquery.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.jquery.command.PageMaker;
import com.jquery.command.SearchCriteria;
import com.jquery.dao.NoticeAttachDAO;
import com.jquery.dao.NoticeAttachDAOImpl;
import com.jquery.dao.NoticeDAO;
import com.jquery.dao.NoticeDAOImpl;
import com.jquery.dto.NoticeAttachVO;
import com.jquery.dto.NoticeVO;
import com.jquery.mybatis.OracleIBatisSqlSessionFactory;

public class NoticeServiceImpl implements NoticeService {

	private static NoticeServiceImpl instance; 

	private NoticeServiceImpl() {
	}

	public static NoticeServiceImpl getInstance() {
		if(instance == null) {
			instance = new NoticeServiceImpl();
		}
		return instance;
	}

	private SqlSessionFactory sqlSessionFactory = OracleIBatisSqlSessionFactory.getSqlSessionFactory();
	public void setSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	private NoticeDAO noticeDAO = new NoticeDAOImpl();
	public void setNoticeDAO(NoticeDAO noticeDAO) {
		this.noticeDAO = noticeDAO;
	}
	private NoticeAttachDAO noticeAttachDAO = new NoticeAttachDAOImpl();
	public void setNoticeAttachDAO(NoticeAttachDAO noticeAttachDAO) {
		this.noticeAttachDAO = noticeAttachDAO;
	}


	@Override
	public Map<String, Object> getNoticeList(SearchCriteria cri) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession(false);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		try {
			// 현재 page 번호에 맞는 리스트를 perPageNum 개수 만큼 가져오기.
			List<NoticeVO> noticeList = noticeDAO.selectNoticeCriteria(session, cri);

			// 전체 notice 개수
			int totalCount = noticeDAO.selectNoticeCriteriaTotalCount(session, cri);

			// PageMaker 생성.
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(totalCount);

			dataMap.put("noticeList", noticeList);
			dataMap.put("pageMaker", pageMaker);
			
			session.commit();
			
			return dataMap;
		} catch (SQLException e) {
			session.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}
	
	@Override
	public void regist(NoticeVO notice) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession(false);
		try {
			int nno = noticeDAO.selectNoticeSeqNext(session);

			notice.setNno(nno);

			noticeDAO.insertNotice(session, notice);

			for (NoticeAttachVO noticeAttach : notice.getNoticeAttachList()) {
				noticeAttach.setNno(nno);
				noticeAttach.setAttacher(notice.getWriter());
				noticeAttachDAO.insertNoticeAttach(session, noticeAttach);
			}
			session.commit();
		} catch (SQLException e) {
			session.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

}
