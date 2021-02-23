package com.nency.contact.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insertNote(Contact note);

    @Query("DELETE FROM contact")
    void deleteAllNotes();

    @Query("DELETE FROM contact WHERE id = :id")
    void deleteNote(int id);

    @Query("UPDATE contact SET firstName = :firstName, lastName = :lastName, email = :email, phoneNumber = :phoneNumber, address = :address where id == :id")
    void updateNote(int id, String firstName, String lastName, String email, int phoneNumber, String address);

    @Query("SELECT * FROM contact ORDER BY firstName")
    List<Contact> getAllNotes();

    @Query("SELECT * FROM contact where id == :id")
    Contact getNote(int id);
}
