package com.example.horse.proyecto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;

/**
 * Created by horse on 05/06/16.
 */
public class FTP {

    String nombreBajar;
    String nombreSubir;

    String cargarEmengencia;

    public static Bitmap FOTO;
    static final String FTP_HOST= "ftp.Smarterasp.net";

    static final String FTP_USER = "reporteando-001";

    static final String FTP_PASS  ="reporte11";


    public void setNombreBajar(String n){
        nombreBajar = n;
    }

    public void setNombreSubir(String n){
        nombreSubir = n;
    }

    public void setBitmap(Bitmap n){
        n = FOTO;
    }

    public void iniciarSubida(){
        upload MiHilo01 = new upload();
        MiHilo01.execute();
    }

    public void iniciarDescarga(){
        download MiHilo01 = new download();
        MiHilo01.execute();
    }


    public void uploadFile(String fileName){


        FTPClient client = new FTPClient();
        File archivo = new File(fileName);

        try {
            client.setAutoNoopTimeout(5000);
            client.connect(FTP_HOST,21);
            client.login(FTP_USER, FTP_PASS);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/db/");

            client.upload(archivo);

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
 /*           StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);*/
            client.setAutoNoopTimeout(5000);
            client.connect(FTP_HOST, 21);
            client.login(FTP_USER, FTP_PASS);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/db/");
            archivo.createNewFile();
            client.download(fileName, archivo);

            FOTO = BitmapFactory.decodeFile(archivo.getAbsolutePath());

            return FOTO;

        } catch (Exception e) {
            e.printStackTrace();

            archivo = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + fileName);

            if (archivo != null)
                FOTO = BitmapFactory.decodeFile(archivo.getAbsolutePath());

            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
         return  null;
    }


    public class upload extends AsyncTask<String, Void, String> {
        //ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            //INSERT YOUR FUNCTION CALL HERE

            uploadFile(nombreSubir);

            return "Executed!";

        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(result);
            Log.d("Hi", "Done Downloading.");


        }
    }

    public class download extends AsyncTask<String, Void, String> {
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
