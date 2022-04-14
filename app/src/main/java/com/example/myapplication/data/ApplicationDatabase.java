package com.example.myapplication.data;

import android.content.Context;

import com.example.myapplication.model.BadAss;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BadAss.class}, version = 1, exportSchema = false)
abstract class ApplicationDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Badass_database";

    private static ApplicationDatabase INSTANCE;

    public static ApplicationDatabase getInstance(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, ApplicationDatabase.class,
                    DATABASE_NAME).build();
        }

        return INSTANCE;
    }

    public abstract BadAssDao badAssDao();

}
