package com.pavel.test.task.web.controller;

import javax.validation.Valid;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pavel.test.task.entity.User;
import com.pavel.test.task.repo.UserRepository;
import com.pavel.test.task.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Registration", description = "APIs for working with registration")
@Controller
public class RegistrationController {
	
	@Autowired
	private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Operation(summary = "Show user")
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registrationPage";
    }

    @Operation(summary = "Registration user")
    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, Model model) {
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registrationPage";
        }
        
        userRepository.save(userForm);
        return "redirect:/";
    }
}
