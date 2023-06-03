package com.udacity.jwdnd.course1.cloudstorage.models;

public class Credentials {
    private Integer credentialId;
    private String url;
    private String username;
    private String securityKey;
    private String password;
    private int userId;

    public Credentials(Integer credentialId, String url, String username, String password, int userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public Credentials(Integer credentialId, String url, String username, String securityKey, String password, int userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.securityKey = securityKey;
        this.password = password;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(int credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return securityKey;
    }

    public void setKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
