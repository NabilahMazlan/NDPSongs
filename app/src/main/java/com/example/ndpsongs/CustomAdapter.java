package com.example.ndpsongs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    private ArrayList<Song> song;
    private Context context;
    private TextView tvTitle, tvYear,tvSinger;
    private ImageView iv1, iv2, iv3, iv4, iv5;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Song> objects) {
        super(context, resource, objects);

        song = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        tvTitle = convertView.findViewById(R.id.textViewTitle);
        tvSinger = convertView.findViewById(R.id.textViewName);
        tvYear = convertView.findViewById(R.id.textViewYear);
        iv1 = convertView.findViewById(R.id.imgStar1);
        iv2 = convertView.findViewById(R.id.imgStar2);
        iv3 = convertView.findViewById(R.id.imgStar3);
        iv4 = convertView.findViewById(R.id.imgStar4);
        iv5 = convertView.findViewById(R.id.imgStar5);


        Song songs= song.get(position);

        tvTitle.setText(songs.getTitle());
        tvSinger.setText(songs.getSingers());
        tvYear.setText(String.valueOf(songs.getYear()));

        if(songs.getStars() == 1){
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        }else if(songs.getStars() == 2){
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        }else if(songs.getStars() == 3){
            iv3.setImageResource(android.R.drawable.btn_star_big_on);
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        }else if(songs.getStars() == 4){
            iv4.setImageResource(android.R.drawable.btn_star_big_on);
            iv3.setImageResource(android.R.drawable.btn_star_big_on);
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        }else if(songs.getStars() == 5){
            iv5.setImageResource(android.R.drawable.btn_star_big_on);
            iv4.setImageResource(android.R.drawable.btn_star_big_on);
            iv3.setImageResource(android.R.drawable.btn_star_big_on);
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        }

        return convertView;


    }
}
