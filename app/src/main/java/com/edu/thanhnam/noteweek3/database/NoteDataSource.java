package com.edu.thanhnam.noteweek3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.thanhnam.noteweek3.model.Note;

import java.util.ArrayList;

/**
 * Created by ThanhNam on 4/12/2017.
 */

public class NoteDataSource {
  private SQLiteDatabase sqLiteDatabase;
  private NoteSQLiteHelper sqLiteHelper;
  private String colum[] = {NoteSQLiteHelper.COLUNM_TITLE, NoteSQLiteHelper.COLUMN_TIME, NoteSQLiteHelper.COLUMN_CONTENT};
  private Context context;

  public NoteDataSource(Context context) {
    this.context = context;
    sqLiteHelper = new NoteSQLiteHelper(context);
  }

  public void open() {
    sqLiteDatabase = sqLiteHelper.getWritableDatabase();
  }

  public ArrayList<Note> getAllNote() {
    ArrayList<Note> notes = new ArrayList<>();
    Cursor cursor = sqLiteDatabase.rawQuery("select * from " + NoteSQLiteHelper.TABLE_NAME, null);
    cursor.moveToFirst();
    while (!(cursor.isAfterLast())) {
      Note note = cursorToNote(cursor);
      notes.add(note);
      cursor.moveToNext();
    }
    return notes;
  }

  private Note cursorToNote(Cursor cursor) {
    Note note = new Note();
    note.setId(cursor.getInt(0));
    note.setTitle(cursor.getString(1));
    note.setTime(cursor.getString(2));
    note.setContent(cursor.getString(3));
    return note;
  }

  public void deleteNote(Note note) {
    sqLiteDatabase.delete(NoteSQLiteHelper.TABLE_NAME, NoteSQLiteHelper.COLUMN_ID + " = " + note.getId(), null);
  }

  public long insertNote(Note note) {
    ContentValues values = new ContentValues();
    values.put(NoteSQLiteHelper.COLUNM_TITLE, note.getTitle());
    values.put(NoteSQLiteHelper.COLUMN_TIME, note.getTime());
    values.put(NoteSQLiteHelper.COLUMN_CONTENT, note.getContent());
    long ok = sqLiteDatabase.insert(NoteSQLiteHelper.TABLE_NAME, null, values);
    return ok;
  }

  public int updateNote(Note note) {
    ContentValues values = new ContentValues();
    values.put(NoteSQLiteHelper.COLUNM_TITLE, note.getTitle());
    values.put(NoteSQLiteHelper.COLUMN_TIME, note.getTime());
    values.put(NoteSQLiteHelper.COLUMN_CONTENT, note.getContent());
    return sqLiteDatabase.update(NoteSQLiteHelper.TABLE_NAME, values, NoteSQLiteHelper.COLUMN_ID + " = " + note.getId(), null);
  }

  public void close() {
    sqLiteHelper.close();
  }
}
