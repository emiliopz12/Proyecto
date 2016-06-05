package com.example.horse.proyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Principal extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    static final String FTP_HOST= "ftp.Smarterasp.net";

    /*********  FTP USERNAME ***********/
    static final String FTP_USER = "reporteando-001";

    /*********  FTP PASSWORD ***********/
    static final String FTP_PASS  ="reporte11";

    private ViewPager mViewPager;
    private EditText inputPelicula;
    public static FloatingActionButton fab;
    public static Usuario usuario;

    public static String mCurrentPhotoPath;
    public static Bitmap fotoElegida;
    public static LocationManager locationManager;

    public static double latitudActual = 0;
    public static double longitudActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this, this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        /*findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Principal.this, Mapa.class));
            }
        });*/

        usuario = (Usuario)getIntent().getExtras().getSerializable("parametro");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        /*fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REST();
                //onLaunchCamera();
            }
        });*/


    }//-------------------------------------------------------------------FIN ONCREATE

    @Override
    public void onBackPressed() {
        //Toast.makeText(getApplicationContext(), "Te atrape", Toast.LENGTH_LONG).show();
        //super.onBackPressed(); //habilite esto si desea que se devuelva con el boton back
        //Button MiBoton = (Button) findViewById(R.id.btnCancelar);
        //MiBoton.performClick();

    }

    //------------------- SERVICIO REST ----------------------------



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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

/*    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(imageCaptureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
           // mImageView.setImageBitmap(imageBitmap);
        }
    }*/


    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    public static boolean tomarFoto = true;
    public static Bitmap FOTO = null;
    public static String b64;

   /* public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                // Load the taken image into a preview
               // ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
               // ivPreview.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    public static FTP ftp;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(imageCaptureIntent, REQUEST_IMAGE_CAPTURE);
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
            ftp = new FTP();
            if(tomarFoto == false && data != null){
                Uri imageUri = data.getData();
                try {
                   FOTO = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                    ((ImageView) findViewById(R.id.imageView)).setImageBitmap(FOTO);

                    b64 = getRealPathFromURI(imageUri);
                }
                catch (Exception e){

                }
            }
            if(tomarFoto == true) {
                //Bundle extras = data.getExtras();



                //Uri imageUri = data.getData();

                //FOTO = (Bitmap) extras.get("data");

                FOTO = BitmapFactory.decodeFile(mCurrentPhotoPath);

                ((ImageView) findViewById(R.id.imageView)).setImageBitmap(FOTO);

                b64 = mCurrentPhotoPath;
            }
        }
    }

    public static void getLocation()
    {
        // Get the location manager

        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        Double lat,lon;
        try {
            latitudActual = location.getLatitude ();
            longitudActual = location.getLongitude();

            // return new LatLng(lat, lon);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            // return null;
        }
    }



    public static String bitmapToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        String base64 = null;
        try {
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            image.compress(compressFormat, quality, byteArrayOS);
            base64 = Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return base64;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    // Returns the Uri for a photo stored on disk given the fileName
   /* public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }*/

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static String ARG_SECTION_NUMBER = "section_number";
        static Activity p;
        static Principal a;
        static boolean primera = true;
        public static List<Reporte> reportes = new ArrayList<Reporte>();
        static final int REQUEST_IMAGE_CAPTURE = 1;


        static Spinner tipo, provincias;
        static EditText descripcion;
        static ImageView fotografia;
        static ListView list;


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Activity pr, Principal pp) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            p = pr;
            a = pp;

           Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
           fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
