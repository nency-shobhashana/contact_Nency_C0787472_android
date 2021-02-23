package com.nency.contact.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)

public abstract class ContactRoomDatabase extends RoomDatabase {
    private static final String DB_NAME = "room_contact_database";

    public abstract ContactDao ContactDao();

    private static volatile ContactRoomDatabase INSTANCE;

    public static ContactRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactRoomDatabase.class,DB_NAME).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
