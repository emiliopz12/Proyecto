package com.example.horse.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InicioSession extends AppCompatActivity {

    EditText nombre, contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_session);
        findViewById(R.id.btnRegistrarse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioSession.this, Registro.class));
            }
        });


        Button MiBoton = (Button) findViewById(R.id.btnEntrar);

        MiBoton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {

                nombre = (EditText) findViewById(R.id.editUsuario);
                String n = nombre.getText().toString();
                contraseña = (EditText) findViewById(R.id.editContraseñaLogueo);
                String cont = contraseña.getText().toString();
                if (n.isEmpty() || cont.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Falta el nombre de Usuario o la Contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intento = new Intent(getApplicationContext(), Principal.class);
                    //finish();
                    startActivity(intento);
                    Toast.makeText(getApplicationContext(), "Iniciado", Toast.LENGTH_SHORT).show();
                }

            }

        });

        Button registro = (Button) findViewById(R.id.btnRegistrarse);

        registro.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {
                Intent intento = new Intent(getApplicationContext(), Registro.class);
                finish();
                startActivity(intento);
            }
        });
        findViewById(R.id.btnEntrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioSession.this, Principal.class));
            }
        });

    }//--------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio_session, menu);
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
