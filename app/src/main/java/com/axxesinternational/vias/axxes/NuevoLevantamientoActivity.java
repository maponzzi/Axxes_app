package com.axxesinternational.vias.axxes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.axxesinternational.vias.axxes.utils.ConstantsUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class NuevoLevantamientoActivity extends Activity {

    Double LAT;
    Double LNG;
    LocationManager mlocManager;
    String URL;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_levantamiento);

        /* Use the LocationManager class to obtain GPS locations */
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        URL = ConstantsUtils.URL_API_MANTENIMIENTOS;
        queue = Volley.newRequestQueue(this);

    }





    /* Class My Location Listener */
    public class MyLocationListener implements LocationListener {
        NuevoLevantamientoActivity mainActivity;

        public NuevoLevantamientoActivity getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(NuevoLevantamientoActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este mŽtodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la detecci—n de un cambio de ubicacion
            loc.getLatitude();
            //loc.getLongitude();
            LAT = loc.getLatitude();
            LNG = loc.getLongitude();

            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            Log.e("Mi Ubicacion", Text);
            //messageTextView.setText(Text);
            //this.mainActivity.setLocation(loc);
            createFolio();
            mlocManager.removeUpdates(this);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este mŽtodo se ejecuta cuando el GPS es desactivado
            //messageTextView.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este mŽtodo se ejecuta cuando el GPS es activado
            //messageTextView.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Este mŽtodo se ejecuta cada vez que se detecta un cambio en el
            // status del proveedor de localizaci—n (GPS)
            // Los diferentes Status son:
            // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
            // TEMPORARILY_UNAVAILABLE -> Temp˜ralmente no disponible pero se
            // espera que este disponible en breve
            // AVAILABLE -> Disponible
        }

    }/* End of Class MyLocationListener */


    public void GuardarNuevo(View v) {
        MyLocationListener mlocListener = new MyLocationListener();
        mlocListener.setMainActivity(this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                (LocationListener) mlocListener);

    }

    private void createFolio(){

        TextView folio = (TextView) findViewById(R.id.txt_folio);
        TextView observaciones = (TextView) findViewById(R.id.txt_observaciones_area);

        JSONObject mantJson = new JSONObject();

        try {
            mantJson.put("plan", 2);
            //mantJson.put("fol", Long.valueOf("20010115010009"));
            mantJson.put("fol", folio.getText());
            mantJson.put("usuario", 2);
            mantJson.put("obser", observaciones.getText());
            mantJson.put("stt", 0);
            mantJson.put("lat", LAT);
            mantJson.put("lng", LNG);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Conectando", "Creando folio de mantenimiento, espere ...");

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, mantJson, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                Log.e("respuesta", response.toString());
                progressDialog.cancel();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.cancel();

            }
        });

        queue.add(req);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nuevo_levantamiento, menu);
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
