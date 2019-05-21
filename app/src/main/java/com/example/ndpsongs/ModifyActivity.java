package com.example.ndpsongs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.content.Intent;

public class ModifyActivity extends AppCompatActivity {
    Button btnCancel, btnUpdate;
    EditText etId, etTitle, etSinger, etYear;
    RadioGroup rg;
    Song data;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);
        etId = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etSongTitle);
        etSinger = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        rg = findViewById(R.id.rgStars);



        Intent received = getIntent();
        data = (Song) received.getSerializableExtra("data");

        //placing data into the UI Elements
        etId.setText(String.valueOf(data.getId()));
        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYear.setText(String.valueOf(data.getYear()));
        if(data.getStars() == 5){
            rg.check(R.id.rb5);
        }else if(data.getStars() == 4){
            rg.check(R.id.rb4);
        }else if(data.getStars() == 3){
            rg.check(R.id.rb3);
        }else if(data.getStars() == 2){
            rg.check(R.id.rb2);
        }else{
            rg.check(R.id.rb1);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB = new DatabaseHelper(ModifyActivity.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSinger.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                int checkedButton = rg.getCheckedRadioButtonId();
                RadioButton rb = findViewById(checkedButton);
                data.setStars(Integer.parseInt(rb.getText().toString()));
                myDB.updateSong(data);
                myDB.close();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });

    }
}
