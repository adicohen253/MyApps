package com.example.mymusicplayer;


import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Song> songs = new ArrayList<>();
    int lastSongPosition = -1;

    private void buildSongPlayers()
    {
        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            try {
                int id = field.getInt(null);
                String name = getResources().getResourceEntryName(id);
                songs.add(new Song(name, id, MediaPlayer.create(this, id)));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildSongPlayers();
        ListView listView = findViewById(R.id.songsListView);
        SongsAdapter adapter = new SongsAdapter(this, R.layout.song_layout, songs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            if(lastSongPosition != -1)
            {
                songs.get(lastSongPosition).getMediaPlayer().pause();
                songs.get(lastSongPosition).getMediaPlayer().seekTo(0);

            }
            songs.get(i).getMediaPlayer().start();
            lastSongPosition = i;
        });

    }
}