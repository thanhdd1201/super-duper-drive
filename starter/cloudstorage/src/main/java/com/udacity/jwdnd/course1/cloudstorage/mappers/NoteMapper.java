package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NotesMapper {
    @Select("SELECT * FROM NOTE")
    List<Note> findAll();

    @Select("SELECT * FROM NOTE WHERE noteId = #{noteId}")
    public Note findOne(int noteId);

    @Select("SELECT * FROM NOTE WHERE userid = #{userId}")
    public List<Note> findByUserId(int userId);

    @Insert("INSERT INTO NOTE (noteTitle, noteDescription, userid) VALUES (#{note.noteTitle}, #{note.noteDescription}, #{userid})")
    public int insertNote(Note note, int userid);

    @Delete("DELETE FROM NOTE WHERE noteId = #{noteId}")
    public int deleteNote(int noteId);

    @Update("UPDATE NOTE SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}")
    public int updateNote(Note note);
}
