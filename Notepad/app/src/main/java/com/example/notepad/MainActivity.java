package com.example.notepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    static NotesListAdapter listAdapter;
    static ArrayList<Note> myNotesList;
    static SQLiteDatabase database;
    ListView notesListView;


    public void  makeNote(View view)
    {
        Intent intent = new Intent(this, NoteMakeActivity.class);
        startActivity(intent);
    }

    private void editNote(int position)
    {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("NoteID", position);
        startActivity(intent);
    }

    private void examineNote(int position)
    {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete this note?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    database.execSQL("DELETE FROM Notes WHERE timeOfCreation = '" + myNotesList.get(position).getTimeOfCreation() + "'");
                    myNotesList.remove(position);
                    listAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Note deleted!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    private void setNotesListView()
    {
        listAdapter = new NotesListAdapter(this, R.layout.note_item, myNotesList);
        notesListView.setAdapter(listAdapter);

        notesListView.setOnItemClickListener((adapterView, view, i, l) -> editNote(i));

        notesListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            examineNote(i);
            return true;
        });
    }

    private void getSavedNotes()
    {
        database = this.openOrCreateDatabase("Notes", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Notes (title VARCHAR, body VARCHAR, timeOfCreation VARCHAR)");

        Cursor c = database.rawQuery("SELECT * FROM Notes", null);
        int titleIndex = c.getColumnIndex("title");
        int bodyIndex = c.getColumnIndex("body");
        int timeOfCreationIndex = c.getColumnIndex("timeOfCreation");

        myNotesList = new ArrayList<>();
        Note note;
        c.moveToFirst();

        while(!c.isAfterLast())
        {
            note = new Note(c.getString(titleIndex), c.getString(bodyIndex), c.getString(timeOfCreationIndex));
            myNotesList.add(note);
            c.moveToNext();
        }
        c.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesListView = findViewById(R.id.myNotesListView);
        getSavedNotes();
        setNotesListView();



    }
}