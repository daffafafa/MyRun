package com.daffa.myrun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


//summary of run allows user to confirm details, add name, rate their run and add comments before adding entry to database
public class Summary extends AppCompatActivity {
    TextView distanceValue;
    TextView maxSpeed;
    TextView time;
    EditText name;
    EditText comments;
    RunRecord runData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Bundle extras = getIntent().getExtras();

        runData = new RunRecord(extras.getLong("totalTime"), extras.getFloat("maxSpeed"), extras.getFloat("totalDistance"));

        distanceValue = findViewById(R.id.DistanceRanValue);
        maxSpeed = findViewById(R.id.MaxSpeedValue);
        time = findViewById(R.id.FinalTimeValue);

        if(runData.getTotalDistance()>1000){
            distanceValue.setText(String.format("%.2f km", runData.getTotalDistance()/1000));
        }
        else{
            distanceValue.setText(String.format("%.2f m", runData.getTotalDistance()));
        }

        maxSpeed.setText(String.format("%.2f m/s", runData.getMaxSpeed()));

        String timeText = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(runData.getTotalTime()) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(runData.getTotalTime())),
                TimeUnit.MILLISECONDS.toSeconds(runData.getTotalTime()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(runData.getTotalTime())));

        time.setText(timeText);
    }

    public void clickGood(View view){
        runData.setRunRating("good");
    }

    public void clickBad(View view){
        runData.setRunRating("bad");
    }

    public void clickFinish(View view){
        MyRunDBHandler MyRunDB = new MyRunDBHandler(this, null, null, 1);

        name = findViewById(R.id.editRunName);

        comments = findViewById(R.id.editComment);

        runData.setRunName(name.getText().toString());

        runData.setComments(comments.getText().toString());

        int run_id = MyRunDB.addRun(runData);

        finish();
    }
}