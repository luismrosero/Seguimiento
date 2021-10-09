package com.example.seguimiento.usuario;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seguimiento.R;
import com.example.seguimiento.entidades.Entidad;
import com.example.seguimiento.otros.Registrador;
import com.example.seguimiento.servicios.ManejoServicio;
import com.example.seguimiento.servicios.ServicioEscucha;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ActUsuario extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private FloatingActionButton fabIniciar;
    private DatabaseReference mDatabase;
    private ManejoServicio manejoServicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_usuario);

        setWidgets();
        funBotones();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        manejoServicio = new ManejoServicio();
        manejoServicio.isServicioActivo(fabIniciar);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void funBotones() {
        fabIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manejoServicio.isServicioActivo(fabIniciar)){
                    stopService(new Intent(ActUsuario.this, ServicioEscucha.class));
                    manejoServicio.isServicioActivo(fabIniciar);

                }else{
                    startService(new Intent(ActUsuario.this, ServicioEscucha.class));
                    manejoServicio.isServicioActivo(fabIniciar);
                }

            }
        });
    }




    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);

        // Add a marker in Sydney and move the camera
        LatLng ipi = new LatLng(0.8279494, -77.6439525);
    //    mMap.addMarker(new MarkerOptions().position(ipi).title("Marker in Sydney"));


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ipi)      // Sets the center of the map to Mountain View
                .zoom(14)                   // Sets the zoom
                //  .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        new Registrador().registrarEntrada();
        encontrarActivos();


    }

    private void encontrarActivos() {


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mMap.clear();


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Entidad activo = ds.getValue(Entidad.class);


                    LatLng actual = new LatLng(activo.getLatitud(), activo.getLongitud());

                    mMap.addMarker(new MarkerOptions().position(actual)
                            //  .title(activo.getMarcaModelo())
                            //.snippet(activo.getPlacas())

                            //.icon(BitmapDescriptorFactory.fromBitmap(resized))
                    );

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };


        mDatabase.child("activos").addValueEventListener(postListener);

    }



    private void setWidgets() {

        fabIniciar = findViewById(R.id.fab_usuario);
    }
}