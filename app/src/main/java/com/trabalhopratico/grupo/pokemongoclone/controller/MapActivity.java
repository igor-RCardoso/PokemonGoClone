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
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.internal.zzf;
import com.trabalhopratico.grupo.pokemongoclone.R;
import com.trabalhopratico.grupo.pokemongoclone.model.Aparecimento;
import com.trabalhopratico.grupo.pokemongoclone.model.ControladoraFachadaSingleton;
import com.trabalhopratico.grupo.pokemongoclone.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapActivity extends Activity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    Handler handler = new Handler();
    public GoogleMap mapa;
    private Location playerPosition;
    private LatLng aux;
    public LocationManager lm;
    public Criteria criteria;
    public String provider;
    public int TEMPO_REQUISICAO_LATLONG = 5000;
    public int DISTANCIA_MIN_METROS = 0;
    private boolean taRodando = true;
    Object posAtt;
    List<Marker> lMO = new ArrayList<Marker>();
    Runnable sorteador = new Runnable() {
        @Override
        public void run() {
            double latMin = aux.latitude - 0.0003;
            double latMax = aux.latitude + 0.0003;
            double longMin = aux.longitude - 0.0003;
            double longMax = aux.longitude + 0.0003;
            ControladoraFachadaSingleton cg = ControladoraFachadaSingleton.getOurInstance();
            cg.sorteaAparecimentos(latMin, latMax, longMin, longMax);
            if (lMO.size() > 0) {
                for (Marker mark : lMO) {
                    mark.remove();
                }
            }
            for (Aparecimento a : cg.getAparecimentos()) {
                LatLng l = new LatLng(a.getLatitude(), a.getLongitude());
                lMO.add(mapa.addMarker(new MarkerOptions().position(l).title(a.getPokemon().getNome()).icon(BitmapDescriptorFactory.fromResource(a.getPokemon().getIcone())))); // a.getPokemon().getIcone()
            }
            handler.postDelayed(sorteador, 180000);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //geral
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        taRodando = true;
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
        super.onStart();

        provider = lm.getBestProvider(criteria, true);
        if (provider == null) {
            Log.e("PROVEDOR", "Nenhum provedor encontrado");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            lm.requestLocationUpdates(provider, TEMPO_REQUISICAO_LATLONG, DISTANCIA_MIN_METROS, this);
        } else {
            Log.i("PROVEDOR", "Provedor utilizado: " + provider);

        }
    }

    @Override
    protected void onDestroy() {
        lm.removeUpdates(this);
        Log.w("PROVEDOR", "Provedor " + provider + " parado");
        taRodando = false;
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (playerPosition != null) {
            aux = new LatLng(playerPosition.getLatitude(), playerPosition.getLongitude());
        } else {
            aux = new LatLng(-20.752946, -42.879097);
        }

        Usuario user = ControladoraFachadaSingleton.getOurInstance().getUser();
        BitmapDescriptor bmp;
        if (user.getSexo().equals("homem")) {
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.male);
        } else {
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.female);
        }

        mapa.setIndoorEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mapa.setMyLocationEnabled(true);

        mapa.setOnMarkerClickListener(this);
        handler.post(sorteador);
        mapa.addMarker(new MarkerOptions().position(aux).title("Voce").icon(bmp));
        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(aux,18));

        /*
        new Thread() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        while(taRodando)  {


                            Log.i("THREAD",aux.latitude +" "+ aux.longitude);

                            ControladoraFachadaSingleton cg = ControladoraFachadaSingleton.getOurInstance();
                            cg.sorteaAparecimentos(latMin, latMax, longMin, longMax);
                            BitmapDescriptor bmp;
                            for(int i = 0; i < 10; i++) {
                                bmp = BitmapDescriptorFactory.fromResource(cg.getAparecimentos()[i].getPokemon().getIcone());
                                LatLng aux1 = new LatLng(cg.getAparecimentos()[i].getLatitude(),cg.getAparecimentos()[i].getLongitude());
                                Log.i("THREAD", cg.getAparecimentos()[i].getLatitude() + " " + cg.getAparecimentos()[i].getLongitude());
                                Log.i("THREAD", cg.getAparecimentos()[i].getPokemon().getNome() + " " + cg.getAparecimentos()[i].getPokemon().getTipos().get(0).getNome() + "");
                                mapa.addMarker(new MarkerOptions().position(aux1).title(cg.getAparecimentos()[i].getPokemon().getNome()));
                            }
                            colocarMarcadores();
                            try {
                                sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

        }.start();*/
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            playerPosition = location;
        }
        ControladoraFachadaSingleton cg = ControladoraFachadaSingleton.getOurInstance();
        BitmapDescriptor bmp;
        if (cg.getUser().getSexo().equals("homem")){
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.male);
        } else {
            bmp = BitmapDescriptorFactory.fromResource(R.drawable.female);
        }
        mapa.addMarker(new MarkerOptions().position(aux).title("Voce").icon(bmp));
        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(aux,18));

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

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!marker.getTitle().equals("Voce")) {
            final Location pontoAtual = new Location(provider);
            pontoAtual.setLatitude(aux.latitude); pontoAtual.setLongitude(aux.longitude);
            final Location pontoPokemon = new Location(provider);
            pontoPokemon.setLatitude(marker.getPosition().latitude); pontoPokemon.setLongitude(marker.getPosition().longitude);

            Log.i("THREAD", aux.latitude+" "+aux.longitude + " " + marker.getPosition().latitude + " " + marker.getPosition().longitude + " " + pontoAtual.distanceTo(pontoPokemon));
            if (pontoAtual.distanceTo(pontoPokemon)/1000000 > 0.00004) {
                Toast.makeText(this,"Aproxime-se " + (pontoAtual.distanceTo(pontoAtual)/1000000 - 40) + " metros para batalhar",Toast.LENGTH_SHORT);
                Log.i("THREAD", " NAO PASSOU");
            } else {
                Log.i("THREAD", "PASSOU");
                Intent it = new Intent(this,CapturarActivity.class);
                startActivity(it);
            }
            return true;
        } else {
            return false;
        }
    }
}
