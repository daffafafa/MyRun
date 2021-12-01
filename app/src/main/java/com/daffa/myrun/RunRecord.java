package com.daffa.myrun;

import java.text.SimpleDateFormat;
import java.util.Date;

//data structure to store database entry
public class RunRecord {
    private int _id;
    private String runName;
    private long totalTime;
    private float maxSpeed;
    private float totalDistance;
    private String runRating;
    private String comments;
    private String runDate;

    public RunRecord(){
    }

    public RunRecord(int id, long totalTime, float maxSpeed, float totalDistance, String runRating,  String comments, String runDate) {
        this._id = id;
        this.totalTime = totalTime;
        this.maxSpeed = maxSpeed;
        this.totalDistance = totalDistance;
        this.runRating = runRating;
        this.comments = comments;
        this.runDate = runDate;
    }

    public RunRecord(long totalTime, float maxSpeed, float totalDistance, String runRating,  String comments) {
        this.totalTime = totalTime;
        this.maxSpeed = maxSpeed;
        this.totalDistance = totalDistance;
        this.runRating = runRating;
        this.comments = comments;
    }

    public RunRecord(long totalTime, float maxSpeed, float totalDistance) {
        this.totalTime = totalTime;
        this.maxSpeed = maxSpeed;
        this.totalDistance = totalDistance;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.runDate = sdf.format(new Date());
    }


    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public void setRunName(String runName){
        this.runName=runName;
    }

    public String getRunName(){
        return this.runName;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public long getTotalTime() {
        return this.totalTime;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getMaxSpeed() {
        return this.maxSpeed;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }

    public float getTotalDistance() {
        return this.totalDistance;
    }

    public void setRunRating(String runRating){
        this.runRating=runRating;
    }

    public String getRunRating(){
        return this.runRating;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments(){
        return this.comments;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public String getRunDate(){
        return this.runDate;
    }
}
