package com.pavel.test.task.service;

import static org.junit.Assert.assertEquals; 

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.pavel.test.task.entity.User;
import com.pavel.test.task.repo.RoleRepository;
import com.pavel.test.task.repo.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTests {
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	RoleRepository roleRepository;
	
	@Autowired
	UserService userService; 
	
	@Test
	public void addUserFailTest() {
		User user = new User();
		user.setUsername("John");
		
		Mockito.doReturn(new User())
			.when(userRepository)
			.findByUsername("John");
		
		boolean isUserCreated = userService.saveUser(user);
		Assert.assertFalse(isUserCreated);
	}
	
	@Test
	public void findAllUsersTest() {
		User user = new User();
		user.setUserId((long) 5);
		user.setUsername("Bob");
		user.setPassword("1");
		user.setEmail("bob.gmail.com");
		userRepository.save(user);
		List<User> users = userService.allUsers();
		List<User> users1 = userRepository.findAll();
		assertEquals(users, users1);
		}
	
	@Test
	public void saveUserTest() {
		User user = new User();
		user.setUserId((long) 5);
		user.setUsername("Bob");
		user.setPassword("1");
		user.setEmail("bob.gmail.com");
		boolean isUserCreated = userService.saveUser(user);
		
		Assert.assertTrue(isUserCreated);
	}
	
	@Test
	public void deleteUserTest() {
		User user = new User();
		user.setUserId((long) 5);
		user.setUsername("Bob");
		user.setPassword("1");
		user.setEmail("bob.gmail.com");
		userRepository.save(user);
		
		userService.deleteUser(user.getUserId());
		
		User user1 = userRepository.findByUserId(user.getUserId());
		assertEquals(null, user1);
	}
}
