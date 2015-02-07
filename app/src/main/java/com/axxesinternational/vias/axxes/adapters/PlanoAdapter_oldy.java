package com.axxesinternational.vias.axxes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axxesinternational.vias.axxes.R;
import com.axxesinternational.vias.axxes.models.Plano;

import java.util.ArrayList;

/**
 * Created by Miguel on 17/01/15.
 */
public class PlanoAdapter_oldy extends RecyclerView.Adapter<PlanoAdapter_oldy.ViewHolder> {

    ArrayList<Plano> plano;
    int itemLayout;

    public PlanoAdapter_oldy(ArrayList<Plano> plano, int itemLayout){
        this.plano = plano;
        this.itemLayout = itemLayout;
    }



    @Override
    public PlanoAdapter_oldy.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(PlanoAdapter_oldy.ViewHolder viewHolder, int i) {

        Plano miplano = plano.get(i);

        //String stt = miplano.getSttHrj().toString();
        viewHolder.nomHerraje.setText(miplano.getHerrNombre());
        viewHolder.ultFecha.setText(miplano.getfUltMant());
        viewHolder.status.setText(miplano.getSttHrj() + " %");
        viewHolder.ubicado.setText(miplano.getUbicacion());

    }

    @Override
    public int getItemCount() {
        return plano.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nomHerraje;
        TextView ultFecha;
        TextView status;
        TextView ubicado;

        public ViewHolder(View itemView) {
            super(itemView);

            nomHerraje = (TextView) itemView.findViewById(R.id.nom_herraje);
            ultFecha = (TextView) itemView.findViewById(R.id.ult_fecha_value);
            status = (TextView) itemView.findViewById(R.id.stt_value);
            ubicado = (TextView) itemView.findViewById(R.id.ubicado_value);
        }
    }
}