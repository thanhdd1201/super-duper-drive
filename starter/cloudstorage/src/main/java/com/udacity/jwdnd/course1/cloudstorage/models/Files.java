package com.udacity.jwdnd.course1.cloudstorage.models;

public class Files {
    private final Integer fileId;
    private final String fileName;
    private final String contentType;
    private final long fileSize;
    private final byte[] fileData;
    private final int userId;

    public Files(Integer fileId, String fileName, String contentType, long fileSize,int userId, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public int getUserId() {
        return userId;
    }
}
