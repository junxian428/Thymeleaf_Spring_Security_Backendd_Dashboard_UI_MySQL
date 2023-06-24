package com.example.erp.controller;

import com.example.erp.dto.UserDto;
import com.example.erp.entity.User;
import com.example.erp.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.net.http.HttpClient.Redirect;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        String name = user.getFirstName() + " " + user.getLastName();
        LocalDateTime localDateTime = LocalDateTime.now();
        return "redirect:/register?success";
    }


    @GetMapping("/dashboard")
    public String Dashboard(Model model){
        return "backend/index";
    }

    @GetMapping("/Content_Management_System")
    public String CMS(Model model){
        return "backend/cms";
    }


    @GetMapping("/eplatform")
    public String ECommerce(Model model){
        return "backend/ecommerce";
    }

    
    @GetMapping("/shipping")
    public String Logistics(Model model){
        return "backend/logistics";
    }

        
    @GetMapping("/Stock")
    public String Stock(Model model){
        return "backend/inventory";
    }


    @GetMapping("/admin")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        LocalDateTime localDateTime = LocalDateTime.now();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
        }
           

        return "dashboard/index";
    }
}
