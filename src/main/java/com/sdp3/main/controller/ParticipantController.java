package com.sdp3.main.controller;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sdp3.main.entity.Course;
import com.sdp3.main.entity.User;
import com.sdp3.main.entity.quiz.Question;
import com.sdp3.main.entity.quiz.Quiz;
import com.sdp3.main.helper.Message;
import com.sdp3.main.repository.CourseRepo;
import com.sdp3.main.service.QuestionService;
import com.sdp3.main.service.QuizService;
import com.sdp3.main.service.UserService;

@Controller
@RequestParameter("/teacher/view/course/{id}/tab/participants")

public class ParticipantController{

    @Autowired
	private UserService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/participantSave")
	public String ParticipantSave(@ModelAttribute("User") User user,Model model,HttpSession session,
			RedirectAttributes ra) {
		
		User userExists = service.getEmail(user.getEmail());
		String role = user.getRole();
		
		if (userExists != null) {
			ra.addAttribute("user",new User());
			ra.addAttribute("title","This is Register page");
			return "redirect:/teacher/view/course/{cid}/tab/participants";
		}else {
		
			try {
				
				if(role.equals("student")) {
					user.setRole("ROLE_USER");
				}else {
					user.setRole("ROLE_ADMIN");
				}
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				this.service.saveUser(user);
				ra.addAttribute("user",new User());
				
				return "redirect:/teacher/view/course/{cid}/tab/participants";
			}catch(Exception e) {
				ra.addAttribute("user",new User());
				return "redirect:/teacher/view/course/{cid}/tab/participants";
			}
		}
		
	}

    @GetMapping("/addParticipant")
    public String AddParticipant(Model model){
        model.addAttribute("tilte","Adding Participant");
        return "addParticipant";
    }

}