/*            reportes.add(new Reporte("Seguridad", "Robo", "San Jose", "12/12/12"));
            reportes.add(new Reporte("Luz", "Poste caido", "Alajuela", "12/12/12"));
            reportes.add(new Reporte("Agua", "Tuberia en mal estado", "Heredia", "12/12/12"));*/
        }

        public void cargaReportes(){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String URL = "http://reporteando-001-site1.etempurl.com/WebServiceApiRouter.svc/api/reportes";
            try{
                String result = "";
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet(URL));
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
                result=reader.readLine();


                JSONObject obj = new JSONObject(result);
                JSONArray proveedores = obj.getJSONArray("lista");

                reportes.clear();

                for (int i=0;i<proveedores.length();i++){
                    JSONObject json = proveedores.getJSONObject(i);
                    String fecha = json.getString("fecha");
                    String tipo = json.getString("tipo");
                    String descripcion = json.getString("descripcion");
                    String direccion = json.getString("direccion");
                    String lat = json.getString("latitud");
                    String longi = json.getString("longitud");
                    String foto = json.getString("foto");

                    if(tipo.equals("1")){
                        tipo = "Agua";
                    }
                    else if(tipo.equals("2")){
                        tipo = "Luz";
                    }
                    else{
                        tipo = "Seguridad";
                    }


                    reportes.add(new Reporte(tipo, descripcion, direccion, fecha, lat, longi, foto));
                    if(i == 10)
                        break;

                }

            }catch(JSONException e){
                e.printStackTrace();
            }catch(ClientProtocolException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        };
        //-----------------------------------------------------------------------



        private File createImageFile() throws IOException {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = image.getAbsolutePath();
            return image;
        }

        public void cargaEnLista(){


            try {
                ArrayAdapter<Reporte> adapter = new AdaptadorReporte(p, reportes);
                list.setAdapter(adapter);

            }
            catch (Exception e){
                System.out.print(e.getMessage());
            }
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View viewClicked,
                                        int position, long id) {
                    //Car clickedCar = myCars.get(position);

                    Reporte report = reportes.get(position);

                    ftp = new FTP();


                    ftp.setBitmap(fotoElegida);
                    ftp.setNombreBajar(report.getImagen());

                    ftp.iniciarDescarga();

                    Intent intento = new Intent(getContext(), Imagen.class);
                    startActivity(intento);

                }
            });

        };

        public boolean guardarReporte(){

            boolean exito = true;



            if(FOTO != null){

            Object tipoE = tipo.getSelectedItem();
            Object provinciaE = provincias.getSelectedItem();
            String descrip = descripcion.getText().toString();

            //String base64 = bitmapToBase64(FOTO, Bitmap.CompressFormat.JPEG, 100);
            if(FOTO != null && tipoE != null && provinciaE != null && descrip != ""){
                Date fechaHoy = new Date();

                if(tipoE.equals("Agua")){
                    tipoE = "1";
                }
                else if(tipoE.equals("Luz")){
                    tipoE = "2";
                }
                else{
                    tipoE = "3";
                }

                SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");


            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
                //b64 = ":)";


                String URL;


                ftp.setNombreSubir(b64);

                String[] cadenas = b64.split("/");
                int ultimo = cadenas.length - 1;
                b64 = cadenas[ultimo];



                if(IngresarLocalizacion.latitud != 0) {
                    URL = "http://reporteando-001-site1.etempurl.com/WebServiceApiRouter.svc/api/insertarreporte?fecha=" + dt1.format(fechaHoy) + "&tipo=" + tipoE + "&ubicacion=" + provinciaE + "&direccion=" + provinciaE + "&descripcion=" + descrip.replace(" ", "%20") + "&puntaje=0&foto=" + b64 + "&ciudadano=" + usuario.getCorreo() + "&ciudad=0&latitud=" + IngresarLocalizacion.latitud + "&logitud=" + IngresarLocalizacion.longitud;
                }
                else {

                    getLocation();

                    URL = "http://reporteando-001-site1.etempurl.com/WebServiceApiRouter.svc/api/insertarreporte?fecha=" + dt1.format(fechaHoy) + "&tipo=" + tipoE + "&ubicacion=" + provinciaE + "&direccion=" + provinciaE + "&descripcion=" + descrip.replace(" ", "%20") + "&puntaje=0&foto=" + b64 + "&ciudadano=" + usuario.getCorreo() + "&ciudad=0&latitud=" + latitudActual + "&logitud=" + longitudActual;
                }
                URL = URL.replace("\n", "");
                URL = URL.replace(" ", "%20");


                try {
                    String result = "";
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response = httpclient.execute(new HttpGet(URL));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    result = reader.readLine();


                    ftp.iniciarSubida();

                    //JSONObject obj = new JSONObject(result);
                    // JSONArray proveedores = obj.getJSONArray("sucess");
                    Toast.makeText(a.getApplicationContext(), "Reporte Agregado", Toast.LENGTH_LONG).show();
              /*  for (int i=0;i<proveedores.length();i++){
                    JSONObject json = proveedores.getJSONObject(i);
                    String fecha = json.getString("fecha");
                    String tipo = json.getString("tipo");
                    String descripcion = json.getString("descripcion");
                    String direccion = json.getString("direccion");

                    if(tipo.equals("1")){
                        tipo = "Agua";
                    }
                    else if(tipo.equals("2")){
                        tipo = "Luz";
                    }
                    else{
                        tipo = "Seguridad";
                    }


                }*/


                }catch(ClientProtocolException e){
                    e.printStackTrace();

                }catch (IOException e){
                    e.printStackTrace();

                }
                catch (Exception e){
                    e.printStackTrace();

                }

        }
            }
        return exito;
        };
        //-----------------------------------------------------------------------



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_principal, container, false);

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                rootView = inflater.inflate(R.layout.inicio, container, false);

                //HACER LO QUE TENGA QUE VER CON INICIO

                list = (ListView) rootView.findViewById(R.id.listaReportes);


                Hilo01 MiHilo01 = new Hilo01();
                MiHilo01.execute();

               /* new Thread(new Runnable() {
                    @Override
                    public void run() {

                cargaReportes();
                cargaEnLista();

                    }
                }).start();*/

                rootView.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), Mapa.class));
                    }
                });


                //fab.setVisibility(View.VISIBLE);
            }

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){


                rootView = inflater.inflate(R.layout.reporte, container, false);

                fotografia = (ImageView) rootView.findViewById(R.id.imageView);

                descripcion = (EditText) rootView.findViewById(R.id.descripcion);

                Spinner s1;
                final String[] presidents = {
                        "Seguridad",
                        "Luz",
                        "Agua"};

                s1 = (Spinner) rootView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, presidents);

                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                s1.setAdapter(adapter);

                tipo = s1;

                Spinner s2;
                final String[] Provincias = {
                        "San José",
                        "Alajuela",
                        "Cartago",
                        "Heredia",
                        "Guanacaste",
                        "Puntarenas",
                        "Limon"};

                s2 = (Spinner) rootView.findViewById(R.id.spinner2);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, Provincias);

                s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                s2.setAdapter(adapter2);

                provincias = s2;


                ImageButton im = (ImageButton) rootView.findViewById(R.id.imageButton);


                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "Tomar foto o seleccionar una", Toast.LENGTH_LONG).show();

                        // a.onLaunchCamera();

                        // Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //   startActivityForResult(imageCaptureIntent, REQUEST_IMAGE_CAPTURE);


                        //

                        //Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        // startActivityForResult(i, RESULT_LOAD_IMAGE);

                        //   Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        //   galleryIntent.setType("image/*");
                        //    startActivityForResult(galleryIntent, 2);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage("Elija una opción");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Tomar una fotografía",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        File photoFile = null;
                                        try {
                                            photoFile = createImageFile();
                                        } catch (IOException ex) {
                                        }
                                        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT,  Uri.fromFile(photoFile));
                                        startActivityForResult(imageCaptureIntent, REQUEST_IMAGE_CAPTURE);
                                        tomarFoto = true;

                                    }
                                });
                        builder1.setNegativeButton("Cargar una fotografía",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                        galleryIntent.setType("image/*");
                                        startActivityForResult(galleryIntent, 2);
                                        tomarFoto = false;

                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();


                    }
                });



                //fotografia.setImageBitmap(a);


                ImageButton im2 = (ImageButton) rootView.findViewById(R.id.ubicacion);


                im2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intento = new Intent(getContext(), IngresarLocalizacion.class);
                        startActivity(intento);
                    }
                });

                Button reporte = (Button) rootView.findViewById(R.id.realizarReporte);


                reporte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guardarReporte();
                    }
                });


            }





            if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                rootView = inflater.inflate(R.layout.cuenta, container, false);

                //HACER  LO QUE TENGA QUE VER CON CUENTA

                //a.dispatchTakePictureIntent()
                rootView.findViewById(R.id.fuera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a.finish();
                        startActivity(new Intent(a, InicioSession.class));
                    }
                });

                rootView.findViewById(R.id.ajustes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intento = new Intent(a, ModificarUsuario.class);
                        intento.putExtra("parametro", usuario);
                        //a.finish();
                        startActivity(intento);
                    }
                });

            }


            return rootView;
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

                cargaReportes();


                return true;
            }

            // Se ejecuta cuando se llama a publishProgress. Recibe parametros enviados desde doInBackground
            // y que pueden ser usado para notificar/actualizar el estado del proceso en segundo plano
            // Puede ser un valor o una cadena de valores
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);

            }


            // Una vez que el hilo en segundo plano termina, se ejecuta lo indicado en el siguiente metodo
            @Override
            protected void onPostExecute(Boolean resultado) {
                //super.onPostExecute(aVoid);


                cargaEnLista();
            }
            // Si se corta la ejecución del hilo, se ejecutan las instrucciones indicadas en el siguiente método
            @Override
            protected void onCancelled() {
                super.onCancelled();


            }
        }



    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Activity p;
        Principal pp;

        public SectionsPagerAdapter(FragmentManager fm, Activity p, Principal pp) {
            super(fm);
            this.p = p;
            this.pp = pp;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            return PlaceholderFragment.newInstance(position + 1, p, pp);

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }
}
