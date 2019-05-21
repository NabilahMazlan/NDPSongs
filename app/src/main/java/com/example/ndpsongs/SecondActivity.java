package com.example.ndpsongs;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import android.content.Intent;

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

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SecondActivity.this);
                dialogBuilder.setTitle("Delete?");
                dialogBuilder.setMessage("Are you sure you want to delete?");
                dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            myDB.deleteSong(alSongs.get(position).getId());
                            alSongs.remove(position);
                            aaSongs.notifyDataSetChanged();
                            Toast.makeText(SecondActivity.this, "Song Deleted", Toast.LENGTH_SHORT).show();


                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();



                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SecondActivity.this, ModifyActivity.class);
                int id = alSongs.get(i).getId();
                String title = alSongs.get(i).getTitle();
                String singers = alSongs.get(i).getSingers();
                int year = alSongs.get(i).getYear();
                int stars = alSongs.get(i).getStars();

                Song songs = new Song(id, title, singers, year, stars);
                intent.putExtra("data", songs);
                startActivityForResult(intent,9);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    //Commiting Change

}
