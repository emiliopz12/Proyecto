package com.example.horse.proyecto;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitude = 9.971670;
    double longitude = -84.128554;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.cancelar).setOnClickListener(new View.OnClickListener() {
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        List<Reporte> reportes = Principal.PlaceholderFragment.reportes;

        for(Reporte r: reportes){
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(Double.parseDouble(r.getLatitud()), Double.parseDouble(r.getLongitud()))).title("Descripcion: " + r.getDescripcion() + ", Fecha:" + r.getFecha());

            // Changing marker icon
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            // adding marker
            mMap.addMarker(marker);

        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(9.971670, -84.128554)).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }


}
