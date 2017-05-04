package com.trabalhopratico.grupo.pokemongoclone.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.trabalhopratico.grupo.pokemongoclone.util.BancoDadosSingleton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Created by usuario on 18/04/2017.
 */

public final class ControladoraFachadaSingleton implements Serializable{
    private Usuario user;
   // private Map<cate> pokemons;
    private Aparecimento aparecimentos[] = new Aparecimento[10];
    private List<Tipo> tiposPokemons;
    private static final ControladoraFachadaSingleton ourInstance = new ControladoraFachadaSingleton();
    private boolean sorteouLendario = false;

    private ControladoraFachadaSingleton() {
    }

    private void daoTipos(){

    }

    private void daoPokemons(ControladoraFachadaSingleton controladorGeral){

    }

    private void daoUsuario(){
        String colunas[] = new String []{"login", "senha", "nome", "sexo", "foto", "dtCadastro"};
        Cursor c = BancoDadosSingleton.getInstance().buscar("usuario", colunas, "temSessao == 'S'", "");
        while(c.moveToNext()) {
            int login = c.getColumnIndex("login");
            user = new Usuario(c.getString(login));
            user.setNome(c.getString(c.getColumnIndex("nome")));
            user.setSenha(c.getString(c.getColumnIndex("senha")));
            user.setFoto(c.getString(c.getColumnIndex("foto")));
            user.setDtCadastro(c.getString(c.getColumnIndex("dtCadastro")));
            user.setSexo(c.getString(c.getColumnIndex("sexo")));
        }
    }

    static ControladoraFachadaSingleton getInstance() {
        return ourInstance;
    }

    public Usuario getUser() {
        return user;
    }


    public Aparecimento[] getAparecimentos() {
        return aparecimentos;
    }

    public List<Tipo> getTiposPokemons() {
        return tiposPokemons;
    }


    public static ControladoraFachadaSingleton getOurInstance() {
        return ourInstance;
    }

    public boolean isSorteouLendario() {
        return sorteouLendario;
    }

    public void setSorteouLendario(boolean sorteouLendario) {
        this.sorteouLendario = sorteouLendario;
    }

    public boolean temSessao(){
        //select * from usuario where temSessao == "S"
        Cursor c = BancoDadosSingleton.getInstance().buscar("usuario", new String[]{"nome"}, "temSessao = 'S'", "");
        if(c.getCount() == 1) {
            daoUsuario();
            return true;
        }else
            return false;
    }

    public boolean loginUser(String login, String senha) {
        Log.i("LOGIN", "LOGINUSER");
        String colunas[] = new String[]{"*"};
        String where = "login = '" + login + "' AND senha = '" + senha +"'";
        Cursor c = BancoDadosSingleton.getInstance().buscar("usuario", colunas, where, "");
        Log.i("LOGIN", "Verificou se esta correto");
        if (c.getCount() == 1) {
            ContentValues values = new ContentValues();
            values.put("temSessao", "S");
            BancoDadosSingleton.getInstance().atualizar("usuario", values, where);
            Log.i("LOGIN", "Atualizou");
            daoUsuario();
            return true;
        }else
            return false;
    }


    public boolean cadastrarUser(String login, String senha, String nome, String sexo, String foto){
        ContentValues _values = new ContentValues();
        _values.put("nome", nome);
        _values.put("login", login);
        _values.put("senha", senha);
        _values.put("temSessao", "S");
        _values.put("sexo", sexo);
        _values.put("foto", foto);

        Date dt = new Date();
        SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
        _values.put("dtCadastro", sdt.format(dt).toString());

        if(getUser() != null) {
            String where = "login = " + getUser().getLogin();
            BancoDadosSingleton.getInstance().deletar("pokemonusuario", where);
            BancoDadosSingleton.getInstance().deletar("usuario", where);
        }

        BancoDadosSingleton.getInstance().inserir("usuario", _values);
        daoUsuario();
        return true;
    }
}
