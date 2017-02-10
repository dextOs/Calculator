package com.dextos.xavier.R2D2.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dextos.xavier.R2D2.R;

import java.util.ArrayList;

/**
 * Created by Dextos on 08/02/2017.
 */

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.AdapterViewHolder>{
    ArrayList<UserScore> ranking;

    MyCustomAdapter(ArrayList<UserScore> us){
        this.ranking=us;
    }

    @Override
    public MyCustomAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.rowlayout, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCustomAdapter.AdapterViewHolder adapterViewholder, int position) {
        if (ranking!=null && !ranking.isEmpty()) {
            adapterViewholder.user.setText(ranking.get(position).getUserID());
            adapterViewholder.score.setText(ranking.get(position).getScore());
        }
        else {
            adapterViewholder.user.setText("");
            adapterViewholder.score.setText("");
        }
    }

    @Override
    public int getItemCount() {
        //Debemos retornar el tamaño de todos los elementos contenidos en el viewholder
        //Por defecto es return 0 --> No se mostrará nada.
        return ranking.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        /*
        *  Mantener una referencia a los elementos de nuestro ListView mientras el usuario realiza
        *  scrolling en nuestra aplicación. Así que cada vez que obtenemos la vista de un item,
        *  evitamos las frecuentes llamadas a findViewById, la cuál se realizaría únicamente la primera vez y el resto
        *  llamaríamos a la referencia en el ViewHolder, ahorrándonos procesamiento.
        */
        public TextView user;
        public TextView score;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.user = (TextView) itemView.findViewById(R.id.userID);
            this.score = (TextView) itemView.findViewById(R.id.points);
        }
    }
}
