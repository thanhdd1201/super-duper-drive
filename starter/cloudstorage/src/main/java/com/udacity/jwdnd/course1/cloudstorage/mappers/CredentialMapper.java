package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CredentialsMapper {
    @Select("SELECT * FROM CREDENTIAL")
    List<Credential> findAll();

    @Select("SELECT * FROM CREDENTIAL WHERE credentialId = #{credentialId}")
    Credential findOne(int credentialId);

    @Select("SELECT * FROM CREDENTIAL WHERE userId = #{userId}")
    List<Credential> findByUserId(int userId);

    @Insert("INSERT INTO CREDENTIAL (url, username, key, password, userId) VALUES (#{credential.url}, #{credential.username}, #{credential.key}, #{credential.password}, #{credential.userId})")
    int insertCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIAL WHERE credentialId = #{credentialId}")
    int deleteCredential(int credentialId);

    @Update("UPDATE CREDENTIAL SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialId = #{credentialId}")
    int updateCredential(Credential credential);
}
