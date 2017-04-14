package com.edu.thanhnam.noteweek3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.thanhnam.noteweek3.database.NoteDataSource;
import com.edu.thanhnam.noteweek3.database.NoteSQLiteHelper;
import com.edu.thanhnam.noteweek3.model.Note;

public class DetailNote extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText edtTitle, edtTime, edtContent;
    private NoteDataSource noteDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);

        noteDataSource = new NoteDataSource(this);
        noteDataSource.open();
        edtTitle = (EditText) this.findViewById(R.id.edt_title);
        edtTime = (EditText) this.findViewById(R.id.edt_time);
        edtContent = (EditText) this.findViewById(R.id.edt_content);
        edtTime.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.edt_time) {
            if (hasFocus) {
                DatePikerFragment date = new DatePikerFragment();
                date.show(getFragmentManager(), "birthday");
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String strTitle = edtTitle.getText().toString().trim();
        String strTime = edtTime.getText().toString().trim();
        String strContent = edtContent.getText().toString().trim();
        if (TextUtils.isEmpty(strTitle)) {
            edtTitle.setError("input title please");
        } else if (TextUtils.isEmpty(strTime)) {
            edtTime.setError("input time please");
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            Note note = new Note(strTitle, strTime, strContent);

            long ok = noteDataSource.insertNote(note);
            if (ok > 0) {
                Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Add fail", Toast.LENGTH_SHORT).show();
            }
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
