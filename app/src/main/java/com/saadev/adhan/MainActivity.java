package com.saadev.adhan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ServiceCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnAdd;
    SharedPreferences sharedPFile;
    public static final String fileName="myfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd=findViewById(R.id.cardAdd);
        btnAdd.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,Clock_Setting.class);
        startActivity(intent);
    }
}