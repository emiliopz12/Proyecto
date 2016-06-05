package com.example.horse.proyecto;

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




        while(FTP.FOTO == null){
            Toast.makeText(getApplicationContext(), "Cargando...", Toast.LENGTH_SHORT).show();
        }


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
        this.finish();
        super.onBackPressed();
    }
}
