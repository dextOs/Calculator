package com.dextos.xavier.R2D2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.dextos.xavier.R2D2.fragments.fragment_calculator;
import com.dextos.xavier.R2D2.fragments.fragment_game;
import com.dextos.xavier.R2D2.fragments.fragment_music;
import com.dextos.xavier.R2D2.fragments.fragment_profile;
import com.dextos.xavier.R2D2.fragments.fragment_rank;

public class fragment_holder extends AppCompatActivity {
    Fragment  f;
    int id;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)id=savedInstanceState.getInt("Id");
        setContentView(R.layout.activity_fragment_holder);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        f= fragment_selector();
        if(f==null)f= new fragment_profile();
        if(savedInstanceState!=null)f.setArguments(savedInstanceState.getBundle("fragArgs"));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,f)
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_profile:
                                id=1;
                                break;
                            case R.id.action_calc:
                                id=2;
                                break;
                            case R.id.action_music:
                                id=3;
                                break;
                            case R.id.action_memory:
                                id=4;
                                break;
                            case R.id.action_rank:
                                id=5;
                                break;
                        }
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment_selector())
                                .commit();
                        return true;
                    }
                });
    }
    @Nullable

    private Fragment fragment_selector(){
        switch (id) {
            case 1:
                bottomNavigationView.getMenu().findItem(R.id.action_profile).setChecked(true);
                return new fragment_profile();
            case 2:
                bottomNavigationView.getMenu().findItem(R.id.action_calc).setChecked(true);
                return new fragment_calculator();
            case 3:
                bottomNavigationView.getMenu().findItem(R.id.action_music).setChecked(true);
                return new fragment_music();
            case 4:
                bottomNavigationView.getMenu().findItem(R.id.action_memory).setChecked(true);
                return new fragment_game();
            case 5:
                bottomNavigationView.getMenu().findItem(R.id.action_rank).setChecked(true);
                return new fragment_rank();
        }
        return null;
    }

/*
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        id=savedInstanceState.getInt("Id");
    }
    */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle("fragArgs",f.getArguments());
        outState.putInt("Id",id);
        super.onSaveInstanceState(outState);
    }
}
