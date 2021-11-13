package com.sdp3.main.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Enroll {
	@Id
	@GeneratedValue
	private long eid;
	
	private int course_id;
	
	private int user_id;

    private Date joined;
	private String accessCodeUsed;
	
	private String studentName;
	private String stduentEmail;
	
	
	
    public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStduentEmail() {
		return stduentEmail;
	}
	public void setStduentEmail(String stduentEmail) {
		this.stduentEmail = stduentEmail;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public long getEid() {
		return eid;
	}
	public void setEid(long eid) {
		this.eid = eid;
	}
	
	public Date getJoined() {
		return joined;
	}
	public void setJoined(Date joined) {
		this.joined = joined;
	}
	public String getAccessCodeUsed() {
		return accessCodeUsed;
	}
	public void setAccessCodeUsed(String accessCodeUsed) {
		this.accessCodeUsed = accessCodeUsed;
	}
	
	
}
