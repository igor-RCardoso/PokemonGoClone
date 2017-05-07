package com.trabalhopratico.grupo.pokemongoclone.controller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.trabalhopratico.grupo.pokemongoclone.R;
import com.trabalhopratico.grupo.pokemongoclone.model.ControladoraFachadaSingleton;
import com.trabalhopratico.grupo.pokemongoclone.model.Usuario;
import com.trabalhopratico.grupo.pokemongoclone.util.BancoDadosSingleton;

public class PerfilActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Intent it = getIntent();
        BancoDadosSingleton bd = BancoDadosSingleton.getInstance();
        TextView nome = (TextView) findViewById(R.id.nome);
        TextView data_de_inicio = (TextView) findViewById(R.id.inicio_da_aventura);
        ImageView imageView = (ImageView) findViewById(R.id.imagem_de_perfil);

        Log.i("PerfilActivity", "BD instanciado");
        //Log.i("PerfilActivity", it.getStringExtra("login"));
        Log.i("PerfilActivity", "Busca por data de captura realizada");


        Drawable drawable;
        Usuario user = ControladoraFachadaSingleton.getOurInstance().getUser();
        data_de_inicio.setText(user.getDtCadastro() + "");

        if(user.getSexo().equals("mulher")) drawable = getResources().getDrawable(R.drawable.female_grande);
        else drawable = getResources().getDrawable(R.drawable.male_grande);
        imageView.setImageDrawable(drawable);
        nome.setText(user.getNome());

        Log.i("PerfilActivity", "Iniciando Curso 2" );
        Cursor c2 = bd.buscar("pokemonusuario", new String[]{"idPokemon"}, "login='"+user.getLogin()+"'",null);
        TextView numero_de_capturas = (TextView) findViewById(R.id.numero_de_capturas);
        numero_de_capturas.setText(""+c2.getCount());
        Log.i("PerfilActivity", "Finalzando");
    }

    public void logout(View v){
        ControladoraFachadaSingleton.getOurInstance().logoutUser();
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
        finish();
    }
}