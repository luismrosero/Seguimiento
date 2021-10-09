package com.example.seguimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

import com.example.seguimiento.usuario.ActUsuario;

public class MainActivity extends AppCompatActivity {
    private static final String ID = "IDM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationChannel channel = new NotificationChannel(ID, "noti", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        startActivity(new Intent(MainActivity.this, ActUsuario.class));
    }
}