package com.daffa.myrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//Menu between history options
public class HistoryMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_menu);
    }

    //Views all time summary
    public void allTime(View view){
        Intent viewAllTime = new Intent(this, HistorySummary.class);

        viewAllTime.putExtra("title", "All Time Summary");


        startActivity(viewAllTime);
    }

    //Goes to menu to pick which month
    public void byMonth(View view){
        Intent viewByMonth = new Intent(this, MonthMenu.class);


        startActivity(viewByMonth);
    }

    //quickly check this month's summary
    public void ThisMonth(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "This Month Summary");
        viewByMonth.putExtra("month", "\'now\'");

        startActivity(viewByMonth);
    }

    //open list of all runs
    public void allRuns(View view){
        Intent viewAll = new Intent(this, AllRunsList.class);


        startActivity(viewAll);
    }
}