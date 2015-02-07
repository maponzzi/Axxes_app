package com.axxesinternational.vias.axxes.fragments;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.axxesinternational.vias.axxes.R;
import com.axxesinternational.vias.axxes.adapters.MantAbiertoAdapter;
import com.axxesinternational.vias.axxes.adapters.PlanoActualAdapter;
import com.axxesinternational.vias.axxes.db.DBHelper;
import com.axxesinternational.vias.axxes.db.DBOperations;
import com.axxesinternational.vias.axxes.models.MantAbierto;
import com.axxesinternational.vias.axxes.models.Plano;
import com.axxesinternational.vias.axxes.utils.ConstantsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatosPlanoFragment extends BaseVolleyFragment {

    ArrayList<Plano> dataset;
    ListView lvPlano;
    ArrayList<MantAbierto> datasetAbiertos;
    GridView gvMantAbiertos;
    private DBOperations dbOperations;


    public DatosPlanoFragment() {
        // Required empty public constructor
    }

    public void setCodB(String codB) {
        if (codB != null) {
            makeRequest(codB);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_datos_plano, container, false);
        lvPlano = (ListView) view.findViewById(R.id.my_list_view_plano);
        gvMantAbiertos = (GridView) view.findViewById(R.id.mant_abiertos_grid_view);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();
        dbOperations = new DBOperations(getActivity()); //Inicializo las operaciones con la DB
        if (arguments != null) {
            String codB = (String) arguments.get("codB");
            setCodB(codB);
        }

    }

    private void makeRequest(String codB) {

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Conectando", "Buscando herraje, espere ...");
        String URL = ConstantsUtils.URL_API_PLANO + codB + "/" + ConstantsUtils.GET_JSON;

        JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response) {

                Log.e("respuesta", response.toString());
                dataset = parser(response);

                if (dataset.size()>0) {

                    Log.e("respuesta", dataset.toString());
                    updatePlanoListView(dataset);
                    datasetAbiertos = parserAbiertos(response);
                    updateMantAbiertos(datasetAbiertos);

                } else {
                    Log.e("resp", "no hay plano");
                }

                progressDialog.cancel();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.cancel();

            }
        });

        addToQueue(request);

    }


    private void updatePlanoListView(ArrayList<Plano> Plano){
        lvPlano.setAdapter(new PlanoActualAdapter(getActivity(), R.layout.row_plano, Plano));
        savePlanoOnDB(Plano);
    }

    private void updateMantAbiertos(ArrayList<MantAbierto> MantAbiertos) {
        gvMantAbiertos.setAdapter(new MantAbiertoAdapter(getActivity(), R.layout.grid_element, MantAbiertos));
    }


    public ArrayList<Plano> parser (JSONArray response){
        ArrayList<Plano> planoAux = new ArrayList<>();

        Plano plano = new Plano();
        try {

            JSONObject jsonPlano = (JSONObject) response.get(0);
            plano.setCodb(Integer.toString(jsonPlano.getInt("codb")));
            plano.setHerrNombre(jsonPlano.getJSONObject("herr").getString("nombre"));

            if (!jsonPlano.isNull("fult")) {
                plano.setfUltMant(jsonPlano.getString("fult"));
            }

            if (!jsonPlano.isNull("stt_hrj")) {
                plano.setSttHrj(jsonPlano.getString("stt_hrj"));
            }

            plano.setUbicacion(jsonPlano.getString("ubic"));


            //Log.e("plano", plano.toString());
            planoAux.add(plano);

        } catch (JSONException e) {

            e.printStackTrace();
            Log.e("respuesta", "No hay planos registrados con ese codigo");
            Toast.makeText(getActivity(), "No hay planos registrados con el codigo que proporcionaste", Toast.LENGTH_LONG).show();

        }

        return planoAux;

    }


    public ArrayList<MantAbierto> parserAbiertos (JSONArray response){

        ArrayList<MantAbierto> mantAbiertos = new ArrayList<>();

        try {
            JSONObject jsonPlano = (JSONObject) response.get(0);
            JSONArray ArrayMantAbierto = jsonPlano.getJSONArray("mantenimientos");
            JSONObject jsonObjectMantAbierto;

            for (int i=0; i < ArrayMantAbierto.length(); i++) {
                jsonObjectMantAbierto = (JSONObject) ArrayMantAbierto.get(i);

                MantAbierto mant = new MantAbierto();
                //Log.e("Folio", jsonObjectMantAbierto.getString("date"));
                mant.setFol(Long.toString(jsonObjectMantAbierto.getLong("fol")));
                mant.setDate(jsonObjectMantAbierto.getString("date"));
                mant.setObser(jsonObjectMantAbierto.getString("obser"));

                mantAbiertos.add(mant);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mantAbiertos;
    }


    private void savePlanoOnDB(ArrayList<Plano> PlanoActual) {

        try {
            // Primero voy a borrar el registro 1 en plano_actual, este registro unico controla el plano en el que se esta trabajando
            dbOperations.deletePlanoActual();

            // Voy a guardar en mi DB en la tabla plano_actual el plano que estoy leyendo
            Plano miplano = PlanoActual.get(0);
            ContentValues values = new ContentValues();

            values.clear();
            values.put(DBHelper.C_ID_PLANO, 1);
            values.put(DBHelper.C_CODB_PLANO, miplano.getCodb());
            values.put(DBHelper.C_NOMBRE_PLANO, miplano.getHerrNombre());
            values.put(DBHelper.C_ULT_MANT_PLANO, miplano.getfUltMant());
            values.put(DBHelper.C_STATUS_PLANO, miplano.getSttHrj());
            values.put(DBHelper.C_UBICADO_PLANO, miplano.getUbicacion());

            dbOperations.insertOrIgnore(values);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}