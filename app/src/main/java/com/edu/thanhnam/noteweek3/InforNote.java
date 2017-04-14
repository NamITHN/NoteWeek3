package com.edu.thanhnam.noteweek3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.thanhnam.noteweek3.database.NoteDataSource;
import com.edu.thanhnam.noteweek3.model.Note;

public class InforNote extends AppCompatActivity {
    private TextView txtContent, txtTitle, txtTime;
    private EditText edtTitle, edtTime, edtContent;
    private NoteDataSource noteDataSource;
    private Note note;
    private Intent intent;
    private Button btnOk;
    private DatePikerFragment datePikerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_note);

        txtTitle = (TextView) this.findViewById(R.id.txt_title);
        txtTime = (TextView) this.findViewById(R.id.txt_time);
        txtContent = (TextView) this.findViewById(R.id.txt_content);
        edtTitle = (EditText) this.findViewById(R.id.edt_title);
        edtTime = (EditText) this.findViewById(R.id.edt_time);
        edtContent = (EditText) this.findViewById(R.id.edt_content);
        btnOk = (Button) this.findViewById(R.id.btn_ok);

        noteDataSource = new NoteDataSource(this);
        noteDataSource.open();
        note = (Note) getIntent().getSerializableExtra("note");

        txtTitle.setText(note.getTitle());
        txtTime.setText(note.getTime());
        txtContent.setText(note.getContent());

        datePikerFragment = new DatePikerFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_infor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_edit:

                intent = new Intent(InforNote.this, MainActivity.class);
                txtTime.setVisibility(View.INVISIBLE);
                edtTime.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.INVISIBLE);
                edtTitle.setVisibility(View.VISIBLE);
                txtContent.setVisibility(View.INVISIBLE);
                edtContent.setVisibility(View.VISIBLE);
                btnOk.setVisibility(View.VISIBLE);
                edtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            datePikerFragment.show(getFragmentManager(), "birthday");
                        }

                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String strTitle = edtTitle.getText().toString().trim();
                        String strTime = edtTime.getText().toString().trim();
                        String strContent = edtContent.getText().toString().trim();
                        if (TextUtils.isEmpty(strTitle)) {
                            edtTitle.setError("input title please");
                        } else if (TextUtils.isEmpty(strTime)) {
                            edtTime.setError("input time please");
                        } else {
                            int id = note.getId();
                            note = new Note(id, strTitle, strTime, strContent);
                            int ok = noteDataSource.updateNote(note);
                            if (ok != 0) {
                                intent = new Intent(InforNote.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(InforNote.this, "update success", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(InforNote.this, "update fail", Toast.LENGTH_LONG).show();
                                intent = new Intent(InforNote.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }

                    }
                });

                break;
            case R.id.mnu_delete:
                intent = new Intent(InforNote.this, MainActivity.class);
                noteDataSource.deleteNote(note);
                Toast.makeText(this, "Delete success", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
