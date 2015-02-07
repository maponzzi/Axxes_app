package com.axxesinternational.vias.axxes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.axxesinternational.vias.axxes.adapters.MantAbiertoAdapter;
import com.axxesinternational.vias.axxes.adapters.PlanoActualAdapter;
import com.axxesinternational.vias.axxes.models.MantAbierto;
import com.axxesinternational.vias.axxes.models.Plano;

import com.axxesinternational.vias.axxes.utils.ConstantsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class DetallePlanoActivityoldy extends Activity {

    ArrayList<Plano> dataset;
    ListView lvPlano;
    ArrayList<MantAbierto> datasetAbiertos;
    GridView gvMantAbiertos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_plano_old);

        Bundle bundle = getIntent().getExtras();
        String codB = bundle.getString("codB");

        lvPlano = (ListView) findViewById(R.id.my_list_view_plano);
        gvMantAbiertos = (GridView) findViewById(R.id.mant_abiertos_grid_view);
        //id = R.layout.row_plano;


        String URL = ConstantsUtils.URL_API_PLANO + codB + "/" + ConstantsUtils.GET_JSON;
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Conectando", "Buscando herraje, espere ...");

        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response) {

                Log.e("respuesta", response.toString());
                dataset = parser(response);
                progressDialog.cancel();
                updatePlanoListView(dataset);

                datasetAbiertos = parserAbiertos(response);
                updateMantAbiertos(datasetAbiertos);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.cancel();

            }
        });

        queue.add(req);

    }

    private void updatePlanoListView(ArrayList<Plano> Plano){
        lvPlano.setAdapter(new PlanoActualAdapter(this, R.layout.row_plano, dataset));
    }

    private void updateMantAbiertos(ArrayList<MantAbierto> MantAbiertos) {
        gvMantAbiertos.setAdapter(new MantAbiertoAdapter(this, R.layout.grid_element, MantAbiertos));
    }


    public ArrayList<Plano> parser (JSONArray response){
        ArrayList<Plano> planoAux = new ArrayList<Plano>();

        Plano plano = new Plano();
        try {
            JSONObject jsonPlano = (JSONObject) response.get(0);
            plano.setHerrNombre(jsonPlano.getJSONObject("herr").getString("nombre"));
            plano.setfUltMant(jsonPlano.getString("fult"));
            plano.setSttHrj(jsonPlano.getString("stt_hrj"));
            plano.setUbicacion(jsonPlano.getString("ubic"));

            //Log.e("plano", plano.toString());
            planoAux.add(plano);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return planoAux;

    }

    public ArrayList<MantAbierto> parserAbiertos (JSONArray response){

        ArrayList<MantAbierto> mantAbiertos = new ArrayList<MantAbierto>();

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



    public void onClickNuevoHerraje(View v) {
        Intent intent = new Intent(this, NuevoLevantamientoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_herraje, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}