package com.axxesinternational.vias.axxes;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;


public class LevantamientoActivity extends Activity {

    private Button btn_scan;
    private SearchView search_codB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levantamiento);

        //Boton en el XML
        btn_scan=(Button)findViewById(R.id.btn_scan);
        search_codB = (SearchView) findViewById(R.id.search_codB);

        //Añadimos Listener, al clickar...
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lanzamos la activity del escaner
                IntentIntegrator.initiateScan(LevantamientoActivity.this);
            }
        });

        search_codB.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    //Marcamos lo que queremos que haga una vez haya leido el código
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case IntentIntegrator.REQUEST_CODE:
            {
                if (resultCode == RESULT_CANCELED){
                }
                else
                {
                    //Recogemos los datos   que nos envio el código Qr/codigo de barras
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(
                            requestCode, resultCode, data);
                    String UPCScanned = scanResult.getContents();

                    try {
                        String[] arrayLectura = UPCScanned.split(";");
                        String codB = arrayLectura[0].toString();

                        Intent detPlano = new Intent(this, DetallePlanoActivity.class);
                        detPlano.putExtra("codB", codB);
                        startActivity(detPlano);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //Log.e("resultado", R.string.error_lectura_qr);
                    }

                }
                break;
            }
        }
    }

    public void onSearch(String codB) {

        Intent intent = new Intent(this, DetallePlanoActivity.class);
        intent.putExtra("codB", codB);
        startActivity(intent);

    }

    public void onClickPendientes(View v) {

        Intent intent = new Intent(this, DetallePlanoActivity.class);
        startActivity(intent);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_levantamiento, menu);
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
