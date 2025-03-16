package com.example.Contact_manager_web;

import com.example.Contact_manager_web.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContactManagerWebApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;

		@Test
		void sendEmailTest() {
			emailService.sendEmail("siddharthsingh71997@gmail.com",
					"Just checking this functionality",
					"This is Contact Manager project");
		}

}
