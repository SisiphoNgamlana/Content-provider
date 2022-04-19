package com.example.myapplication.data;

import android.database.Cursor;

import com.example.myapplication.model.BadAss;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BadAssDao {

    @Insert
    long insert(BadAss badAss);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(BadAss badAss);

    @Query("SELECT * FROM badass_table WHERE id = :id")
    Cursor findBadassById(long id);

    @Query("SELECT * FROM badass_table")
    Cursor findAllBadAsses();

    @Query("DELETE FROM badass_table WHERE id = :id")
    int deleteBadAss(long id);

    @Query("DELETE FROM badass_table")
    int deleteAllBadAss();
}
