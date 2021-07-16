package com.example.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NotesListAdapter extends ArrayAdapter<Note> {
    Context context;
    int resource;
    LayoutInflater inflater;
    ArrayList<Note> notes;

    public NotesListAdapter(Context context, int resource, ArrayList<Note> notes)
    {   super(context, resource, notes);
        this.context = context;
        this.resource = resource;
        inflater = LayoutInflater.from(this.context);
        this.notes = notes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(this.resource, parent, false);   
            holder.noteTitle = convertView.findViewById(R.id.titleTextView);
            holder.noteBody = convertView.findViewById(R.id.bodyTextView);
            holder.noteTimeOfCreation = convertView.findViewById(R.id.timeOfCreationTextView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.noteTitle.setText(notes.get(position).getTitle());
        holder.noteBody.setText(notes.get(position).getBody());
        holder.noteTimeOfCreation.setText(notes.get(position).getTimeOfCreation());
        return convertView;
    }

    private static class ViewHolder
    {
        TextView noteTitle;
        TextView noteBody;
        TextView noteTimeOfCreation;
    }

}
