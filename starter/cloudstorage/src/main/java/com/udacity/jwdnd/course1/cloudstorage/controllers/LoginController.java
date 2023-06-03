package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping()
    public String loginView(Model model) {
        return "login";
    }

    @GetMapping("/error")
    public String AuthenticationFailLogin(Model model){
        model.addAttribute("isAuthenticationFail", true);
        return "login";
    }
}
