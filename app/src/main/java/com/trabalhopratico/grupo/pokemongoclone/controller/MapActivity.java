package com.trabalhopratico.grupo.pokemongoclone.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trabalhopratico.grupo.pokemongoclone.R;
import com.trabalhopratico.grupo.pokemongoclone.model.ControladoraFachadaSingleton;
import com.trabalhopratico.grupo.pokemongoclone.model.Usuario;

public class MapActivity extends Activity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mapa;
    private Location playerPosition;
    public LocationManager lm;
    public Criteria criteria;
    public String provider;
    public int TEMPO_REQUISICAO_LATLONG = 5000;
    public int DISTANCIA_MIN_METROS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //geral
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Log.i("VERIFICANDO", "TESTE");
        Log.i("VERIFICANDO", ControladoraFachadaSingleton.getOurInstance().getUser().getNome());

        //escreve o nome do usuario na tela
        Usuario user = ControladoraFachadaSingleton.getOurInstance().getUser();
        TextView nomePerfil = ((TextView) findViewById(R.id.textViewProfileName));
        nomePerfil.setText(user.getNome());

        //define as imagens dos botoes e qual o icone do perfil
        ImageButton fotoPerfil = ((ImageButton) findViewById((R.id.imageButtonProfilePic)));
        ImageButton fotoMapa = ((ImageButton) findViewById((R.id.imageButtonMap)));
        fotoMapa.setImageResource(R.drawable.mapa_captura);
        ImageButton fotoDex = ((ImageButton) findViewById((R.id.imageButtonPokedex)));
        fotoDex.setImageResource(R.drawable.pokedex);
        if (user.getSexo().equals("homem")) {
            fotoPerfil.setImageResource(R.drawable.male_profile);
        } else {
            fotoPerfil.setImageResource(R.drawable.female_profile);
        }
        Log.i("CICLO_DE_VIDA", "onCreate");

        //pega o fragment do mapa
        MapFragment mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragment));
        mapFragment.getMapAsync(this);

        //faz a geolocalizacao
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        PackageManager pm = getPackageManager();
        boolean hasGPS = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        if (hasGPS) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            Log.i("LOCATION", "Usando GPS");
        } else {
            Log.i("LOCATION", "Usando servicos de internet");
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        }
    }

    public void perfil(View v) {
        Intent it = new Intent(this, PerfilActivity.class);
        startActivity(it);
    }

    public void mapaCapturas(View v) {
        Intent it = new Intent(this, MapCapturasActivity.class);
        startActivity(it);
    }

    public void pokedex(View v) {
        Intent it = new Intent(this, PokedexActivity.class);
        startActivity(it);
    }

    @Override
    protected void onStart() {

        Log.i("CICLO_DE_VIDA", "onStart");
        super.onStart();
        criteria = new Criteria();
        provider = lm.getBestProvider(criteria, true);
        if (provider == null) {
            Log.e("PROVEDOR", "Nenhum provedor encontrado");
        } else {
            Log.i("PROVEDOR", "Provedor utilizado: " + provider);
            //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //  return;
            //}
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return;
            }
            lm.requestLocationUpdates(provider, TEMPO_REQUISICAO_LATLONG, DISTANCIA_MIN_METROS, this);
        }
    }

    @Override
    protected void onDestroy() {
        lm.removeUpdates(this);
        Log.w("PROVEDOR","Provedor " + provider + " parado");
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.i("CICLO_DE_VIDA", "onMap");
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LatLng aux;
        if(playerPosition != null && provider != null) {
            Log.i("PROVEDOR", "minha casa");
            Log.i("PROVEDOR", playerPosition.getLatitude() + " " + playerPosition.getLongitude());
            aux = new LatLng(playerPosition.getLatitude(),playerPosition.getLongitude());
        } else {
            aux = new LatLng(-20.752946,-42.879097);
        }
        Usuario user = ControladoraFachadaSingleton.getOurInstance().getUser();
        BitmapDescriptor bmp;
        if (user.getSexo().equals("homem")){
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.male);
        } else {
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.female);
        }
        mapa.addMarker(new MarkerOptions().position(aux).title("Voce").icon(bmp));
        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(aux,18));
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("CICLO_DE_VIDA", "onLocation");
        if(location != null) {
            //playerPosition = location;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("LOCATION","Provedor mudou de estado");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("LOCATION","Provedor habilitado");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("LOCATION","Provedor desabilitado");
    }
}
