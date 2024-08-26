package com.example.obdii_reader.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.obdii_reader.R;

public class OnBoardingActivity extends AppCompatActivity {


    private Button bt_getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        bt_getStarted = findViewById(R.id.get_started);
        bt_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivityIntent = new Intent(OnBoardingActivity.this,SignupActivity.class);
                startActivity(startActivityIntent);
            }
        });
    }
}