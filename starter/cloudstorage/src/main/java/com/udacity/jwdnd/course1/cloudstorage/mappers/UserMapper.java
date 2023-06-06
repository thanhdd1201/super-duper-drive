package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS")
    List<Users> findAll();

    @Select("SELECT * FROM USERS WHERE userId = #{userId}")
    Users findOne(int userid);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    Users findByUsername(String username);

    @Insert("INSERT INTO USERS (username, password, salt, firstName, lastName) VALUES (#{username}, #{password}, #{salt}, #{firstName}, #{lastName})")
    int insertUser(Users superUser);

    @Delete("DELETE FROM USERS WHERE username = #{username}")
    int deleteUser(String username);

    @Update("UPDATE USERS SET username = #{username}, password = #{password}, salt = #{salt}, firstName = #{firstName}, lastName = #{lastName} WHERE userId = #{userId}")
    int updateUser(Users user);
}
