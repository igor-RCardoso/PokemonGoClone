package com.trabalhopratico.grupo.pokemongoclone.controller;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.trabalhopratico.grupo.pokemongoclone.R;
import com.trabalhopratico.grupo.pokemongoclone.model.ControladoraFachadaSingleton;

public class MapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Log.i("VERIFICANDO", "TESTE");
        Log.i("VERIFICANDO", ControladoraFachadaSingleton.getOurInstance().getUser().getNome());
    }
}
