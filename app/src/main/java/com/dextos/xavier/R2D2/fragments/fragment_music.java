package com.dextos.xavier.R2D2.fragments;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.dextos.xavier.R2D2.R;
import com.dextos.xavier.R2D2.SQLhandler;
import com.dextos.xavier.R2D2.activity_login;

import static android.app.Activity.RESULT_OK;
import static java.security.AccessController.getContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_music extends Fragment implements View.OnClickListener {

    Boolean pause;
    View rootView;
    Uri selectedSong;
    public fragment_music() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_music, container, false);
        this.setHasOptionsMenu(true);
        pause=false;
        rootView.findViewById(R.id.btn_src).setOnClickListener(this);
        rootView.findViewById(R.id.btn_pp).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pp:
                if (pause) {
                    getActivity().stopService((new Intent(getActivity().getApplicationContext(), Music.class)));
                    pause = false;
                }
                else {
                    getActivity().startService(new Intent(getActivity().getApplicationContext(), Music.class));
                    pause = true;
                }
                break;

            case R.id.btn_src:
                if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && getContext().checkSelfPermission(Manifest.permission.WAKE_LOCK)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WAKE_LOCK}, 0);
                }
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode >= 1 && requestCode <= 3) {
                //data.getData();
                selectedSong = data.getData();
                this.getActivity().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE)
                        .edit().putString("uri",selectedSong.toString()).apply();
            }
        }
    }
    //-------------------------------------------MENU
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_music, menu);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences prefs=this.getActivity().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE);
        SQLhandler SQL= new SQLhandler(rootView.getContext());
        switch (item.getItemId()){
            case R.id.mit_logout:
                SQL.updateToast(prefs.getString("user",null),prefs.getBoolean("toast",true));
                SQL.updateHiscore(prefs.getString("user",null),prefs.getInt("Hiscore",0));
                prefs.edit().putBoolean("log_in",false).apply();
                prefs.edit().putString("user",null).apply();
                SQL.close();
                Intent intent = new Intent(getActivity().getApplicationContext(), activity_login.class);
                startActivity(intent);
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
