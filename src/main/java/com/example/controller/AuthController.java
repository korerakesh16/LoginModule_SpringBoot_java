package com.example.springbootlogin.controller;

import com.example.springbootlogin.dto.UserRegistrationDto;
import com.example.springbootlogin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegistrationDto userDto,
                           BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "register";
        }
        try {
            userService.registerNewUser(userDto);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("registrationError", ex.getMessage());
            return "register";
        }
        return "redirect:/login?registered";
    }

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }
}
