package com.trabalhopratico.grupo.pokemongoclone.controller;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.trabalhopratico.grupo.pokemongoclone.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("Inicializando", "LOGIN ACTIVITY");
        setContentView(R.layout.activity_login);

    }
}
