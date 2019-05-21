package com.example.ndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String SONG = "song.db";

    //For creating note content table
    public static final String SONG_TABLE = "song_table";
    public static final String SONG_ID = "song_id";
    public static final String TITLE = "title";
    public static final String SINGERS = "singers";
    public static final String YEAR = "year";
    public static final String STARS = "stars";

    public DatabaseHelper(Context context) {
        super(context, SONG, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSongTable = "CREATE TABLE  " +  SONG_TABLE + "" +
                "(" + SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT, "
                + SINGERS + " TEXT, "
                + YEAR + " INTEGER, "
                + STARS + " INTEGER ); ";
        sqLiteDatabase.execSQL(createSongTable);
        Log.i("info", "created tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS " + SONG_TABLE);

        onCreate(sqLiteDatabase);
    }

    //insert Songs
    public long insertNote(String title, String singers, int year, int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(SINGERS, singers);
        values.put(YEAR, year);
        values.put(STARS, stars);
        long result = db.insert(SONG_TABLE, null, values);
        db.close();
        Log.d("SQL Insert ",""+ result); //id returned, shouldn’t be -1
        return result;
    }

    //update Songs
    public int updateNote(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, data.getTitle());
        cv.put(SINGERS, data.getSingers());
        cv.put(YEAR, data.getYear());
        cv.put(STARS, data.getStars());
        String condition = SONG_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(SONG_TABLE, cv, condition, args);
        db.close();
        return result;
    }

    //delete Songs
    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = SONG_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(SONG_TABLE, condition, args);
        db.close();
        return result;
    }

    //Getting all the songs
    public ArrayList<String> getAllNotesString() {
        ArrayList<String> songs = new ArrayList<String>();

        String selectQuery = "SELECT " + SONG_ID + ","
                + TITLE + " FROM " + SONG_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String content = cursor.getString(1);
                songs.add("ID:" + id + ", " + content);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }
    
    //filter
    public ArrayList<Song> getAllNotes(String keyword) {
        ArrayList<Song> notes = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {SONG_ID, TITLE, SINGERS, YEAR, STARS};
        String condition = TITLE + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(SONG_TABLE, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Song songs = new Song(id, title, singers, year, stars);
                notes.add(songs);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }
}
