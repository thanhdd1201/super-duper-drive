package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Notes findOne(int noteId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Notes> findByUserId(int userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    int insertNote(Notes note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int deleteNote(int noteId);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}")
    int updateNote(Notes note);
}
