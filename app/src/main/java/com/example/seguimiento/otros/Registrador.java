package com.example.seguimiento.otros;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Registrador {


    private DatabaseReference mDatabase;

    public Registrador() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void registrarEntrada(){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("valor",new Date().toLocaleString());

        mDatabase.child("registro").child("V1").updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Log.e("Incremento", "SI");
            }
        });
    }

}
