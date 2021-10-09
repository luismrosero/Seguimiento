package com.example.seguimiento.servicios;

import android.app.ActivityManager;
import android.content.Context;

import com.example.seguimiento.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ManejoServicio {

    public ManejoServicio() {
    }

    public Boolean isServicioActivo(FloatingActionButton fab){


        if (isMyServiceRunning(ServicioEscucha.class, fab)){
            fab.setImageResource(R.drawable.ic_activo);
            return true;
        }else{
            fab.setImageResource(R.drawable.ic_inactivo);
            return false;
        }

    }


    private boolean isMyServiceRunning(Class<?> serviceClass, FloatingActionButton fab) {
        ActivityManager manager = (ActivityManager) fab.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }



}
