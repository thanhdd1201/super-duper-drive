package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final UserService userService;
    private final NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    public List<String> validate(Map<String, String> data) {
        List<String> errMsg = new ArrayList<>();

        if(data.get("noteTitle").isEmpty())
            errMsg.add("Note title is required");
        if(data.get("noteDescription").isEmpty())
            errMsg.add("Note description is required");

        return errMsg;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createView(
            HttpServletResponse response,
            Authentication authentication,
            @RequestParam Map<String, String> data,
            Model model
    ) {
        var errors = validate(data);
        Users user = userService.getUser(authentication.getName());
        if (user == null) {
            errors.add("Forbidden resource!");
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "result";
        }
        var uid = user.getUserId();

        if (data.get("noteId").equals("")) {
            noteService.addNote(new Notes(null, data.get("noteTitle"), data.get("noteDescription"), uid));
        } else {
            noteService.updateNote(new Notes(Integer.parseInt(data.get("noteId")), data.get("noteTitle"), data.get("noteDescription"), uid));
        }

        model.addAttribute("success", true);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @GetMapping(value = "delete/{noteId}")
    public String removeView(
            HttpServletResponse response,
            @PathVariable Integer noteId,
            Authentication authentication,
            Model model
    ) {
        List<String> errors = new ArrayList<>();
        model.addAttribute("success", true);
        try {
            Users user = userService.getUser(authentication.getName());
            if (user == null) {
                errors.add("Forbidden resource!");
                model.addAttribute("errors", errors);
                model.addAttribute("success", false);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            noteService.deleteNote(noteId);
        } catch (Exception ignore) {
            errors.add("There was a server error. The note was not removed.");
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return "result";
    }
}
