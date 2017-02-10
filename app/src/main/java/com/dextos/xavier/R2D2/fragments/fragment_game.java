package com.dextos.xavier.R2D2.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dextos.xavier.R2D2.R;
import com.dextos.xavier.R2D2.SQLhandler;
import com.dextos.xavier.R2D2.activity_login;
import com.example.material.joanbarroso.flipper.CoolImageFlipper;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_game extends Fragment implements View.OnClickListener{
    //--------------------------------Public Variables----------------------------------------------
//intentar quitar el
    TextView score;
    View rootView;
    int points,id_imgv,id_nxt,id_pht,pairs,base;
    Vector<Integer> img_id;
    boolean first;
    //-------------------------------Class methods--------------------------------------------------
    public fragment_game() {
        // Required empty public constructor
    }
    ImageView im_aa,im_ab,im_ac,im_ad
            ,im_ba,im_bb,im_bc,im_bd
            ,im_ca,im_cb,im_cc,im_cd
            ,im_da,im_db,im_dc,im_dd;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
            points=0;
            first=true;
            fill();
            Collections.shuffle(img_id);
        super.onCreate(savedInstanceState);
    }

    private void init() {
        base= (R.drawable.base);
        score=(TextView)rootView.findViewById(R.id.txt_score);
        im_aa=(ImageView) rootView.findViewById(R.id.imgv_aa);
        im_ba=(ImageView) rootView.findViewById(R.id.imgv_ba);
        im_ca=(ImageView) rootView.findViewById(R.id.imgv_ca);
        im_da=(ImageView) rootView.findViewById(R.id.imgv_da);
        im_ab=(ImageView) rootView.findViewById(R.id.imgv_ab);
        im_bb=(ImageView) rootView.findViewById(R.id.imgv_bb);
        im_cb=(ImageView) rootView.findViewById(R.id.imgv_cb);
        im_db=(ImageView) rootView.findViewById(R.id.imgv_db);
        im_ac=(ImageView) rootView.findViewById(R.id.imgv_ac);
        im_bc=(ImageView) rootView.findViewById(R.id.imgv_bc);
        im_cc=(ImageView) rootView.findViewById(R.id.imgv_cc);
        im_dc=(ImageView) rootView.findViewById(R.id.imgv_dc);
        im_ad=(ImageView) rootView.findViewById(R.id.imgv_ad);
        im_bd=(ImageView) rootView.findViewById(R.id.imgv_bd);
        im_cd=(ImageView) rootView.findViewById(R.id.imgv_cd);
        im_dd=(ImageView) rootView.findViewById(R.id.imgv_dd);
        im_aa.setOnClickListener(this);
        im_ba.setOnClickListener(this);
        im_ca.setOnClickListener(this);
        im_da.setOnClickListener(this);
        im_ab.setOnClickListener(this);
        im_bb.setOnClickListener(this);
        im_cb.setOnClickListener(this);
        im_db.setOnClickListener(this);
        im_ac.setOnClickListener(this);
        im_bc.setOnClickListener(this);
        im_cc.setOnClickListener(this);
        im_dc.setOnClickListener(this);
        im_ad.setOnClickListener(this);
        im_bd.setOnClickListener(this);
        im_cd.setOnClickListener(this);
        im_dd.setOnClickListener(this);
    }
    private void fill(){
        img_id=new Vector<Integer>(16);
        img_id.add(0,(R.drawable.a));
        img_id.add(1,(R.drawable.b));
        img_id.add(2,(R.drawable.c));
        img_id.add(3,(R.drawable.d));
        img_id.add(4,(R.drawable.e));
        img_id.add(5,(R.drawable.f));
        img_id.add(6,(R.drawable.g));
        img_id.add(7,(R.drawable.h));
        img_id.add(8,(R.drawable.a));
        img_id.add(9,(R.drawable.b));
        img_id.add(10,(R.drawable.c));
        img_id.add(11,(R.drawable.d));
        img_id.add(12,(R.drawable.e));
        img_id.add(13,(R.drawable.f));
        img_id.add(14,(R.drawable.g));
        img_id.add(15,(R.drawable.h));
    }
    private ImageView getim(int id){
        switch (id){
            case R.id.imgv_aa:
                return im_aa;
            case R.id.imgv_ab:
                return im_ab;
            case R.id.imgv_ac:
                return im_ac;
            case R.id.imgv_ad:
                return im_ad;
            case R.id.imgv_ba:
                return im_ba;
            case R.id.imgv_bb:
                return im_bb;
            case R.id.imgv_bc:
                return im_bc;
            case R.id.imgv_bd:
                return im_bd;
            case R.id.imgv_ca:
                return im_ca;
            case R.id.imgv_cb:
                return im_cb;
            case R.id.imgv_cc:
                return im_cc;
            case R.id.imgv_cd:
                return im_cd;
            case R.id.imgv_da:
                return im_da;
            case R.id.imgv_db:
                return im_db;
            case R.id.imgv_dc:
                return im_dc;
            case R.id.imgv_dd:
                return im_dd;
        }
        return null;
    }
    private void change_score(){
        score.setText(getResources().getText(R.string.score).toString()+String.valueOf(points));
    }
    private void reset_img(ImageView i) {
        i.setClickable(true);
        i.setVisibility(View.VISIBLE);
        i.setImageDrawable(getResources().getDrawable(base));
    }
    private void img_flush(ImageView i) {
        i.setVisibility(View.INVISIBLE);
        i.setClickable(false);
    }
    private void reset_lay() {
        first = true;
        points = 0;
        pairs = 0;
        change_score();
        reset_img(im_aa);
        reset_img(im_ba);
        reset_img(im_ca);
        reset_img(im_da);
        reset_img(im_ab);
        reset_img(im_bb);
        reset_img(im_cb);
        reset_img(im_db);
        reset_img(im_ac);
        reset_img(im_bc);
        reset_img(im_cc);
        reset_img(im_dc);
        reset_img(im_ad);
        reset_img(im_bd);
        reset_img(im_cd);
        reset_img(im_dd);
    }
    private void win() {
        SharedPreferences prefs=this.getActivity().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE);
        SQLhandler SQL= new SQLhandler(rootView.getContext());
        SQL.insertScore(prefs.getString("user",null),points);
        SQL.close();
        int hi=prefs.getInt("hiscore",0);
        String msg;
        if( hi==0 || hi > points ){
            prefs.edit().putInt("hiscore",points).apply(); // actualizar hiscore en prefs, luego la guardaremos en el SQL(al cerrar)
            msg=(getResources().getText(R.string.score)+String.valueOf(points)+"\n"+getResources().getText(R.string.newrecord));
        }
        else msg=(getResources().getText(R.string.score)+String.valueOf(points));
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(rootView.getContext());
        alertDialogBuilder.setTitle(getResources().getText(R.string.congrats));
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(getResources().getText(R.string.replay),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {dialog.cancel();}
                        }
                );
        alertDialogBuilder.create().show();
        Collections.shuffle(img_id);
        reset_lay();
    }
    private void clicable(ImageView i, boolean b){
        if(i.getVisibility()!=View.INVISIBLE) i.setClickable(b);
    }
    private void allclicable(boolean b){
        clicable(im_aa,b);
        clicable(im_ba,b);
        clicable(im_ca,b);
        clicable(im_da,b);
        clicable(im_ab,b);
        clicable(im_bb,b);
        clicable(im_cb,b);
        clicable(im_db,b);
        clicable(im_ac,b);
        clicable(im_bc,b);
        clicable(im_cc,b);
        clicable(im_dc,b);
        clicable(im_ad,b);
        clicable(im_bd,b);
        clicable(im_cd,b);
        clicable(im_dd,b);
    }
    private Integer get_img_id(int imgv_id){
        switch (imgv_id){
            case R.id.imgv_aa:
                return img_id.get(0);
            case R.id.imgv_ab:
                return img_id.get(1);
            case R.id.imgv_ac:
                return img_id.get(2);
            case R.id.imgv_ad:
                return img_id.get(3);
            case R.id.imgv_ba:
                return img_id.get(4);
            case R.id.imgv_bb:
                return img_id.get(5);
            case R.id.imgv_bc:
                return img_id.get(6);
            case R.id.imgv_bd:
                return img_id.get(7);
            case R.id.imgv_ca:
                return img_id.get(8);
            case R.id.imgv_cb:
                return img_id.get(9);
            case R.id.imgv_cc:
                return img_id.get(10);
            case R.id.imgv_cd:
                return img_id.get(11);
            case R.id.imgv_da:
                return img_id.get(12);
            case R.id.imgv_db:
                return img_id.get(13);
            case R.id.imgv_dc:
                return img_id.get(14);
            case R.id.imgv_dd:
                return img_id.get(15);
        }
        return -1;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        rootView=inflater.inflate(R.layout.fragment_game, container, false);
        init();
        return rootView;
    }
    private void flip(ImageView i, int pht) {
        CoolImageFlipper flipper = new CoolImageFlipper(getContext());
        flipper.flipImage(getResources().getDrawable(pht),i);
        //Picasso.with(rootView.getContext()).load(pht).into(i);
    }
    //-----------------------View_Listener----------------------------------------------------------
    @Override
    public void onClick(View v) {
        rootView.findViewById(v.getId()).setClickable(false);
        if(first){
            id_imgv=v.getId();
            id_pht=get_img_id(id_imgv);
            flip(getim(id_imgv),id_pht);
            first=false;
        }
        else{
            allclicable(false);
            id_nxt=v.getId();
            if(id_pht==get_img_id(v.getId())){
                pairs++;
                flip(getim(v.getId()),get_img_id(v.getId()));
                new CountDownTimer(2000,1000) {
                    public void onTick(long millisUntilFinished) {}
                    public void onFinish() {
                        img_flush(getim(id_imgv));
                        img_flush(getim(id_nxt));
                        if(pairs!=8) allclicable(true);
                        if(pairs==8)win();
                    }
                    }.start();
            }
            else {
                flip(getim(v.getId()),get_img_id(v.getId()));
                new CountDownTimer(500,1000) {
                    public void onTick(long millisUntilFinished) {}
                    public void onFinish() {
                        flip(getim(id_imgv),base);
                        flip(getim(id_nxt),base);
                        rootView.findViewById(id_imgv).setClickable(true);
                        rootView.findViewById(id_nxt).setClickable(true);
                        allclicable(true);
                    }
                }.start();
            }
            points++;
            change_score();
            first=true;
        }
    }

    //---------------------------MENU---------------------------------------------------------------
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.game_menu,menu);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isChecked())item.setChecked(false);
        else item.setChecked(true);

        switch (item.getItemId()){
            case R.id.mit_restar:
                Collections.shuffle(img_id);
                reset_lay();
                return true;
            case R.id.mit_worldchamp:
                Intent nav= new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/la6vK6hI7rM"));
                startActivity(nav);
                return true;
            case R.id.mit_logout:
                SharedPreferences prefs=this.getActivity().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE);
                SQLhandler SQL= new SQLhandler(rootView.getContext());
                SQL.updateToast(prefs.getString("user",null),prefs.getBoolean("toast",true));
                SQL.updateHiscore(prefs.getString("user",null),prefs.getInt("Hiscore",0));
                prefs.edit().putBoolean("log_in",false).apply();
                prefs.edit().putString("user",null).apply();
                SQL.close();
                Intent intent = new Intent(getActivity().getApplicationContext(), activity_login.class);
                startActivity(intent);
                getActivity().finish();
                return true;
            case R.id.mit_developer:
                Intent navd= new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/UhCGYUKeinY"));
                startActivity(navd);
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
