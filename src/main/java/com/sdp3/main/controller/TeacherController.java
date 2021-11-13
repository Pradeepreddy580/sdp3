package com.sdp3.main.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sdp3.main.entity.Course;
import com.sdp3.main.entity.CourseParticipantsCalculation;
import com.sdp3.main.entity.CourseParticipantsList;
import com.sdp3.main.entity.Enroll;
import com.sdp3.main.entity.Result;
import com.sdp3.main.entity.User;
import com.sdp3.main.entity.quiz.Quiz;
import com.sdp3.main.helper.Message;
import com.sdp3.main.repository.CourseRepo;
import com.sdp3.main.repository.EnrollRepo;
import com.sdp3.main.repository.ResultRepo;
import com.sdp3.main.service.CourseService;
import com.sdp3.main.service.QuizService;
import com.sdp3.main.service.UserService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	@Autowired
	private UserService service;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseRepo repo;
	
	@Autowired
	private ResultRepo rrepo;
	
	@Autowired
	private EnrollRepo erepo;

	@Autowired
	private QuizService qservice;
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		User user = service.getEmail(userName);
		model.addAttribute("user",user);
	}
	
	@GetMapping("/teacherProfile")
	public String userProfile(Model model){
		model.addAttribute("title","This is Profile");
		return "teacher/teacher_profile";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title","This is Dashboard");
		String username = principal.getName();
		
		List<Course> course = this.repo.getCourses(username);
		
		model.addAttribute("course",course);
		
		return "teacher/teacher_dashboard";
	}
	@RequestMapping("/courses")
	public String courses(Model model, Principal principal) {
		model.addAttribute("title","This is Courses Page");
		String username = principal.getName();
		
		List<Course> course = this.repo.getCourses(username);
		
		model.addAttribute("course",course);
		return "teacher/teacher_courses";
	}
	@RequestMapping("/createCourseSave")
	public String createCourseSave(@ModelAttribute("Course") Course course,Model model,HttpSession session) {
		
		try {
			this.courseService.saveCourse(course);
			model.addAttribute("course",new Course());
			model.addAttribute("title","This is Course page");
			session.setAttribute("message", new Message("Successfull Created Course","alert-success"));
			return "teacher/teacher_courses";
		}catch(Exception e) {
			model.addAttribute("Course",new Course());
			model.addAttribute("title","Create Course");
			session.setAttribute("message", new Message("Course Creation Unsuccessfull"+e.getMessage(),"alert-danger"));
			return "teacher/createCourse";
		}
		
	}
	@RequestMapping("/createCourse")
	public String createCourses(Model model) {
		
		model.addAttribute("title","Creating Course");
		return "teacher/createCourse";
	}
	
	@GetMapping("/view/course/{id}")
	public String viewCourse(@PathVariable("id") Long id,Model model) {
		Course course = repo.findByCourseCode(id);
		model.addAttribute(course);
		model.addAttribute("title",course.getCourseName());
		return "teacher/view_tab/about";
	}
	
	@GetMapping("/view/course/{id}/tab/{tabid}")
    public String tab(@PathVariable("id") Long id, 
    		  @PathVariable("tabid") String tab,Model model) {
		Course course = repo.findByCourseCode(id);
		model.addAttribute(course);
		model.addAttribute("title",course.getCourseName());
        if (tab.equals("about")){
            return  "teacher/view_tab/about";
        }
        else  if (tab.equals("material")){
            return  "teacher/view_tab/material";
        }
        else  if (tab.equals("quizes")){
        	Set<Quiz> quiz = this.qservice.getQuizesByCourseCode(id);
    		model.addAttribute("quiz",quiz);
            return  "teacher/view_tab/quizes";
        }
        else  if (tab.equals("grades")){
        	List<Result> result = this.rrepo.findAllByUserNameAndCourse(id);
        	
        	model.addAttribute("result",result);
            return  "teacher/view_tab/grades";
        }
        else  if (tab.equals("participants")){
        	Course course1 = this.courseService.getAllCourseById(id);
        	List<Enroll> usersEnrolledForParticularCourse = erepo.findAllEnrolledByCourseId(course1.getId());
        	
        	model.addAttribute("participantslist",usersEnrolledForParticularCourse);
            return  "teacher/view_tab/participants";
        }
        else{
            return  "teacher/view_tab/assignments";
        }
    }
}
