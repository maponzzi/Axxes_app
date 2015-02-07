package com.axxesinternational.vias.axxes.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.axxesinternational.vias.axxes.R;
import com.axxesinternational.vias.axxes.adapters.PlanoActualAdapter;
import com.axxesinternational.vias.axxes.db.DBOperations;
import com.axxesinternational.vias.axxes.models.Plano;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatosPlanoNuevoMantFragment extends Fragment {

    ListView lvplano;
    TextView txt_folio;
    private DBOperations dbOperations;

    public DatosPlanoNuevoMantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_datos_plano_nuevo, container, false);
        lvplano = (ListView) view.findViewById(R.id.my_list_view_datos_plano_nuevo);
        txt_folio = (TextView) view.findViewById(R.id.txt_folio);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbOperations = new DBOperations(getActivity());
        ArrayList<Plano> plano = dbOperations.getPlanoActual();
        lvplano.setAdapter(new PlanoActualAdapter(getActivity(), R.layout.row_nuevo_mant, plano));
    }

}
