package com.dextos.xavier.R2D2.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dextos.xavier.R2D2.R;
import com.dextos.xavier.R2D2.SQLhandler;
import com.dextos.xavier.R2D2.activity_login;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_profile extends Fragment implements View.OnClickListener{

    View rootView;
    Dialog dialog;
    TextView userid,name,loc,hisco;
    List<Address> addressList;
    LocationManager locationManager;
    LocationListener locationListener;
    EditText ed_name;
    public fragment_profile() {
        // Required empty public constructor
    }
    private void init(){
        rootView.findViewById(R.id.btn_gps).setOnClickListener(this);
        rootView.findViewById(R.id.btn_logout).setOnClickListener(this);
        rootView.findViewById(R.id.btn_regist).setOnClickListener(this);
        rootView.findViewById(R.id.imgvw_avatar).setOnClickListener(this);
        userid=(TextView)rootView.findViewById(R.id.txt_user);
        name=(TextView)rootView.findViewById(R.id.txt_name);
        loc=(TextView)rootView.findViewById(R.id.txt_where);
        hisco=(TextView)rootView.findViewById(R.id.txt_hiscore);
        }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        SharedPreferences prefs=this.getActivity().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE);
        userid.setText(prefs.getString("user",""));
        hisco.setText(hisco.getText()+String.valueOf(prefs.getInt("hiscore",0)));
        SQLhandler sql= new SQLhandler(rootView.getContext());
        Cursor c= sql.getProfileInfo(prefs.getString("user",""));
        c.moveToFirst();
        if(!c.isNull(c.getColumnIndex("Name")))name.setText(c.getString(c.getColumnIndex("Name")));
        if(!c.isNull(c.getColumnIndex("Avatar"))) Picasso.with(rootView.getContext())
                .load((c.getString(c.getColumnIndex("Avatar"))))
                .into((ImageView)rootView.findViewById(R.id.imgvw_avatar));
        c.close();
        sql.close();
        return rootView;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_gps:
                addressList = null;
                locationManager = (LocationManager) rootView.getContext()
                        .getSystemService(Context.LOCATION_SERVICE);
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Geocoder gc = new Geocoder(rootView.getContext());
                        try {
                            //5 mxresults
                            addressList = gc.getFromLocation(location.getLatitude(),
                                    location.getLongitude(), 5);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < addressList.size(); ++i) {
                            TextView t = (TextView) rootView.findViewById(R.id.txt_where);
                            if (i == 0) t.setText("");
                            t.setText(t.getText() + "\n" + addressList.get(i).getAddressLine(0));
                        }
                    }
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };
                if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {


                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                    return;
                }
                //provider, tiempo, distancia, listener
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                break;
            case R.id.btn_regist:
                dialog= new Dialog(rootView.getContext());
                dialog.setContentView(R.layout.name_dialog);
                dialog.setTitle(getResources().getText(R.string.plsname));
                dialog.findViewById(R.id.btn_apply).setOnClickListener(this);
                ed_name= (EditText) dialog.findViewById(R.id.etxt_name);
                dialog.show();
                break;
            case R.id.imgvw_avatar:
                /*int permissionCheck = ContextCompat.checkSelfPermission(,
                        Manifest.permission.READ_EXTERNAL_STORAGE);*/
                Intent getImageAsContent = new Intent(Intent.ACTION_GET_CONTENT, null);
                getImageAsContent.setType("image/*");
                startActivityForResult(getImageAsContent, 1);
                break;
            case R.id.btn_logout:
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
                break;
            case R.id.btn_apply:
                SharedPreferences p=this.getActivity().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE);
                SQLhandler sql= new SQLhandler(rootView.getContext());
                sql.updateName(p.getString("user",null),ed_name.getText().toString());
                sql.close();
                name.setText(ed_name.getText());
                dialog.cancel();
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Como en este caso los 3 intents hacen lo mismo, si el estado es correcto recogemos el resultado
        //Aún así comprobamos los request code. Hay que tener total control de lo que hace nuestra app.
        if(resultCode == RESULT_OK){
            if(requestCode >= 1 && requestCode <= 3){
                data.getData();
                Uri selectedImage = data.getData();
                SQLhandler sql= new SQLhandler(rootView.getContext());
                SharedPreferences prefs=this.getActivity().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE);
                sql.updateAvatar(prefs.getString("user",""),selectedImage.toString());
                Picasso.with(rootView.getContext()).load(selectedImage).into((ImageView)rootView.findViewById(R.id.imgvw_avatar));
            }
        }
    }
}
