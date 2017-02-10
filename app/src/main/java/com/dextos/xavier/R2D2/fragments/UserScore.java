package com.dextos.xavier.R2D2.fragments;

/**
 * Created by Dextos on 08/02/2017.
 */

public class UserScore {
    private String UserID;
    private String Score;

    UserScore(String user, String score){

        this.UserID = user;
        this.Score = score;

    }
    UserScore(){

    }


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        this.UserID = userID;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {this.Score = score;
    }
}
