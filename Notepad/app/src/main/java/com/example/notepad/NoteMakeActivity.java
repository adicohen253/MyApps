    package com.example.notepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.time.LocalDateTime;


public class NoteMakeActivity extends AppCompatActivity {
    EditText noteTitleTextView;
    EditText noteBodyTextView;

    public void createNote(View view)
    {
        if(!noteTitleTextView.getText().toString().equals(""))
        {
            if(!isTitleUnique()) {
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_menu_edit)
                .setTitle("Title already taken")
                .setMessage("There is already a note with that title, are you sure you want to save this note?")
                .setPositiveButton("Yes, save my note!", (dialogInterface, i) -> saveNote())
                .setNegativeButton("Cancel", null).show();
            }

            else if(!noteBodyTextView.getText().toString().equals("")) {
                saveNote();
            }

            else{
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_menu_edit)
                .setTitle("Note missing a body")
                .setMessage("Your note missing a body, are you sure you want to save this note?")
                .setPositiveButton("Yes, Save my note!", (dialogInterface, i) -> saveNote())
                .setNegativeButton("Cancel", null).show();

            }

        }
        else
        {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_menu_edit)
                    .setTitle("Note missing a title")
                    .setMessage("Your note missing a title, please return and give a title to your note")
                    .setPositiveButton("Ok", null).show();
        }
    }

    private boolean isTitleUnique()
    {
        for(int i=0;i<MainActivity.myNotesList.size();i++)
        {
            if(noteTitleTextView.getText().toString().equals(MainActivity.myNotesList.get(i).getTitle()))
                return false;
        }
        return true;
    }

    private void saveNote()
    {
        String title = noteTitleTextView.getText().toString();
        String body = noteBodyTextView.getText().toString();
        Note newNote = new Note(title, body, LocalDateTime.now());
        title = title.replace("'", String.valueOf(1));
        body = body.replace("'", String.valueOf(1));
        MainActivity.database.execSQL("INSERT INTO Notes VALUES ('" + title + "', '" + body + "', '" + newNote.getTimeOfCreation() + "')");
        MainActivity.myNotesList.add(newNote);
        MainActivity.listAdapter.notifyDataSetChanged();
        Toast.makeText(this, "new Note Saved!", Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_make);
        noteTitleTextView = findViewById(R.id.noteMakeTitleEditText);
        noteBodyTextView = findViewById(R.id.noteMakeBodyEditText);


    }
}