package com.axxesinternational.vias.axxes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.axxesinternational.vias.axxes.fragments.ComponentesFragment;
import com.axxesinternational.vias.axxes.fragments.FotosFragment;
import com.axxesinternational.vias.axxes.fragments.RequerimientosFragment;
import com.axxesinternational.vias.axxes.fragments.TrabajadoresFragment;


public class NuevoMantenimientoActivity extends Activity implements ActionBar.TabListener {

    private Fragment[] fragments = new Fragment[] {
        new ComponentesFragment(),
        new TrabajadoresFragment(),
        new FotosFragment(),
        new RequerimientosFragment()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_mantenimiento);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        for (Fragment fragment : fragments) {
            fragmentTransaction.add(R.id.area_nuevo_mantenimiento, fragment).hide(fragment);
        }

        fragmentTransaction.show(fragments[0]).commit();

        setTabs();

    }


    private void setTabs(){
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText("Componentes").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Trabajadores").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Fotos").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Requerimientos").setTabListener(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nuevo_mantenimiento, menu);
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }

        fragmentTransaction.show(fragments[tab.getPosition()]);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
