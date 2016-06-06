package com.example.horse.proyecto;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class Imagen extends AppCompatActivity {

    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);

        Hilo01 MiHilo01 = new Hilo01();
        MiHilo01.execute();

        Toast.makeText(getApplicationContext(), "Cargando imagen...", Toast.LENGTH_SHORT).show();

        ImageView imagen = (ImageView) findViewById(R.id.imagenFoto);


        imagen.setImageResource(R.drawable.sincargar);

        findViewById(R.id.volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });




    }

    public void cargar(){
        ImageView imagen = (ImageView) findViewById(R.id.imagenFoto);

        imagen.setImageBitmap(FTP.FOTO);

        findViewById(R.id.volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });
    }

    public void volver(){
        FTP.FOTO = null;
        this.finish();
        super.onBackPressed();
    }


    private class Hilo01 extends AsyncTask<Void,Integer,Boolean> {
        // Parametro 1 (Void) es para doInBackground
// Parametro 2 (Integer) es para onProgressUpdate
// Parametro 3 (Boolean) tipo de respuesta del doInBackground!!
//Usted los puede cambiar como guste!!


        // Coloque aquí las tareas que deben hacerse (en el hilo principal) antes de enviar el hilo a trabajar en segundo plano
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Iniciacion


        }

        // Tareas que se ejecutaran en segundo plano
        @Override
        protected Boolean doInBackground(Void... params) {
            // Tareas ejecutadas en segundo plano

            // Puede llamar al metodo publishProgress que permite comunicarse con el hilo principal
            // Ejemplo: publishProgress(10); // que enviaria 10 a onProgressUpdate


            while(FTP.FOTO == null){
                publishProgress(1);
            }


            return true;
        }

        // Se ejecuta cuando se llama a publishProgress. Recibe parametros enviados desde doInBackground
        // y que pueden ser usado para notificar/actualizar el estado del proceso en segundo plano
        // Puede ser un valor o una cadena de valores
        @Override
        protected void onProgressUpdate(Integer... values) {
            //super.onProgressUpdate(values);


        }


        // Una vez que el hilo en segundo plano termina, se ejecuta lo indicado en el siguiente metodo
        @Override
        protected void onPostExecute(Boolean resultado) {
            //super.onPostExecute(aVoid);

            ImageView imagen = (ImageView) findViewById(R.id.imagenFoto);

            imagen.setImageBitmap(FTP.FOTO);




        }
        // Si se corta la ejecución del hilo, se ejecutan las instrucciones indicadas en el siguiente método
        @Override
        protected void onCancelled() {
            super.onCancelled();


        }
    }


}
