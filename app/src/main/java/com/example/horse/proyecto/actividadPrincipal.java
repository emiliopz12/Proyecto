package com.example.horse.proyecto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class actividadPrincipal extends AppCompatActivity {

    TabHost TbH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);




        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();

        TabHost.TabSpec inicio = TbH.newTabSpec("Inicio");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec cuenta = TbH.newTabSpec("Mi cuenta");
        TabHost.TabSpec reporte = TbH.newTabSpec("Realizar Reporte");
        TabHost.TabSpec mapa = TbH.newTabSpec("Mapa");

        inicio.setIndicator("Inicio");    //qué queremos que aparezca en las pestañas
        inicio.setContent(R.id.tab1); //definimos el id de cada Tab (pestaña)

        cuenta.setIndicator("Mi cuenta");
        cuenta.setContent(R.id.tab2);

        reporte.setIndicator("Realizar Reporte");
        reporte.setContent(R.id.tab3);

        mapa.setIndicator("Mapa");
        mapa.setContent(R.id.tab4);

        TbH.addTab(inicio); //añadimos los tabs ya programados
        TbH.addTab(cuenta);
        TbH.addTab(reporte);
        TbH.addTab(mapa);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
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
