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

import com.pavel.test.task.entity.User;
import com.pavel.test.task.service.UserService;
import com.pavel.test.task.web.controller.AdminController;

@RunWith(SpringJUnit4ClassRunner.class)
public class AdminControllerTests {

	private MockMvc mockMvc;
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private AdminController Ac;
	
	
	@Before
	public void setUp() {
		 mockMvc = MockMvcBuilders.standaloneSetup(Ac)
	                .build();
	}
	
	@Test
	public void testUserList() throws Exception {
		 mockMvc.perform(get("/admin"))
		 .andExpect(status().isOk());
	}

	@Test
	public void testGtUser() throws Exception {
		User user = new User(4L, "John", "password", "john@gmai.com");
		userService.saveUser(user);
		mockMvc.perform(get("/admin/gt/4"))
		 .andExpect(status().isOk());
	}

}
