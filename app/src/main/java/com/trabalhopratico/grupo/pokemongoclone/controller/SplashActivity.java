package com.trabalhopratico.grupo.pokemongoclone.controller;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.trabalhopratico.grupo.pokemongoclone.R;
import com.trabalhopratico.grupo.pokemongoclone.util.BancoDadosSingleton;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Inicializando", "Criando Splash");
        setContentView(R.layout.activity_splash);
        boolean con = VerificaConexao(getBaseContext());
        Log.i("ESTADO", "verificou conexao");

        //select * from usuario where temSessao == "S"
        Cursor c = BancoDadosSingleton.getInstance().buscar("usuario", new String[]{"login", "senha"}, "temSessao = 'S'", "");
        Log.i("ESTADO", "verificou login");
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
}
