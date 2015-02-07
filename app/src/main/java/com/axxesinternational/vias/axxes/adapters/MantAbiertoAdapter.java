package com.axxesinternational.vias.axxes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.axxesinternational.vias.axxes.R;
import com.axxesinternational.vias.axxes.models.MantAbierto;

import java.util.ArrayList;

/**
 * Created by Miguel on 18/01/15.
 */
public class MantAbiertoAdapter extends ArrayAdapter<MantAbierto> {

    private Context context;
    private ArrayList<MantAbierto> mantAbiertos;

    public MantAbiertoAdapter(Context context, int resource, ArrayList<MantAbierto> mantAbiertos) {
        super(context, resource, mantAbiertos);

        this.context = context;
        this.mantAbiertos = mantAbiertos;

    }


    static class ViewHolder {
        public TextView folio;
        public TextView date;
        public TextView observacion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_element, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.folio = (TextView) convertView.findViewById(R.id.txt_folio);
            viewHolder.date = (TextView) convertView.findViewById(R.id.txt_date);
            viewHolder.observacion = (TextView) convertView.findViewById(R.id.txt_observacion);

            convertView.setTag(viewHolder);

        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.folio.setText(mantAbiertos.get(position).getFol());
        holder.date.setText(mantAbiertos.get(position).getDate());
        holder.observacion.setText(mantAbiertos.get(position).getObser());

        return convertView;

    }
}