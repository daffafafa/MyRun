package com.daffa.myrun;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

//activity that handles displaying summaries from "My History" section
public class HistorySummary extends AppCompatActivity {
    TextView title;
    String titleText;
    int queryType;
    int recordCount;

    float totalDistance;
    float avgDistance;

    float avgMaxSpeed;

    long totalTime;
    long avgTime;

    MyRunDBHandler MyRunDB;

    ArrayList<Long> time;
    ArrayList<Float> maxSpeed;
    ArrayList<Float> distance;
    ArrayList<String> rating;

    TextView totalDistanceView;
    TextView totalRunsView;
    TextView avgDistanceView;
    TextView longestDistanceView;

    TextView highestMaxSpeedView;
    TextView avgMaxSpeedView;

    TextView goodCount;

    TextView totalTimeView;
    TextView avgTimeView;

    String month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_summary);

        Bundle extras = getIntent().getExtras();

        title = findViewById(R.id.HistorySummaryTitle);

        titleText = extras.getString("title");

        title.setText(titleText);

        MyRunDB = new MyRunDBHandler(this, null, null, 1);

        time = new ArrayList<>();
        maxSpeed = new ArrayList<>();
        distance = new ArrayList<>();
        rating = new ArrayList<>();

        if (titleText.equals("All Time Summary")){
            queryType = 1;

            putDataInArrays(queryType);        }

        else{
            queryType = 2;
            month = extras.getString("month");

            putDataInArrays(queryType);
        }

        if(recordCount>0){
            totalDistance = sumArrayListFloat(distance);
            totalDistanceView = findViewById(R.id.TotalDistanceRanValue);
            displayDistance(totalDistance, totalDistanceView);

            totalRunsView = findViewById(R.id.TotalRunsValue);
            totalRunsView.setText(String.valueOf(recordCount));

            avgDistance = totalDistance/recordCount;
            avgDistanceView = findViewById(R.id.avgDistanceValue);
            displayDistance(avgDistance, avgDistanceView);

            longestDistanceView = findViewById(R.id.longestDistanceValue);
            displayDistance(Collections.max(distance), longestDistanceView);

            highestMaxSpeedView = findViewById(R.id.HighestMaxSpeedValue);
            highestMaxSpeedView.setText(String.format("%.2f m/s", Collections.max(maxSpeed)));

            avgMaxSpeed = sumArrayListFloat(maxSpeed)/recordCount;
            avgMaxSpeedView = findViewById(R.id.avgMaxSpeedValue);
            avgMaxSpeedView.setText(String.format("%.2f m/s", avgMaxSpeed));

            goodCount = findViewById(R.id.GoodRunsValue);
            goodCount.setText(String.valueOf(countGood(rating)));

            totalTime = sumArrayListLong(time);
            totalTimeView = findViewById(R.id.totalTimeValue);
            displayTime(totalTime, totalTimeView);

            avgTime = totalTime/recordCount;
            avgTimeView = findViewById(R.id.avgTimeValue);
            displayTime(avgTime, avgTimeView);
        }





    }

    //makes necessary conversion from m to km and displays in the view
    private void displayDistance(float totalDistance, TextView valueView) {
        if(totalDistance>1000){
            valueView.setText(String.format("%.2f km", totalDistance/1000));
        }
        else{
            valueView.setText(String.format("%.2f m", totalDistance));
        }
    }

    //makes necessary formatting according to amount of time
    private void displayTime(long totalTime, TextView valueView) {
        if(totalTime>3600000){
            valueView.setText(String.format("%02dh %02dm %02ds", TimeUnit.MILLISECONDS.toHours(totalTime),
                    TimeUnit.MILLISECONDS.toMinutes(totalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTime)),
                    TimeUnit.MILLISECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime))));
        }
        else{
            valueView.setText(String.format("%02dm %02ds",
                    TimeUnit.MILLISECONDS.toMinutes(totalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTime)),
                    TimeUnit.MILLISECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime))));
        }
    }

    public void clickFinish(View view){
        finish();
    }

    //puts data in arrayLists for further calculations
    public void putDataInArrays(int queryType){
        Cursor records;
        if (queryType == 1){
            records = MyRunDB.readAllData();
        }
        else{

            String selection = "strftime(\'%Y\',"+ MyRunDB.DATE + ") = strftime(\'%Y\',date(\'now\')) AND  strftime(\'%m\',"+ MyRunDB.DATE + ") = strftime(\'%m\',date(" + month + "))";

            records = MyRunDB.readMonthData(selection);
        }
        recordCount = records.getCount();

        if (recordCount == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else{
            while (records.moveToNext()){
                time.add(records.getLong(2));
                maxSpeed.add(records.getFloat(3));
                distance.add(records.getFloat(4));
                rating.add(records.getString(5));
            }
        }
    }

    public float sumArrayListFloat(ArrayList<Float> values){
        float sum = 0;
        for(int i = 0; i < values.size(); i++)
            sum += values.get(i);
        return sum;
    }

    public long sumArrayListLong(ArrayList<Long> values){
        long sum = 0;
        for(int i = 0; i < values.size(); i++)
            sum += values.get(i);
        return sum;
    }

    public int countGood(ArrayList<String> values){
        int count = 0;
        for(int i = 0; i < values.size(); i++)
            if(values.get(i).equals("good")){
                count++;
            }
            else{
                continue;
            }
        return count;
    }
}