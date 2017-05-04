package com.trabalhopratico.grupo.pokemongoclone.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.trabalhopratico.grupo.pokemongoclone.R;
import com.trabalhopratico.grupo.pokemongoclone.model.ControladoraFachadaSingleton;
import com.trabalhopratico.grupo.pokemongoclone.model.Usuario;
import com.trabalhopratico.grupo.pokemongoclone.util.BancoDadosSingleton;
import com.trabalhopratico.grupo.pokemongoclone.util.MyApp;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("CICLO_DE_VIDA", "Splash Criado");
        setContentView(R.layout.activity_splash);

        if(!VerificaConexao(getBaseContext())){
            AlertDialog alerta;
            Log.i("PRE_VERIFICACOES", "SEM CON");
            //Cria o gerador do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //define o titulo
            builder.setTitle("Sem conexão");
            //define a mensagem
            builder.setMessage("É necessário ter uma conexão com a internet");
            //define um botão como positivo
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();
        }
        Log.i("PRE_VERIFICACOES", "verificou conexao");

        if(!ControladoraFachadaSingleton.getOurInstance().temSessao()){
            Intent it = new Intent(this, LoginActivity.class);
            startActivity(it);
        }else{
            Intent it = new Intent(this, MapActivity.class);
            startActivity(it);
        }

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
