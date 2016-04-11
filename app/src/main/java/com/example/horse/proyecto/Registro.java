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

public class Registro extends AppCompatActivity {

    EditText email, contraseña, Contraseña2, nombre, apellido1, apellido2, telefono, fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button cancelar = (Button) findViewById(R.id.btnCancelar);

        cancelar.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {
                Intent intento = new Intent(getApplicationContext(), InicioSession.class);
                finish();
                startActivity(intento);
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }

        });

        Button registro = (Button) findViewById(R.id.btnRegistrarse);

        registro.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {

                if(required()) {
                    Intent intento = new Intent(getApplicationContext(), InicioSession.class);
                    finish();
                    startActivity(intento);
                    Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Debe llenar todas las opciones", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }


    public boolean required(){
        boolean exito = true;
        nombre =(EditText) findViewById(R.id.editEmail);
        String e = nombre.getText().toString();
        nombre =(EditText) findViewById(R.id.editContraseña);
        String c = nombre.getText().toString();
        nombre =(EditText) findViewById(R.id.editContraseña2);
        String c2 = nombre.getText().toString();
        nombre =(EditText) findViewById(R.id.editNombre);
        String n = nombre.getText().toString();
        nombre =(EditText) findViewById(R.id.editApellido1);
        String a1 = nombre.getText().toString();
        nombre =(EditText) findViewById(R.id.editApellido2);
        String a2 = nombre.getText().toString();
        nombre =(EditText) findViewById(R.id.editTelefono);
        String t = nombre.getText().toString();
        nombre =(EditText) findViewById(R.id.editNacimiento);
        String na = nombre.getText().toString();

        if(e.isEmpty() || c.isEmpty() || c2.isEmpty() || n.isEmpty() || a1.isEmpty() || a2.isEmpty() || t.isEmpty()
                || na.isEmpty()){
            exito = false;
        }

        return exito;
    }


            // colocar al abrir la clase de la activity
            @Override
            public void onBackPressed() {
                //Toast.makeText(getApplicationContext(), "Te atrape", Toast.LENGTH_LONG).show();
                //super.onBackPressed(); //habilite esto si desea que se devuelva con el boton back
                //Button MiBoton = (Button) findViewById(R.id.btnCancelar);
                //MiBoton.performClick();

            }


            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_registro, menu);
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
