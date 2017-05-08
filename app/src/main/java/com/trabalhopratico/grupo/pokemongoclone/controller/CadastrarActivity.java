package com.trabalhopratico.grupo.pokemongoclone.controller;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.trabalhopratico.grupo.pokemongoclone.R;
import com.trabalhopratico.grupo.pokemongoclone.model.ControladoraFachadaSingleton;
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
        EditText edtUsuario = (EditText) findViewById(R.id.edtUsuarioCadastro); String login = edtUsuario.getText().toString();
        EditText edtSenha = (EditText) findViewById(R.id.edtSenhaCadastro); String senha = edtSenha.getText().toString();
        EditText edtConfirmaSenha = (EditText) findViewById(R.id.edtConfirmarSenha); String confSenha = edtConfirmaSenha.getText().toString();
        RadioButton rdHomem = (RadioButton) findViewById(R.id.rdHomem);
        RadioButton rdMulher = (RadioButton) findViewById(R.id.rdMulher);
        String sexo;
        String foto;

        if(!senha.equals(confSenha)){
            Toast.makeText(getBaseContext(), "A confirmação de senha deve ser igual a senha", Toast.LENGTH_SHORT).show();
            return;
        }
        if(login.equals("")){
            Toast.makeText(getBaseContext(), "Insira um nome de usuário", Toast.LENGTH_SHORT).show();
            return;
        }
        if(senha.equals("")){
            Toast.makeText(getBaseContext(), "Insira uma senha", Toast.LENGTH_SHORT).show();
            return;
        }
        if(rdHomem.isSelected()) {
            sexo = "homem";
            foto = "@drawable/male_profile.png";
        }else {
            sexo = "mulher";
            foto = "@drawable/female_profile.png";
        }

        Log.i("CADASTRAR", "Sobrecarregou variaveis");
        if(ControladoraFachadaSingleton.getOurInstance().cadastrarUser(login, senha, nome,sexo, foto)) {
            Intent it = new Intent(this, MapActivity.class);
            startActivity(it);
            finish();
        }
    }
}
