package com.example.myapplication.model;

import android.content.ContentValues;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "badass_table")
public class BadAss implements Serializable {

    private static final long serialVersionUID = -123456766787L;

    private static final String BADASS_ID = "id";
    private static final String BADASS_NAME = "name";
    private static final String BADASS_IMAGE = "image";
    private static final String BADASS_DESCRIPTION = "description";

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image")
    private int image;

    @ColumnInfo(name = "description")
    private String description;

    public BadAss() {
    }

    public BadAss(String name, int image) {
        this.name = name;
        this.image = image;
        this.description = "description";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static BadAss getBadAssFromContentValues(ContentValues contentValues) {
        BadAss badAss = new BadAss();
        if (contentValues.containsKey(BADASS_NAME)) {
            badAss.setName(contentValues.getAsString(BADASS_NAME));
        }
        if (contentValues.containsKey(BADASS_IMAGE)) {
            badAss.setImage(contentValues.getAsInteger(BADASS_IMAGE));
        }
        if (contentValues.containsKey(BADASS_DESCRIPTION)) {
            badAss.setDescription(contentValues.getAsString(BADASS_DESCRIPTION));
        }
        return badAss;
    }
}
