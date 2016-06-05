package com.example.horse.proyecto;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import it.sauronsoftware.ftp4j.FTPClient;

public class InicioSession extends AppCompatActivity {

    EditText nombre, contraseña;
    String nom, contr;
    Usuario usuario;

    String nombreBajar;
    String nombreSubir;
    static final String FTP_HOST= "ftp.Smarterasp.net";

    /*********  FTP USERNAME ***********/
    static final String FTP_USER = "reporteando-001";

    /*********  FTP PASSWORD ***********/
    static final String FTP_PASS  ="reporte11";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_session);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setSubtitle("Iniciar Sessión");

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


        Button e = (Button) findViewById(R.id.button);

        e.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        nombreBajar = "Suma-Emilio.java";

        dowoload MiHilo01 = new dowoload();
        MiHilo01.execute();

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



    public void uploadFile(File fileName){


        FTPClient client = new FTPClient();

        try {

            client.connect(FTP_HOST,21);
            client.login(FTP_USER, FTP_PASS);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/db/");

            client.upload(fileName);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public Bitmap downloadFile(String fileName){


        FTPClient client = new FTPClient();

        File archivo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName);

        try {

            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            client.connect(FTP_HOST, 21);
            client.login(FTP_USER, FTP_PASS);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/db/");
            archivo.createNewFile();
            client.download(fileName, archivo);

            return BitmapFactory.decodeFile(archivo.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    return null;
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

                Uri imageUri = data.getData();
                try {

                    String path = getRealPathFromURI(imageUri);
                    a = new File(path);

                    upload MiHilo01 = new upload();
                    MiHilo01.execute();

                  // uploadFile(a);
                }
                catch (Exception e){
                        System.out.print(e.getMessage());
                }

        }
    }

    File a;


    private class upload extends AsyncTask<String, Void, String> {
        //ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            //INSERT YOUR FUNCTION CALL HERE

            uploadFile(a);

            return "Executed!";

        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(result);
            Log.d("Hi", "Done Downloading.");


        }
    }

    private class dowoload extends AsyncTask<String, Void, String> {
        //ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            //INSERT YOUR FUNCTION CALL HERE

            downloadFile(nombreBajar);

            return "Executed!";

        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(result);
            Log.d("Hi", "Done Downloading.");


        }
    }

}
