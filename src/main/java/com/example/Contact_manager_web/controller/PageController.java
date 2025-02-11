package com.example.Contact_manager_web.controller;

import com.example.Contact_manager_web.Forms.UserForm;
import com.example.Contact_manager_web.entities.User;
import com.example.Contact_manager_web.helper.Message;
import com.example.Contact_manager_web.helper.MessageType;
import com.example.Contact_manager_web.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String main(){
		return "index";
	}


	@GetMapping("/home")
	public String home(Model model) {
//		model.addAttribute("name", "hello");
//		model.addAttribute("Work", "Smart Contact manager");
//		model.addAttribute("youtubeVedio", "https://www.youtube.com/watch?v=H0Qzvii2ZrI&list=PL7DBaibuDD9P5yRyq_Oyn-wuYpBayz_0h&index=5");
		return "index";
	}

//About Page
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("isLogin", false);
		return "about";
	}

	// Services page
	@GetMapping("/services")
	public String services() {
		return "services";
	}

	// Contact Page 
	@GetMapping("/contact")
	public String contact(){
		return "contact";
	}


	// Login Page 
	@GetMapping("/login")
	public String login(){
		return "login";
	}

//	@GetMapping("/login")
//	public String loginpost(){
//		return "login";
//	}


	// SignUp Page
	@GetMapping("/register")
	public String register(Model model){
		UserForm userForm = new UserForm();
		userForm.setName(userForm.getName());
		userForm.setAbout(userForm.getAbout());
		userForm.setEmail(userForm.getEmail());
		userForm.setPhoneNumber(userForm.getPhoneNumber());
		userForm.setPassword(userForm.getPassword());
		model.addAttribute("userForm", userForm);

		return "register";
	}

// Processisng Register Page
	@PostMapping("do_register")
	public String processingRegister(@Valid @ModelAttribute  UserForm userForm, BindingResult bindingResult,  HttpSession httpSession){
//		System.out.println("Register Processing!!!!");
//		System.out.println(userForm);
//		User user = User.builder()
//				.name(userForm.getName())
//				.email(userForm.getEmail())
//				.password(userForm.getPassword())
//				.about(userForm.getAbout())
//				.phoneNumber(userForm.getPhoneNumber())
//				.profilePic("https://www.vecteezy.com/png/20911740-user-profile-icon-profile-avatar-user-icon-male-icon-face-icon-profile-icon")
//				.build();
//				User savedUser =  userService.saveUser(user);
//				System.out.println("Saved User " );



		// validation data
		if (bindingResult.hasErrors()){
			return "register";
		}
			User user = new User();
			user.setName(userForm.getName());
			user.setEmail(userForm.getEmail());
			user.setPassword(userForm.getPassword());
			user.setAbout(userForm.getAbout());
			user.setPhoneNumber(userForm.getPhoneNumber());
			user.setProfilePic("https://www.vecteezy.com/png/20911740-user-profile-icon-profile-avatar-user-icon-male-icon-face-icon-profile-icon");

			User savedUser = userService.saveUser(user);
			System.out.println("User Saved Into DataBase!!!!!!!!!");

			// Add message
			Message message = Message.builder()
					.content("Registration Successful")
					.type(MessageType.yellow)  // Set the type (you can choose from your enum)
					.build();
			httpSession.setAttribute("message" , message);

				return "redirect:/register";
	}

}
