package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {
    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadView(
        HttpServletResponse response,
        Authentication authentication,
        @RequestParam(name = "fileUpload") MultipartFile multipartFile,
        Model model
    ) {
        List<String> errMsg = new ArrayList<>();
        model.addAttribute("success", true);

        if (multipartFile.isEmpty()) {
            errMsg.add("File must not be empty.");
            model.addAttribute("errors", errMsg);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "result";
        }
        Users user = userService.getUser(authentication.getName());
        if (user == null) {
            errMsg.add("Forbidden resource!");
            model.addAttribute("errors", errMsg);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "result";
        }
        var uid = user.getUserId();
        try {
            var file = new Files(
                null,
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getSize(),
                uid, multipartFile.getBytes()
            );

            if (fileService.isFileAvailable(file.getFileName(), uid)) {
                errMsg.add("File is already stored.");
                model.addAttribute("errors", errMsg);
                model.addAttribute("success", false);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "result";
            }

            fileService.addFile(file);

        } catch (Exception e) {
            e.printStackTrace();
            errMsg.add("Something is wrong, please try again!");
            model.addAttribute("errors", errMsg);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @GetMapping(value = "/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadView(
            @PathVariable Integer fileId
    ) {
        var file = fileService.getFileById(fileId);
        if (file != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header("Content-Disposition", "attachment; filename=" + file.getFileName())
                    .body(new ByteArrayResource(file.getFileData()));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/delete/{fileId}")
    public String removeView(
        Authentication authentication,
        HttpServletResponse response,
        @PathVariable Integer fileId,
        Model model
    ) {
        List<String> errMsg = new ArrayList<>();
        var user = userService.getUser(authentication.getName());
        if (user == null) {
            errMsg.add("Forbidden resource!");
            model.addAttribute("errors", errMsg);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        model.addAttribute("success", true);
        try {
            fileService.deleteFile(fileId);
        } catch (Exception ignore) {
            errMsg.add("There was a server error. The file was not removed.");
            model.addAttribute("errors", errMsg);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "result";
    }
}
