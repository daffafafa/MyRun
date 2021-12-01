package com.daffa.myrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//activity to select month summaries
public class MonthMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_menu);
    }

    public void January(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "January Summary");
        viewByMonth.putExtra("month", "01");

        startActivity(viewByMonth);
    }

    public void February(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "February Summary");
        viewByMonth.putExtra("month", "02");

        startActivity(viewByMonth);
    }

    public void March(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "March Summary");
        viewByMonth.putExtra("month", "03");

        startActivity(viewByMonth);
    }

    public void April(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "April Summary");
        viewByMonth.putExtra("month", "04");

        startActivity(viewByMonth);
    }

    public void May(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "May Summary");
        viewByMonth.putExtra("month", "05");

        startActivity(viewByMonth);
    }

    public void June(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "June Summary");
        viewByMonth.putExtra("month", "06");

        startActivity(viewByMonth);
    }

    public void July(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "July Summary");
        viewByMonth.putExtra("month", "07");

        startActivity(viewByMonth);
    }

    public void August(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "August Summary");
        viewByMonth.putExtra("month", "08");

        startActivity(viewByMonth);
    }

    public void September(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "September Summary");
        viewByMonth.putExtra("month", "09");

        startActivity(viewByMonth);
    }

    public void October(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "October Summary");
        viewByMonth.putExtra("month", "10");

        startActivity(viewByMonth);
    }

    public void November(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "November Summary");
        viewByMonth.putExtra("month", "11");

        startActivity(viewByMonth);
    }

    public void December(View view){
        Intent viewByMonth = new Intent(this, HistorySummary.class);

        viewByMonth.putExtra("title", "December Summary");
        viewByMonth.putExtra("month", "12");

        startActivity(viewByMonth);
    }

}