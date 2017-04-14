package com.edu.thanhnam.noteweek3;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.thanhnam.noteweek3.adapter.AdapterNote;
import com.edu.thanhnam.noteweek3.database.NoteDataSource;
import com.edu.thanhnam.noteweek3.model.Note;

import java.util.ArrayList;

import static com.edu.thanhnam.noteweek3.R.id.parent;
import static com.edu.thanhnam.noteweek3.R.layout.item;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView lvNote;
    private TextView txtNote;
    private ArrayList<Note> notes;
    private AdapterNote adapterNote;
    private NoteDataSource noteDataSource;
    private MenuItem miUndo, mnuDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvNote = (ListView) this.findViewById(R.id.lv_note);
        txtNote = (TextView) this.findViewById(R.id.txt_noty);

        noteDataSource = new NoteDataSource(this);
        noteDataSource.open();
        lvNote.setOnItemClickListener(this);
        lvNote.setLongClickable(true);
        lvNote.setOnItemLongClickListener(this);
        updateList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (notes != null && notes.size() > 0) {
                txtNote.setVisibility(View.INVISIBLE);
                lvNote.setVisibility(View.VISIBLE);
                adapterNote = new AdapterNote(MainActivity.this, notes);
                lvNote.setAdapter(adapterNote);
            } else {
                txtNote.setVisibility(View.VISIBLE);
                lvNote.setVisibility(View.INVISIBLE);
            }
        }
    };

    private void updateList() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                notes = noteDataSource.getAllNote();
                handler.sendEmptyMessage(0);
            }
        };
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuadd, menu);

        mnuDelete = menu.findItem(R.id.mnu_delete).setVisible(false);
        miUndo = menu.findItem(R.id.mnu_undo).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.mnu_delete:
                for (int i = 0; i < adapterNote.getCount(); i++) {
                    if (adapterNote.getItem(i).isSelected()) {
                        noteDataSource.deleteNote(adapterNote.getItem(i));
                    }
                }
                updateList();
                break;
            case R.id.mnu_undo:
                for (int i = 0; i < adapterNote.getCount(); i++) {
                    if (adapterNote.getItem(i).isSelected()) {
                        adapterNote.getItem(i).setSelected(false);
                        updateList();
                    }
                }
                break;
            case R.id.mnu_add:
                Intent intent = new Intent(MainActivity.this, DetailNote.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        noteDataSource.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Note note = adapterNote.getItem(position);
        Intent intent = new Intent(MainActivity.this, InforNote.class);
        intent.putExtra("note", note);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        notes.get(position).setSelected(true);
        adapterNote.notifyDataSetChanged();

        return false;
    }

    public void toggleUndo(boolean visibility) {
        miUndo.setVisible(visibility);
        mnuDelete.setVisible(visibility);
    }
}
