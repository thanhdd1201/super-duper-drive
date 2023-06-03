package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Notes> getNotes(int userid){
        return noteMapper.findByUserId(userid);
    }

    public void addNote(Notes note){
        noteMapper.insertNote(note);
    }

    public void updateNote(Notes note) {
        noteMapper.updateNote(note);
    }

    public void deleteNote(int noteId){
        noteMapper.deleteNote(noteId);
    }
}
