package com.example.ndpsongs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Song> alSongs;
    CustomAdapter aaSongs;
    DatabaseHelper myDB;
    Button btn5Stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lv = findViewById(R.id.lv);
        btn5Stars = findViewById(R.id.button5Stars);
        alSongs = new ArrayList<>();
        myDB = new DatabaseHelper(this);
        alSongs = myDB.getAllSongsString();
        myDB.close();
        aaSongs = new CustomAdapter(SecondActivity.this, R.layout.custom_listview, alSongs);
        lv.setAdapter(aaSongs);
        aaSongs.notifyDataSetChanged();

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB = new DatabaseHelper(SecondActivity.this);
                alSongs.clear();
                alSongs.addAll(myDB.getAllSongs(5));
                aaSongs = new CustomAdapter(SecondActivity.this, R.layout.custom_listview,alSongs);
                myDB.close();
                lv.setAdapter(aaSongs);
                aaSongs.notifyDataSetChanged();



            }
        });

    }
}
