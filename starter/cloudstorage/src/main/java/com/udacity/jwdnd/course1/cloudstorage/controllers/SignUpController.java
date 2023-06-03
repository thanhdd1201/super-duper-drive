package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signUpView() { return "signup"; }

    @PostMapping()
    public String signUpUser(@ModelAttribute Users user, Model model) {
        String errMsg = null;
        if(!userService.isUsernameAvailable(user.getUsername())) {
            errMsg = "The username is already exist";
        }
        if(errMsg==null) {
            int userAdded = userService.createUser(user);
            if(userAdded< 0) errMsg = "There was an error signing you up";
        }
        if(errMsg!= null){
            model.addAttribute("errMsg", errMsg);
            return "signup";
        }
        model.addAttribute("signupSuccess", true);

        return "login";
    }
}
