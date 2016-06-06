package com.example.horse.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InicioSession extends AppCompatActivity {

    EditText nombre, contraseña;
    String nom, contr;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_session);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setSubtitle("Iniciar Sesión");

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
                nom = nombre.getText().toString();
                contraseña = (EditText) findViewById(R.id.editContraseñaLogueo);
                contr = contraseña.getText().toString();
                if (nom.isEmpty() || contr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Falta el nombre de Usuario o la Contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intento = new Intent(getApplicationContext(), Principal.class);
                    //finish();
                    if (REST(nom, contr)) {
                        usuario = new Usuario(nom, contr);
                        intento.putExtra("parametro", usuario);
                        finish();
                        startActivity(intento);
                        Toast.makeText(getApplicationContext(), "Iniciado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Datos Incorrectos", Toast.LENGTH_SHORT).show();
                    }
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

    }//--------------------------------


    public boolean REST(String us, String con){
        boolean exito = false;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String URL = "http://reporteando-001-site1.etempurl.com/WebServiceApiRouter.svc/api/login?usuario=" + us + "&contrasena=" + con;
        try{
            String result = "";
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(URL));
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            result=reader.readLine();


            JSONObject obj = new JSONObject(result);
            JSONArray proveedores = obj.getJSONArray("lista");

            if(proveedores.length() > 0){
                exito = true;
            }

        }catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }catch(ClientProtocolException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return exito;
    };

}
