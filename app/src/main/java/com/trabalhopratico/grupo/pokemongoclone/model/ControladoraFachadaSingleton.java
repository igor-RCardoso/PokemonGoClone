package com.trabalhopratico.grupo.pokemongoclone.model;

import java.util.List;
import java.util.Map;

/**
 * Created by usuario on 18/04/2017.
 */

class ControladoraFachadaSingleton {
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

    }

    static ControladoraFachadaSingleton getInstance() {
        return ourInstance;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Aparecimento[] getAparecimentos() {
        return aparecimentos;
    }

    public void setAparecimentos(Aparecimento[] aparecimentos) {
        this.aparecimentos = aparecimentos;
    }

    public List<Tipo> getTiposPokemons() {
        return tiposPokemons;
    }

    public void setTiposPokemons(List<Tipo> tiposPokemons) {
        this.tiposPokemons = tiposPokemons;
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




}
