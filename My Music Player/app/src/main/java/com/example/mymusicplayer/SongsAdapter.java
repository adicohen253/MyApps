package com.example.mymusicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SongsAdapter extends ArrayAdapter<Song> {
    Context context;
    int resource;
    LayoutInflater inflater;
    ArrayList<Song> songs;


    public SongsAdapter(Context context, int resource, ArrayList<Song> songs) {
        super(context, resource, songs);
        this.context = context;
        this.resource = resource;
        this.inflater = LayoutInflater.from(context);
        this.songs = songs;

    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.duration = convertView.findViewById(R.id.duration);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();

        }

        holder.name.setText(songs.get(position).getName().replace("_", " "));
        holder.duration.setText(songs.get(position).getDuration());
        return convertView;
    }


    static class ViewHolder
    {
        TextView name;
        TextView duration;
    }
}
