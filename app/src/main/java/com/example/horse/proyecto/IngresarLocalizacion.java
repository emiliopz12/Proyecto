package com.example.horse.proyecto;


import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class IngresarLocalizacion extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static double latitud = 0;
    public static double longitud = 0;

    public static double latitudActual = 0;
    public static double longitudActual = 0;
    Location locationCt;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        getLocation();

        findViewById(R.id.cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitud = longitud = 0;
                volver();
            }
        });

        findViewById(R.id.listo).setVisibility(View.INVISIBLE);
        findViewById(R.id.listo).setOnClickListener(new View.OnClickListener() {
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
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public static MarkerOptions marker;

    public void getLocation()
    {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        Double lat,lon;
        try {
            latitudActual = location.getLatitude();
            longitudActual = location.getLongitude();

           // return new LatLng(lat, lon);
        }
        catch (NullPointerException e){
            e.printStackTrace();
           // return null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMyLocationEnabled(true);

        LatLng latLng = new LatLng(10.135489,
                -84.248523);


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(7).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {

                mMap.clear();
                findViewById(R.id.listo).setVisibility(View.VISIBLE);

                latitud = point.latitude;

                longitud = point.longitude;

                marker = new MarkerOptions()
                        .position(point)
                        .title("Aquí")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                mMap.addMarker(marker);


            }
        });

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

/*        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Descripcion: robo, Fecha:12/12/12");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        // adding marker
        mMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(9.971670, -84.128554)).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));*/
    }

    public class GPS_Location implements LocationListener {


        public GPS_Location(){
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }


        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub

            latitud = (location.getLatitude());
            longitud =  (location.getLongitude());

            Toast.makeText(getApplicationContext(), "Latitude: " + latitud + ", Longitude: " + longitud, Toast.LENGTH_LONG).show();

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

    }

}




