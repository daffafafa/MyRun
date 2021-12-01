package com.daffa.myrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//main menu
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //starts tracking run
    public void StartRunActivity(View view) {
        Intent StartRun = new Intent(this, RunActivity.class);


        startActivity(StartRun);
    }

    //view history of past runs
    public void StartHistoryMenu(View view) {
        Intent OpenHistory = new Intent(this, HistoryMenu.class);


        startActivity(OpenHistory);
    }
}