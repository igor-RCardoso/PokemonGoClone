package com.trabalhopratico.grupo.pokemongoclone.controller;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.trabalhopratico.grupo.pokemongoclone.R;
import com.trabalhopratico.grupo.pokemongoclone.util.BancoDadosSingleton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
    }

    public void cadastrar(View v){

        Log.i("CADASTRAR", "Entrou no evento");
        EditText edtNome = (EditText) findViewById(R.id.edtNome); String nome = edtNome.getText().toString();
        EditText edtUsuario = (EditText) findViewById(R.id.edtUsuarioCadastro); String usuario = edtUsuario.getText().toString();
        EditText edtSenha = (EditText) findViewById(R.id.edtSenhaCadastro); String senha = edtSenha.getText().toString();
        EditText edtConfirmaSenha = (EditText) findViewById(R.id.edtConfirmarSenha); String confSenha = edtConfirmaSenha.getText().toString();
        RadioButton rdHomem = (RadioButton) findViewById(R.id.rdHomem);
        RadioButton rdMulher = (RadioButton) findViewById(R.id.rdMulher);
        Log.i("CADASTRAR", "Recuperou xml");

        if(!senha.equals(confSenha)){
            return;
        }
        if(usuario.equals("")){
            return;
        }
        if(senha.equals("")){
            return;
        }
        if(confSenha.equals("")){
            return;
        }


        ContentValues _values = new ContentValues();
        _values.put("nome", nome);
        _values.put("login", usuario);
        _values.put("senha", senha);

        Log.i("CADASTRAR", "Colocou nome, login e senha");
        if(rdHomem.isSelected()) {
            _values.put("sexo", "homem");
            _values.put("foto", "@drawable/male_profile.png");
        }else {
            _values.put("sexo", "mulher");
            _values.put("foto", "@drawable/female_profile.png");
        }
        Log.i("CADASTRAR", "Colocou sexo e foto");
        _values.put("temSessao", "S");
        Date dt = new Date();
        SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
        _values.put("dtCadastro", sdt.format(dt).toString());

        Log.i("CADASTRAR", "Colocou data");

        BancoDadosSingleton.getInstance().inserir("usuario", _values);

        Log.i("CADASTRAR", "Inserido");

    }
}
