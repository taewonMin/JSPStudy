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
		SqlSession session = sqlSessionFactory.openSession();

		try {
			// 현재 page 번호에 맞는 리스트를 perPageNum 개수 만큼 가져오기.
			List<NoticeVO> noticeList = noticeDAO.selectNoticeCriteria(session, cri);

			// 전체 notice 개수
			int totalCount = noticeDAO.selectNoticeCriteriaTotalCount(session, cri);

			// PageMaker 생성.
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(totalCount);

			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("noticeList", noticeList);
			dataMap.put("pageMaker", pageMaker);
			
			return dataMap;
		} finally {
			session.close();
		}
	}
	
	@Override
	public NoticeVO getNoticeForModify(int nno) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession(false);
		try {
			NoticeVO notice = noticeDAO.selectNoticeByNno(session, nno);
			List<NoticeAttachVO> attachList = noticeAttachDAO.selectNoticeAttachesByNno(session, nno);
			notice.setNoticeAttachList(attachList);
			session.commit();
			return notice;
		} catch (SQLException e) {
			session.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public NoticeVO getNotice(int nno) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession(false);
		try {
			NoticeVO notice = noticeDAO.selectNoticeByNno(session, nno);
			List<NoticeAttachVO> attachList = noticeAttachDAO.selectNoticeAttachesByNno(session, nno);
			notice.setNoticeAttachList(attachList);
			
			noticeDAO.increaseViewCnt(session, nno);
			session.commit();
			return notice;
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

	@Override
	public void modify(NoticeVO notice) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession(false);
		try {
			noticeDAO.updateNotice(session, notice);

			for (NoticeAttachVO attach : notice.getNoticeAttachList()) {
				attach.setNno(notice.getNno());
				attach.setAttacher(notice.getWriter());
				noticeAttachDAO.insertNoticeAttach(session, attach);
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
	
	@Override
	public void remove(int nno) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession(false);
		try {
			noticeDAO.deleteNotice(session, nno);
			session.commit();
		} catch (SQLException e) {
			session.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}
	
	@Override
	public NoticeAttachVO getNoticeAttachVO(int nano) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession(false);
		try {
			NoticeAttachVO attach = noticeAttachDAO.selectNoticeAttachByNano(session, nano);
			return attach;
		} catch (SQLException e) {
			session.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public void removeNoticeAttachNano(int nano) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession(false);
		try {
			noticeAttachDAO.deleteNoticeAttach(session, nano);
			
			session.commit();
		}catch(SQLException e) {
			session.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}
}
