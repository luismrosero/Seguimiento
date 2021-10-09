package com.example.seguimiento.servicios;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.seguimiento.R;
import com.example.seguimiento.entidades.Entidad;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

public class ServicioEscucha extends Service {
    private static final String ID = "IDM";
    private static final double DISTANCIA = 0.07;
    private FusedLocationProviderClient fusedLocationClient;
    private DatabaseReference mDatabase;


    public ServicioEscucha() {


    }


    @Override
    public void onCreate() {

        Log.e("Creo", "SERVICIO ESCUCHA");
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                obtenerUbicacion();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mDatabase.child("registro").child("V1").addValueEventListener(listener);

        // Looper.prepare();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), ID);
        builder.setSmallIcon(R.drawable.ic_empieza);
        builder.setContentTitle("Seguimiento");
        //  builder.setContentText("a");
        builder.setDefaults(Notification.DEFAULT_SOUND);

        startForeground(145, builder.build());
        // Looper.loop();
        return START_STICKY;


    }

    private void obtenerUbicacion() {


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getCurrentLocation(100, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull

            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    double latit = task.getResult().getLatitude();
                    double longi = task.getResult().getLongitude();
                    subirDatos(latit, longi);
                }

            }
        });


    }

    private void subirDatos(double latitud, double longitud) {

        Entidad entidad = new Entidad("dos", latitud, longitud);

        mDatabase.child("activos").child("dos").setValue(entidad);
    }


}
