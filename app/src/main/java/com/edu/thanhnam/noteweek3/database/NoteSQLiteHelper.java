package com.edu.thanhnam.noteweek3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class NoteSQLiteHelper extends SQLiteOpenHelper {
  public static final String DATABASE_NAME = "myNote.db";
  public static final String TABLE_NAME = "Note";
  public static final String COLUMN_ID = "id";
  public static final String COLUNM_TITLE = "title";
  public static final String COLUMN_TIME = "time";
  public static final String COLUMN_CONTENT = "content";
  public static final int DATABASE_VERSION = 1;
  public static final String CRAETE_DATABASE = " CREATE TABLE " + TABLE_NAME + " ( " +
      COLUMN_ID + " integer primary key autoincrement ," +
      COLUNM_TITLE + " text not null ," + COLUMN_TIME + " text not null ," +
      COLUMN_CONTENT + " text not null" + " ) ";


  public NoteSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);

  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CRAETE_DATABASE);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exist" + TABLE_NAME);
    onCreate(db);

  }
}
