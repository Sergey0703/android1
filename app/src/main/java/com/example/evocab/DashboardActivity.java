package com.example.evocab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        username=findViewById(R.id.dashUserName);
        Intent intent =getIntent();
        if(intent.getExtras()!=null){
            System.out.println("Extra="+intent.getExtras());
            String passedUserName=intent.getStringExtra("data");
            username.setText("Welcom "+passedUserName);
        }

    }
}