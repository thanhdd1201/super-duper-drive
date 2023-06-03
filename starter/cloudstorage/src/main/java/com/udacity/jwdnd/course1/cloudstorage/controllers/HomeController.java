package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialsService credentialsService;

    public HomeController(
            FileService fileService,
            UserService userService,
            NoteService noteService,
            CredentialsService credentialsService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialsService = credentialsService;
    }

    @GetMapping()
    public String home(Authentication authentication, Model model) {
        try {
            Users user = userService.getUser(authentication.getName());
            if(user == null)   {
                return "redirect:/login/error";
            }
            var uid = user.getUserId();


            model.addAttribute("notes", noteService.getNotes(uid));
            model.addAttribute("files", fileService.getUploadedFiles(uid));
            model.addAttribute("credentials",credentialsService.getCredentials(uid));
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
        return "home";
    }
}
