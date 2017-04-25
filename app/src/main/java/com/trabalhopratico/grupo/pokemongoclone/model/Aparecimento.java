package com.trabalhopratico.grupo.pokemongoclone.model;

/**
 * Created by usuario on 18/04/2017.
 */

public class Aparecimento {
    private double latitude;
    private double longitude;
    private Pokemon pokemon;

    public Aparecimento() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
}
