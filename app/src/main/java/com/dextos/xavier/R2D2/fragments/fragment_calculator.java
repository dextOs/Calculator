package com.dextos.xavier.R2D2.fragments;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dextos.xavier.R2D2.R;
import com.dextos.xavier.R2D2.SQLhandler;
import com.dextos.xavier.R2D2.activity_login;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_calculator extends Fragment implements View.OnClickListener {
    TextView scr;
    boolean isANS;
    View rootView;

    public fragment_calculator() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        rootView= inflater.inflate(R.layout.fragment_calculator, container, false);
        init();
        return rootView;
    }

    private void showError(String msg) {
        SharedPreferences prefs=this.getActivity().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE);
        if(prefs.getBoolean("toast",true)) Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT).show();
        else{
            NotificationCompat.Builder mBuilder=
                    (NotificationCompat.Builder) new NotificationCompat.Builder(getContext())
                            .setSmallIcon(R.drawable.ic_warning_black_24dp)
                            .setContentTitle(getResources().getText(R.string.error).toString())
                            .setContentText(msg);
            NotificationManager mNotificationManager=
                    (NotificationManager) this.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0,mBuilder.build());
        }
    }
    //---------------------------INIT---------------------------------------------------------------
    private void init(){
        scr=(TextView)rootView.findViewById(R.id.txt_screen);
        rootView.findViewById(R.id.btn_zero).setOnClickListener(this);
        rootView.findViewById(R.id.btn_one).setOnClickListener(this);
        rootView.findViewById(R.id.btn_two).setOnClickListener(this);
        rootView.findViewById(R.id.btn_three).setOnClickListener(this);
        rootView.findViewById(R.id.btn_four).setOnClickListener(this);
        rootView.findViewById(R.id.btn_five).setOnClickListener(this);
        rootView.findViewById(R.id.btn_six).setOnClickListener(this);
        rootView.findViewById(R.id.btn_seven).setOnClickListener(this);
        rootView.findViewById(R.id.btn_eight).setOnClickListener(this);
        rootView.findViewById(R.id.btn_nine).setOnClickListener(this);
        rootView.findViewById(R.id.btn_sum).setOnClickListener(this);
        rootView.findViewById(R.id.btn_subs).setOnClickListener(this);
        rootView.findViewById(R.id.btn_mult).setOnClickListener(this);
        rootView.findViewById(R.id.btn_div).setOnClickListener(this);
        rootView.findViewById(R.id.btn_equ).setOnClickListener(this);
        rootView.findViewById(R.id.btn_dot).setOnClickListener(this);
    }
    //---------------------------LISTENER-----------------------------------------------------------
    @Override
    public void onClick(View v) {
        if(isANS) {
            isANS=false;
            scr.setText("");
        }
        switch (v.getId()){
            case R.id.btn_zero:
                scr.setText(scr.getText().toString()+"0");
                break;
            case R.id.btn_one:
                scr.setText(scr.getText().toString()+"1");
                break;
            case R.id.btn_two:
                scr.setText(scr.getText().toString()+"2");
                break;
            case R.id.btn_three:
                scr.setText(scr.getText().toString()+"3");
                break;
            case R.id.btn_four:
                scr.setText(scr.getText().toString()+"4");
                break;
            case R.id.btn_five:
                scr.setText(scr.getText().toString()+"5");
                break;
            case R.id.btn_six:
                scr.setText(scr.getText().toString()+"6");
                break;
            case R.id.btn_seven:
                scr.setText(scr.getText().toString()+"7");
                break;
            case R.id.btn_eight:
                scr.setText(scr.getText().toString()+"8");
                break;
            case R.id.btn_nine:
                scr.setText(scr.getText().toString()+"9");
                break;
            case R.id.btn_sum:
                scr.setText(scr.getText().toString()+"+");
                break;
            case R.id.btn_subs:
                scr.setText(scr.getText().toString()+"-");
                break;
            case R.id.btn_mult:
                scr.setText(scr.getText().toString()+"*");
                break;
            case R.id.btn_div:
                scr.setText(scr.getText().toString()+"/");
                break;
            case R.id.btn_equ:
                String s=scr.getText().toString();
                if(rightspelling(s)) {
                    Double ans=eval(s);
                    if(!Double.isInfinite(ans) && !Double.isNaN(ans)) {
                        scr.setText(Double.toString(ans));
                        isANS=true;
                    }
                    else{
                        showError("No se puede dividir entre zero");
                        scr.setText("");
                    }
                }
                else showError("Syntax Error");
                break;
            case R.id.btn_dot:
                scr.setText(scr.getText().toString()+".");
                break;
            case R.id.btn_erase:
                String e=scr.getText().toString();
                if(e.length()>0) scr.setText(e.substring(0,e.length()-1));
                break;
        }
        if(scr.getLineCount()==2){
            if(v.getId()!=R.id.btn_equ)showError(getResources().getText(R.string.toolong).toString());
            while (scr.getLineCount()>1){scr.setText(scr.getText().toString().substring(0,scr.getText().toString().length()-1));}
        }
    }
    //---------------------------MENU---------------------------------------------------------------
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.inside_menu,menu);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(this.getActivity().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE).getBoolean("toast",true)){
            menu.findItem(R.id.mit_toast).setChecked(true);
        }
        else menu.findItem(R.id.mit_state).setChecked(true);
        super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isChecked())item.setChecked(false);
        else item.setChecked(true);
        SharedPreferences prefs=this.getActivity().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE);
        switch (item.getItemId()){
            case R.id.mit_call:
                String tel= scr.getText().toString();
                if(OnlyNumbers(tel)) {
                    tel="tel:"+tel;
                    Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
                    startActivity(call);
                }
                else showError(getResources().getText(R.string.nocall).toString());
                return true;
            case R.id.mit_logout:
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
            case R.id.mit_nav:
                Intent nav= new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.es"));
                startActivity(nav);
                return true;
            case R.id.mit_state:
                prefs.edit().putBoolean("toast",false).apply();
                return true;
            case R.id.mit_toast:
                prefs.edit().putBoolean("toast",true).apply();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //---------------------------AQUI LA LOGICA DE LA CALCULADORA-----------------------------------
    private boolean OnlyNumbers(String s) {
        return (!s.contains("*") && !s.contains("/") && (!s.contains("+") || s.startsWith("+")) && !s.contains("-") && !s.contains(".") && !s.contains("E"));
    }
    private boolean rightspelling(String s) {
        return !(s.contains("/*") || s.contains("*/") || s.contains("//")|| s.startsWith("*")
                || s.startsWith("/") || s.endsWith("*") || s.endsWith("/")  || s.endsWith(".")
                || s.endsWith("+") || s.endsWith("-") ||s.contains("/*") || s.contains("*/")
                || s.contains("//") || s.contains("*.*")  || s.contains("+.*") || s.contains("-.*")
                || s.contains("/.*") || s.contains("*./") || s.contains("+./") || s.contains("-./")
                || s.contains("/./"));
    }
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;
            void nextChar() {ch = (++pos < str.length()) ? str.charAt(pos) : -1;}
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }
            double parse() {
                nextChar();
                double x = parseExpression();
                return x;
            }
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }
            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }
            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus
                double x = 0;
                int startPos = this.pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                }
                if (eat('E')) x = Math.pow(x, parseFactor()); // exponentiation
                return x;
            }
        }.parse();
    }
    //-----------------------------LEAKS BOOSTING---------------------------------------------------
    @Override
    public void onDestroyView() {
        super.onDestroyView();
         rootView= null; // now cleaning up!
    }

}
