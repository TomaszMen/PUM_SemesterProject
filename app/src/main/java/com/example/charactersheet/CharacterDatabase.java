package com.example.charactersheet;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Character.class}, version = 1, exportSchema = false)
public abstract class CharacterDatabase extends RoomDatabase {
    private static CharacterDatabase instance;

    public abstract CharacterDao characterDao();

    public static synchronized CharacterDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CharacterDatabase.class, "character_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}