package com.trabalhopratico.grupo.pokemongoclone.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.trabalhopratico.grupo.pokemongoclone.R;
import com.trabalhopratico.grupo.pokemongoclone.util.BancoDadosSingleton;
import com.trabalhopratico.grupo.pokemongoclone.util.MyApp;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("CICLO_DE_VIDA", "Splash Criado");

        setContentView(R.layout.activity_splash);
        boolean con = VerificaConexao(getBaseContext());
        Log.i("PRE_VERIFICACOES", "verificou conexao");

        //select * from usuario where temSessao == "S"
        VerificaSessao(BancoDadosSingleton.getInstance().buscar("usuario", new String[]{"*"}, "temSessao = 'S'", ""), this);

    }

    public static boolean VerificaConexao(Context _context){
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    protected void VerificaSessao(Cursor c, Context _context){
        if(c.getCount() == 0) {
            Intent it = new Intent(_context, LoginActivity.class);
            Log.i("PRE_VERIFICACOES", c.getCount() + "");
            _context.startActivity(it);
        }
    }
}
