package com.axxesinternational.vias.axxes;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.axxesinternational.vias.axxes.fragments.DatosPlanoFragment;


public class DetallePlanoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_plano);

        Intent i = getIntent();
        String codB = i.getStringExtra("codB");

        FragmentManager fm = (getFragmentManager());
        DatosPlanoFragment fragment = (DatosPlanoFragment) fm.findFragmentById(R.id.datos_herraje_fragment);

        fragment.setCodB(codB);
    }


    public void onClickNuevoMantenimiento(View v) {
        Intent intent = new Intent(this, NuevoMantenimientoActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_plano2, menu);
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
