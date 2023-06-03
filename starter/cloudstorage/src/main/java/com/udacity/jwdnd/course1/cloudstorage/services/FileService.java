package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void addFile(Files file) {
        fileMapper.saveFile(file);
    }

    public List<Files> getUploadedFiles(Integer userid){
        return fileMapper.findAll(userid);
    }

    public boolean isFileAvailable(String filename, Integer userid) {
        Files file = fileMapper.getFile(userid, filename);

        return file != null;
    }

    public int deleteFile(int fileId) {
        return fileMapper.deleteFile(fileId);
    }

    public Files getFileById(Integer fileId){
        return fileMapper.findById(fileId);
    }
}
