package com.pavel.test.task.init;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.pavel.test.task.entity.User;
import com.pavel.test.task.repo.UserRepository;

@Component
public class UserInit implements ApplicationRunner  {

	private UserRepository userRepository;
		
	@Autowired
	public UserInit(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		long count = userRepository.count();
		
		if (count == 0) {
            User user1 = new User();
            user1.setUsername("John");
            user1.setPassword("1");
            user1.setEmail("john@gmail.com");
            
            User user2 = new User();
            user2.setUsername("Smith");
            user2.setPassword("2");
            user2.setEmail("smith@gmail.com");
 
            userRepository.save(user1);
            userRepository.save(user2);
        }
	}
}
