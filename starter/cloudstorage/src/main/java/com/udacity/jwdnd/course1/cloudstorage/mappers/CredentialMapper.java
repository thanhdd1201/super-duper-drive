package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credentials findOne(int credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credentials> findByUserId(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, securityKey, password, userId) VALUES (#{url}, #{username}, #{securityKey}, #{password}, #{userId})")
    int insertCredential(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int deleteCredential(int credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, securityKey = #{securityKey}, password = #{password} WHERE credentialId = #{credentialId}")
    int updateCredential(Credentials credentials);
}
