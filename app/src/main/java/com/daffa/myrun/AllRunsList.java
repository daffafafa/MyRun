package com.daffa.myrun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

//Activity that allows user to inspect all runs
public class AllRunsList extends AppCompatActivity {
    RecyclerView AllDataList;

    MyRunDBHandler MyRunDB;
    ArrayList<Integer> _id;
    ArrayList<String> name;
    ArrayList<String> date;

    int recordCount;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_runs_list);

        AllDataList = (RecyclerView) findViewById(R.id.RunsTable);

        MyRunDB = new MyRunDBHandler(this, null, null, 1);
        _id = new ArrayList<>();
        name = new ArrayList<>();
        date = new ArrayList<>();

        //puts displayed data in arrayList first
        putDataInArrays();

        //sets up recyclerView
        customAdapter = new CustomAdapter(this, _id, name, date);
        AllDataList.setAdapter(customAdapter);
        AllDataList.setLayoutManager(new LinearLayoutManager(this));
    }

    //method to put data from cursor to arrayList
    public void putDataInArrays(){
        Cursor records;

        records = MyRunDB.readAllData();


        recordCount = records.getCount();

        if (recordCount == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else{
            while (records.moveToNext()){
                _id.add(records.getInt(0));
                name.add(records.getString(1));
                date.add(records.getString(7));
            }
        }
    }
}