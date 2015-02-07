package com.axxesinternational.vias.axxes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.axxesinternational.vias.axxes.R;
import com.axxesinternational.vias.axxes.models.Plano;
import com.axxesinternational.vias.axxes.utils.DateUtils;

import java.util.ArrayList;


/**
 * Created by Miguel on 18/01/15.
 */
public class PlanoActualAdapter extends ArrayAdapter<Plano> {

    private Context context;
    private ArrayList<Plano> plano;
    private int itemLayout;

    public PlanoActualAdapter(Context context, int itemLayout, ArrayList<Plano> plano) {
        super(context, itemLayout, plano);

        this.context = context;
        this.plano = plano;
        this.itemLayout = itemLayout;
    }

    static class ViewHolder {
        public TextView NomHerraje;
        public TextView fUltMantenimiento;
        public TextView status;
        public TextView ubicacion;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(itemLayout, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.NomHerraje = (TextView) convertView.findViewById(R.id.nom_herraje);
            viewHolder.fUltMantenimiento = (TextView) convertView.findViewById(R.id.ult_fecha_value);
            viewHolder.status = (TextView) convertView.findViewById(R.id.stt_value);
            viewHolder.ubicacion = (TextView) convertView.findViewById(R.id.ubicado_value);

            convertView.setTag(viewHolder);

        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.NomHerraje.setText(plano.get(position).getHerrNombre());

        if (plano.get(position).getfUltMant() != null) {
            holder.fUltMantenimiento.setText(DateUtils.setDateFormat(plano.get(position).getfUltMant()));
        }

        if (plano.get(position).getSttHrj() != null) {
            holder.status.setText(plano.get(position).getSttHrj() + " %");
        }

        holder.ubicacion.setText(plano.get(position).getUbicacion());

        return convertView;

    }
}