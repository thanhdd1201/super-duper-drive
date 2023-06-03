package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {
    @Select("SELECT * FROM FILE WHERE fileName = #{fileName}")
    File findFileByName(String fileName);

    @Select("SELECT * FROM FILE WHERE fileId = #{fileId}")
    File findFileById(Integer fileId);

    @Insert("INSERT INTO FILE (fileName, contentType, fileSize, userId, fileData) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int saveFile(File file);

    @Select("SELECT * FROM FILE WHERE userId= #{userId} ")
    List<File> findAll(Integer userId);

    @Delete("DELETE FROM FILE WHERE fileId = #{fileId}")
    int deleteFile(int fileId);

    @Select("SELECT * FROM FILE WHERE userId = #{userId} AND fileName = #{fileName}")
    File getFile(Integer userId, String fileName);
}
