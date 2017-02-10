package com.dextos.xavier.R2D2;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

import static android.R.attr.data;

public class activity_login extends AppCompatActivity implements View.OnClickListener{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "bQDAe7q0XbQUj1whFLYGqMCjU";
    private static final String TWITTER_SECRET = "W7qB9yk5b3XcdXLkUfsyS7HxWCLTjKo0WO5MdN4DYxSQivIaZm";

    private TwitterLoginButton loginButton;

    Button b_log,b_reg;
    EditText et_user,et_pass;
    SQLhandler SQL;
    SharedPreferences prefs;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = this.getSharedPreferences("com.dextos.xavier.R2D2",Context.MODE_PRIVATE);
        if(prefs.getBoolean("log_in",false))log_in(true,true,0,"");// no hace falta porque ya tiene el valor del toast
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);
        b_log=(Button)findViewById(R.id.btn_log);
        b_reg=(Button)findViewById(R.id.btn_reg);
        et_user=(EditText)findViewById(R.id.etxt_user);
        et_pass=(EditText)findViewById(R.id.etxt_pass);
        b_log.setOnClickListener(this);
        b_reg.setOnClickListener(this);
        SQL= new SQLhandler(getApplicationContext());
        dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        Button b_rety=(Button) dialog.findViewById(R.id.btn_retry);
        b_rety.setOnClickListener(this);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                // si existe hacer log, sino crear user
                Cursor c = SQL.getUserInfo(session.getUserName());
                if (c.moveToNext()){
                    boolean toast=(1==c.getInt(c.getColumnIndex("Toast")));
                    int hiscore=c.getInt(c.getColumnIndex("Hiscore"));
                    c.close();
                    log_in(false,toast,hiscore,session.getUserName());
                }
                else{// TE REGISTRA
                    ContentValues cv= new ContentValues();
                    cv.put("UserID", session.getUserName());
                    SQL.createProfile(cv);
                    cv.put("Pass","");
                    SQL.createUser(cv);
                    log_in(false,true,0,session.getUserName());
                }
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log:
                if(et_user.getText().toString().equals(""))Toast.makeText(getApplicationContext(),getResources().getText(R.string.noemptyID),Toast.LENGTH_SHORT).show();
                else if(et_pass.getText().toString().equals(""))Toast.makeText(getApplicationContext(),getResources().getText(R.string.noemptypass),Toast.LENGTH_SHORT).show();
                else {
                    Cursor c = SQL.getUserInfo(et_user.getText().toString());
                    if (c.moveToNext()){// si el usuario existe
                        if (et_pass.getText().toString().equals(c.getString(c.getColumnIndex("Pass")))) {// si la contraseña coincide
                            boolean toast=(1==c.getInt(c.getColumnIndex("Toast")));
                            int hiscore=c.getInt(c.getColumnIndex("Hiscore"));
                            c.close();
                            log_in(false,toast,hiscore,et_user.getText().toString());
                        } else {
                            dialog.show();
                        }
                    }
                    else {
                        et_pass.setText("");
                        et_user.setText("");
                        Toast.makeText(getApplicationContext(), getResources().getText(R.string.notexist), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.btn_reg:
                if(et_user.getText().toString().equals(""))Toast.makeText(getApplicationContext(),getResources().getText(R.string.noemptyID),Toast.LENGTH_SHORT).show();
                else if(et_pass.getText().toString().equals(""))Toast.makeText(getApplicationContext(),getResources().getText(R.string.noemptypass),Toast.LENGTH_SHORT).show();
                else {
                    ContentValues cv = new ContentValues();
                    cv.put("UserID", et_user.getText().toString());
                    cv.put("Pass", et_pass.getText().toString());
                    if (SQL.createUser(cv) == -1)
                        Toast.makeText(getApplicationContext(), getResources().getText(R.string.existingUser), Toast.LENGTH_SHORT).show();
                    else {
                        cv.clear();
                        cv.put("UserID", et_user.getText().toString());
                        SQL.createProfile(cv);
                        Toast.makeText(getApplicationContext(), getResources().getText(R.string.regsuccess), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_retry:
                et_pass.setText("");
                dialog.dismiss();
                break;
        }
    }
    private void log_in(boolean already_log, boolean toast,int hiscore, String user){
        if(!already_log) {
            prefs.edit().putBoolean("log_in", true).apply();
            prefs.edit().putString("user",user).apply();
            prefs.edit().putBoolean("toast", toast).apply();
            prefs.edit().putInt("hiscore",hiscore).apply();
        }
        Intent intent = new Intent(this, fragment_holder.class);
        startActivity(intent);
        this.finish();
    }
}
