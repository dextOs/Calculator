package com.dextos.xavier.R2D2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by xavier on 27/01/2017.
 */

public class SQLhandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="appusers";
    public static final String TABLE_USERS= "users";
    public static final String TABLE_PROFILE="profile";
    public static final String TABLE_RANK="ranking";
    public SQLhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ TABLE_USERS +" (UserID TEXT PRIMARY KEY, Pass TEXT, Toast INTEGER DEFAULT 1 , Hiscore INTEGER DEFAULT 0);");
        db.execSQL("CREATE TABLE "+ TABLE_PROFILE +"(UserID TEXT PRIMARY KEY, Name TEXT, Avatar TEXT );");
        db.execSQL("CREATE TABLE "+ TABLE_RANK +"(p INTEGER PRIMARY KEY AUTOINCREMENT, UserID TEXT , score INTEGER );");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oV, int nV) {

    }
    public Cursor getUserInfo(String UserID){// SI PASA UN CURSOR VACIO NO EXITE USUARIO
        SQLiteDatabase db= this.getReadableDatabase();
        String [] columns= {"Pass","Toast","Hiscore"};
        String [] where = {UserID};
        return db.query(
                TABLE_USERS,
                columns,
                "UserID=?",
                where,
                null,
                null,
                null);
    }
    public long createUser(ContentValues values){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.insert(
                TABLE_USERS,
                null,
                values
        );
    }//  si no se puede crear devuelve -1
    public long createProfile(ContentValues values){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.insert(
                TABLE_PROFILE,
                null,
                values
        );
    }
    public void updateToast(String UserID,boolean Toast){
        SQLiteDatabase db = this.getWritableDatabase();
        String [] where= {UserID};
        ContentValues cv= new ContentValues();
        if(Toast)cv.put("Toast",1);
        else cv.put("Toast",2);
        db.update(TABLE_USERS,cv,"UserID=?",where);
    }
    public void updateHiscore(String UserID,int Hi){
        SQLiteDatabase db = this.getWritableDatabase();
        String [] where= {UserID};
        ContentValues cv= new ContentValues();
        cv.put("Hiscore",Hi);
        db.update(TABLE_USERS,cv,"UserID=?",where);
        db.close();
    }
    //--------------------RANNK-------------------
    public void insertScore(String u, int s){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("UserID",u);
        cv.put("score",s);
        db.insert(
                TABLE_RANK,
                null,
                cv
        );
        db.close();
    }
    public void resetRank(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE_RANK,null,null);
        ContentValues cv= new ContentValues();
        cv.put("Hiscore",0);
        db.update(TABLE_USERS,cv,null,null);
        db.close();
    }
    public Cursor getRank(){
        SQLiteDatabase db= this.getReadableDatabase();
        String [] columns= {"UserID","score"};
        return db.query(
                TABLE_RANK,
                columns,
                null,
                null,
                null,
                null,
                "score ASC");
    }
    //-------------------PROFILE-----------------
    public Cursor getProfileInfo(String UserID){// SI PASA UN CURSOR VACIO NO EXITE USUARIO
        SQLiteDatabase db= this.getReadableDatabase();
        String [] columns= {"Name","Avatar"};
        String [] where = {UserID};
        return db.query(
                TABLE_PROFILE,
                columns,
                "UserID=?",
                where,
                null,
                null,
                null);
    }
    public void updateName(String UserID,String n){
        SQLiteDatabase db = this.getWritableDatabase();
        String [] where= {UserID};
        ContentValues cv= new ContentValues();
        cv.put("Name",n);
        db.update(TABLE_PROFILE,cv,"UserID=?",where);
        db.close();
    }
    public void updateAvatar(String UserID,String av){
        SQLiteDatabase db = this.getWritableDatabase();
        String [] where= {UserID};
        ContentValues cv= new ContentValues();
        cv.put("Avatar",av);
        db.update(TABLE_PROFILE,cv,"UserID=?",where);
        db.close();
    }
}
