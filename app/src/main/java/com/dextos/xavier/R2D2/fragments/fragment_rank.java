package com.dextos.xavier.R2D2.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dextos.xavier.R2D2.R;
import com.dextos.xavier.R2D2.SQLhandler;
import com.dextos.xavier.R2D2.activity_login;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_rank extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    private View rootView;
    ArrayList<UserScore> us;
    public fragment_rank() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        rootView= inflater.inflate(R.layout.fragment_rank, container, false);
        // Inflate the layout for this fragment
        //findViewById del layout activity_main
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        //LinearLayoutManager necesita el contexto de la Activity.
        //El LayoutManager se encarga de posicionar los items dentro del recyclerview
        //Y de definir la politica de reciclaje de los items no visibles.
        mLinearLayout = new LinearLayoutManager(rootView.getContext());
        //Asignamos el LinearLayoutManager al recycler:
        mRecyclerView.setLayoutManager(mLinearLayout);
        //El adapter se encarga de  adaptar un objeto definido en el c�digo a una vista en xml
        //seg�n la estructura definida.
        //Asignamos nuestro custom Adapter
        us= new ArrayList<UserScore>();
        fillrank();
        mRecyclerView.setAdapter(new MyCustomAdapter(us));
        return rootView;
    }
    private void fillrank() {
        SQLhandler sql= new SQLhandler(rootView.getContext());
        Cursor c= sql.getRank();
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                UserScore u=new UserScore();
                u.setScore(String.valueOf(c.getInt(c.getColumnIndex("score"))));
                u.setUserID(c.getString(c.getColumnIndex("UserID")));
                us.add(u);
                c.moveToNext();
            }
        }
        c.close();
        sql.close();
    }
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //----------------------------MENU--------------------------------------------------------------
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.rank_menu, menu);
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
            case R.id.mit_delete:
                prefs.edit().putInt("hiscore",0).apply();
                SQL.resetRank();
                SQL.close();
                us.clear();
                mRecyclerView.setAdapter(new MyCustomAdapter(us));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //-----------------------------LEAKS BOOSTING---------------------------------------------------
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView= null; // now cleaning up!
    }
}
