package com.axxesinternational.vias.axxes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClick(View v) {

        Toast.makeText(this, "Iniciando sesion", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MenuPrincipal.class);
        startActivity(intent);
    }
}
