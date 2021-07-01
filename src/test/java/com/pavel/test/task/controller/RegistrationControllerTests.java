package com.pavel.test.task.controller;

import org.junit.Test; 

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavel.test.task.entity.User;
import com.pavel.test.task.repo.UserRepository;
import com.pavel.test.task.service.UserService;
import com.pavel.test.task.web.controller.RegistrationController;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationControllerTests {

	private MockMvc mockMvc;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private RegistrationController Rc;
	
	@Before
	public void setUp() {
		 mockMvc = MockMvcBuilders.standaloneSetup(Rc)
	                .build();
	}
	
	@Test
	public void testRegistration() throws Exception {
		 mockMvc.perform(get("/registration"))
		 .andExpect(status().isOk());
	}

	@Test
	public void testAddUser() throws Exception {
		User user = new User(5L, "John", "password", "john@gmail.com");
		mockMvc.perform(post("/registration")
			.content(new ObjectMapper().writeValueAsString(user)))
			.andExpect(status().isOk());
	}

}
