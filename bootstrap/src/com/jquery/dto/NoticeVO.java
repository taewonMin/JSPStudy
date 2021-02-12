package com.jquery.dto;

import java.util.Date;
import java.util.List;

public class NoticeVO {
	private int nno;
	private String title;
	private String writer;
	private String content;
	private Date regdate;
	private int viewcnt;
	private int point;
	private Date startdate;
	private Date enddate;
	private Date updatedate;
	private String dist;
	
	List<NoticeAttachVO> noticeAttachList; //첨부파일
	
	
	public int getNno() {
		return nno;
	}
	public void setNno(int nno) {
		this.nno = nno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getViewcnt() {
		return viewcnt;
	}
	public void setViewcnt(int viewcnt) {
		this.viewcnt = viewcnt;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public String getDist() {
		return dist;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}
	public List<NoticeAttachVO> getNoticeAttachList() {
		return noticeAttachList;
	}
	public void setNoticeAttachList(List<NoticeAttachVO> noticeAttachList) {
		this.noticeAttachList = noticeAttachList;
	}
	@Override
	public String toString() {
		return "NoticeVO [nno=" + nno + ", title=" + title + ", writer=" + writer + ", content=" + content
				+ ", regdate=" + regdate + ", viewcnt=" + viewcnt + ", point=" + point + ", startdate=" + startdate
				+ ", enddate=" + enddate + ", updatedate=" + updatedate + ", dist=" + dist + ", noticeAttachList="
				+ noticeAttachList + "]";
	}
	
	
	
}
