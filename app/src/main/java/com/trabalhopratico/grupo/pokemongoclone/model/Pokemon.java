package com.trabalhopratico.grupo.pokemongoclone.model;

import java.util.List;
import java.util.Objects;

/**
 * Created by usuario on 18/04/2017.
 */
//DESCOMENTAR PARTE DE CONTROLADORAFACHADASINGLETON
public class Pokemon {
    private int numero;
    private String nome;
    private String categoria;
    private int foto;
    private int icone;
    private List<Tipo> tipos;

    public Pokemon() {
    }

    protected Pokemon(int numero, String nome, String categoria, int foto, int icone, ControladoraFachadaSingleton cg) {
        this.numero = numero;
        this.nome = nome;
        this.categoria = categoria;
        this.foto = foto;
        this.icone = icone;

    }

    private void preencherTipos(ControladoraFachadaSingleton cg){

    }

    public boolean equals(Object obj){
        return false;
    }

    public int hashCode(){
        return 0;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public int getIcone() {
        return icone;
    }

    public void setIcone(int icone) {
        this.icone = icone;
    }

    public List<Tipo> getTipos() {
        return tipos;
    }


}
