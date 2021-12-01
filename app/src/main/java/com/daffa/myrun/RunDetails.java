package com.daffa.myrun;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

//activity to show details when user clicks on table entry
public class RunDetails extends AppCompatActivity {
    MyRunDBHandler MyRunDB;
    TextView totalDistanceView;
    TextView totalTimeView;
    TextView maxSpeedView;
    TextView ratingView;
    TextView commentsView;

    RunRecord runData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_details);

        Bundle extras = getIntent().getExtras();

        MyRunDB = new MyRunDBHandler(this, null, null, 1);

        runData = new RunRecord();

        putDataInRecord(extras.getInt("id"));

        totalDistanceView = findViewById(R.id.DetailsDistanceValue);

        displayDistance(runData.getTotalDistance(), totalDistanceView);

        totalTimeView = findViewById(R.id.DetailsTime);

        displayTime(runData.getTotalTime(), totalTimeView);

        maxSpeedView = findViewById(R.id.DetailsMaxSpeed);

        maxSpeedView.setText(String.format("%.2f m/s", runData.getMaxSpeed()));

        ratingView = findViewById(R.id.DetailsRatingValue);

        ratingView.setText(runData.getRunRating());

        commentsView = findViewById(R.id.DetailsCommentsValue);

        commentsView.setText(runData.getComments());
    }

    public void clickBack(View view){
        finish();
    }

    public void putDataInRecord(int id){
        Cursor records;

        String selection = MyRunDB._ID + " = " + id;

        records = MyRunDB.queryID(selection);




        if (records.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else{
            records.moveToFirst();
            runData.setTotalDistance(records.getFloat(4));
            runData.setTotalTime(records.getLong(2));
            runData.setMaxSpeed(records.getFloat(3));
            runData.setRunRating(records.getString(5));
            runData.setComments(records.getString(6));
        }
    }

    //conversion from m to km if necessary
    private void displayDistance(float totalDistance, TextView valueView) {
        if(totalDistance>1000){
            valueView.setText(String.format("%.2f km", totalDistance/1000));
        }
        else{
            valueView.setText(String.format("%.2f m", totalDistance));
        }
    }

    //formatting of time if necessary
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
}