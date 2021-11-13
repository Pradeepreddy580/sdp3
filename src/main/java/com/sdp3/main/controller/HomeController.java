package com.sdp3.main.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sdp3.main.entity.User;
import com.sdp3.main.helper.Message;
import com.sdp3.main.service.UserService;

@Controller
public class HomeController {
	
	
	@Autowired
	private UserService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("/")
	public String homePage(Model model) {
		model.addAttribute("title","This is home page");
		return "index";
	}
	
	
	@RequestMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("title","This is Register page");
		model.addAttribute("user",new User());
		return "register";
	}
	
	@RequestMapping("/signin")
	public String loginPage(Model model) {
		model.addAttribute("title","This is Login page");
		return "login";
	}
	
	@PostMapping("/registerUser")
	public String registerUser(@ModelAttribute("User") User user,Model model,HttpSession session,
			RedirectAttributes ra) {
		
		User userExists = service.getEmail(user.getEmail());
		String role = user.getRole();
		
		if (userExists != null) {
			ra.addAttribute("user",new User());
			ra.addAttribute("title","This is Register page");
			ra.addFlashAttribute("message", "Failed");
			 ra.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/register";
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
				ra.addAttribute("title","This is Register page");
				ra.addFlashAttribute("flag","showAlert");
				return "redirect:/login";
			}catch(Exception e) {
				ra.addAttribute("user",new User());
				ra.addAttribute("title","This is Register page");
				ra.addFlashAttribute("flag","showAlert");
				return "redirect:/register";
			}
		}
		
	}
	
}
