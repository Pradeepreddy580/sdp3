package com.sdp3.main.entity;

public class CourseParticipantsCalculation {
	
	private int course_id;
	private long userCount;
	public CourseParticipantsCalculation(int course_id, long userCount) {
		super();
		this.course_id = course_id;
		this.userCount = userCount;
	}
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	public long getUserCount() {
		return userCount;
	}
	public void setUserCount(long userCount) {
		this.userCount = userCount;
	}
	
}
