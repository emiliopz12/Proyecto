package com.example.horse.proyecto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class Imagen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);

        ImageView imagen = (ImageView) findViewById(R.id.imagenFoto);

        imagen.setImageBitmap(Principal.fotoElegida);


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
