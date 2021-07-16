package com.example.notepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NoteEditorActivity extends AppCompatActivity {
    EditText noteTitleEditText;
    EditText noteBodyEditText;
    TextView timeOfCreationTitleTextView;
    int noteID;
    Note editedNote;

    public void saveEditNote(View view)
    {

        String title = noteTitleEditText.getText().toString();
        String body = noteBodyEditText.getText().toString();
        editedNote.setTitle(title);
        editedNote.setBody(body);
        MainActivity.listAdapter.notifyDataSetChanged();
        title = title.replace("'", String.valueOf(1));
        body = body.replace("'", String.valueOf(1));
        MainActivity.database.execSQL("UPDATE Notes SET title = '" + title + "', body = '" + body + "' " +
                "WHERE timeOfCreation='" + editedNote.getTimeOfCreation() +"'");
        Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void examineNote(View view)
    {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete this note?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    MainActivity.database.execSQL("DELETE FROM Notes WHERE timeOfCreation = '" + MainActivity.myNotesList.get(noteID).getTimeOfCreation() + "'");
                    MainActivity.myNotesList.remove(noteID);
                    MainActivity.listAdapter.notifyDataSetChanged();
                    Toast.makeText(NoteEditorActivity.this, "Note deleted!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        noteTitleEditText = findViewById(R.id.noteEditeTitleEditText);
        noteBodyEditText = findViewById(R.id.noteEditBodyEditText);
        timeOfCreationTitleTextView = findViewById(R.id.timeOfCreationTitleTextView);
        Intent intent = getIntent();
        noteID = intent.getIntExtra("NoteID", -1);
        if(noteID == -1)
        {
            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
            finish();
        }
        editedNote = MainActivity.myNotesList.get(noteID);
        noteTitleEditText.setText(editedNote.getTitle());
        noteBodyEditText.setText(editedNote.getBody());
        timeOfCreationTitleTextView.setText("Created: " + editedNote.getTimeOfCreation());


    }
}