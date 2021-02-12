package com.jquery.dto;

import java.util.Date;

public class NoticeAttachVO {
	
	private int nano;
	private String uploadpath;
	private String filename;
	private int nno;
	private String filetype;
	private String attacher;
	private Date regdate;
	private String uuid;
	
	
	public int getNano() {
		return nano;
	}
	public void setNano(int nano) {
		this.nano = nano;
	}
	public String getUploadpath() {
		return uploadpath;
	}
	public void setUploadpath(String uploadpath) {
		this.uploadpath = uploadpath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getNno() {
		return nno;
	}
	public void setNno(int nno) {
		this.nno = nno;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getAttacher() {
		return attacher;
	}
	public void setAttacher(String attacher) {
		this.attacher = attacher;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public String toString() {
		return "NoticeAttachVO [nano=" + nano + ", uploadpath=" + uploadpath + ", filename=" + filename + ", nno=" + nno
				+ ", filetype=" + filetype + ", attacher=" + attacher + ", regdate=" + regdate + ", uuid=" + uuid + "]";
	}
	
}
