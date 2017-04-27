package com.trabalhopratico.grupo.pokemongoclone.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.trabalhopratico.grupo.pokemongoclone.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("Inicializando", "LOGIN ACTIVITY");
        setContentView(R.layout.activity_login);
    }

    public void cadastrar(View v){
        Intent it = new Intent(this, CadastrarActivity.class);
        startActivity(it);
    }
}
