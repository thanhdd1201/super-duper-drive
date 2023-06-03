package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credentials> getCredentials(int userId) {
        return credentialMapper.findByUserId(userId);
    }

    public void addCredential(Credentials credentials) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedKey);

        credentials.setKey(encodedKey);
        credentials.setPassword(encryptedPassword);

        credentialMapper.insertCredential(credentials);
    }

    public int deleteCredential(int credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }

    public void editCredential(Credentials credentials) {
        Credentials storedCredentials = credentialMapper.findOne(credentials.getCredentialId());

        credentials.setKey(storedCredentials.getKey());
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), credentials.getKey());
        credentials.setPassword(encryptedPassword);
        credentialMapper.updateCredential(credentials);
    }

    public String decryptCredential(int credentialId) {
        var found = credentialMapper.findOne(credentialId);
        return encryptionService.decryptValue(found.getPassword(), found.getKey());
    }
}
