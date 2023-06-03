package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private CredentialsService credentialsService;

    private UserService userService;

    public CredentialController(CredentialsService credentialsService, UserService userService) {
        this.credentialsService = credentialsService;
        this.userService = userService;
    }

    public List<String> validate(Map<String, String> data) {
        var errMsg = new ArrayList<String>();

        if (data.get("url").isEmpty())
            errMsg.add("URL must not be empty.");

        if (data.get("username").isEmpty())
            errMsg.add("Username must not be empty.");

        if (data.get("password").isEmpty())
            errMsg.add("Password must not be empty.");

        return errMsg;
    }


    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createView(
            HttpServletResponse response,
            Authentication authentication,
            @RequestParam Map<String, String> data,
            Model model
    ) {
        var errMsg = validate(data);

        var user = userService.getUser(authentication.getName());
        if (user == null) {
            errMsg.add("Forbidden resource!");
            model.addAttribute("errors", errMsg);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "result";
        }
        int uid = user.getUserId();
        if (data.get("credentialId").equals("")) {
            credentialsService.addCredential(new Credentials(null, data.get("url"), data.get("username"), data.get("password"), uid));
        } else {
            credentialsService.editCredential(new Credentials(Integer.parseInt(data.get("credentialId")), data.get("url"), data.get("username"), data.get("password"), uid));
        }

        model.addAttribute("success", true);
        if (!errMsg.isEmpty()) {
            model.addAttribute("errors", errMsg);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @GetMapping(value = "/delete/{credentialId}")
    public String removeView(
            HttpServletResponse response,
            @PathVariable Integer credentialId,
            Authentication authentication,
            Model model
    ) {
        var errMsg = new ArrayList<String>();
        model.addAttribute("success", true);
        try {
            var user = userService.getUser(authentication.getName());
            if (user == null) {
                errMsg.add("Forbidden resource!");
                model.addAttribute("errors", errMsg);
                model.addAttribute("success", false);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }

            credentialsService.deleteCredential(credentialId);

        } catch (Exception ignore) {
            errMsg.add("There was a server error. The credential was not removed.");
            model.addAttribute("errors", errMsg);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "result";
    }
    @ResponseBody
    @GetMapping(value = "/get-password/{credentialId}")
    public ResponseEntity<String> getPassword(
            @PathVariable Integer credentialId,
            Authentication authentication
    ) {
        try {
            var user = userService.getUser(authentication.getName());
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(credentialsService.decryptCredential(credentialId), HttpStatus.OK);

        } catch (Exception ignored) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
