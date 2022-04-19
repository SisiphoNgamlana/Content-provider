package com.example.myapplication.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.myapplication.model.BadAss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BadAssContentProvider extends ContentProvider {

    public static final String TAG = BadAssContentProvider.class.getName();

    public static final String AUTHORITY = "com.example.myapplication";
    public static final String BADASS_TABLE_NAME = "badass_table";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IMAGE = "image";

    public static final int ALL_BADASS = 1;
    public static final int BADASS_ID = 2;

    public static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
            + BADASS_TABLE_NAME);

    static {
        uriMatcher.addURI(AUTHORITY, BADASS_TABLE_NAME, ALL_BADASS);
        uriMatcher.addURI(AUTHORITY, BADASS_TABLE_NAME + "/#", BADASS_ID);
    }

    private BadAssDao badAssDao;

    @Override
    public boolean onCreate() {
        badAssDao = ApplicationDatabase.getInstance(getContext()).badAssDao();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] strings,
                        @Nullable String s,
                        @Nullable String[] strings1,
                        @Nullable String s1) {
        Log.d(TAG, "query: ");
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case ALL_BADASS:
                cursor = badAssDao.findAllBadAsses();
                if (getContext() != null) {
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                }
            case BADASS_ID:
                cursor = badAssDao.findBadassById(ContentUris.parseId(uri));
                if (getContext() != null) {
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                }
            default:
                throw new IllegalArgumentException("Unsupported uri: " + uri);

        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_BADASS:
                return "vnd.android.cursor.dir/" + BADASS_TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unsupported uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (uriMatcher.match(uri)) {
            case ALL_BADASS:
                if (getContext() != null && contentValues != null) {
                    long id = badAssDao.insert(BadAss.getBadAssFromContentValues(contentValues));
                    if (id != 0) {
                        getContext().getContentResolver()
                                .notifyChange(uri, null);
                        return ContentUris.withAppendedId(uri, id);
                    }
                }
            case BADASS_ID:
                throw new IllegalArgumentException("Invalid URI: insert failed" + uri);
            default:
                throw new IllegalArgumentException("Invalid URI: insert failed" + uri);

        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        switch (uriMatcher.match(uri)) {
            case ALL_BADASS:
                if (getContext() != null) {
                    int result = badAssDao.deleteAllBadAss();
                    getContext().getContentResolver().notifyChange(uri, null);
                    return result;
                }
            case BADASS_ID:
                if (getContext() != null) {
                    int result = badAssDao.deleteBadAss(ContentUris.parseId(uri));
                    getContext().getContentResolver().notifyChange(uri, null);
                    if (result != 0) {
                        Log.d(TAG, "Delete successfull");
                    } else {
                        Log.d(TAG, "Delete failed");
                    }
                    return result;
                }
            default:
                throw new IllegalArgumentException("Invalid URI: delete failed" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues
            , @Nullable String s, @Nullable String[] strings) {
        int result;
        switch (uriMatcher.match(uri)) {
            case BADASS_ID:
                if (getContext() != null && contentValues != null) {
                    result = badAssDao.update(BadAss.getBadAssFromContentValues(contentValues));
                    getContext().getContentResolver().notifyChange(uri, null);
                    return result;
                }
            case ALL_BADASS:
                throw new IllegalArgumentException("Invalid URI: insert failed" + uri);
            default:
                throw new IllegalArgumentException("Invalid URI: insert failed" + uri);
        }
    }
}
