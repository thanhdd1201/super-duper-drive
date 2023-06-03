package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    Files findByName(String fileName);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    Files findById(Integer fileId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Results({
            @Result(property = "contentType", column = "content_type")
    })
    int saveFile(Files file);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<Files> findAll(Integer userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(int fileId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId} AND fileName = #{fileName}")
    Files getFile(Integer userId, String fileName);
}
