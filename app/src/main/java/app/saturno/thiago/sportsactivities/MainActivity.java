package app.saturno.thiago.sportsactivities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CollectionReference usuarios;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usuarios = db.collection("usuarios");



        ((RelativeLayout) findViewById(R.id.lytBasquete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarEsporte("basquete");
            }
        });

        ((RelativeLayout) findViewById(R.id.lytFutebol)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarEsporte("futebol");
            }
        });

        ((RelativeLayout) findViewById(R.id.lytVolei)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarEsporte("volei");
            }
        });

        ((RelativeLayout) findViewById(R.id.lytHandebol)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarEsporte("handebol");
            }
        });

        ((RelativeLayout) findViewById(R.id.lytTenis)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarEsporte("tenis");
            }
        });

        ((RelativeLayout) findViewById(R.id.lytPeteca)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarEsporte("peteca");
            }
        });

        ((RelativeLayout) findViewById(R.id.lytAtletismo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarEsporte("atletismo");
            }
        });

        //Sistema de coordenadas

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);;
        final Looper looper = null;
        final Criteria criteria = new Criteria();

        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Location Changes", location.toString());
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Status Changed", String.valueOf(status));
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Provider Enabled", provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Provider Disabled", provider);
            }
        };

        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                Toast.makeText(MainActivity.this, "Ative seu GPS", Toast.LENGTH_SHORT).show();
            }
            locationManager.requestSingleUpdate(criteria, locationListener, looper);
        }


        Log.e("CORD:", "Latitude: "+latitude);
        Log.e("CORD:", "Logitude: "+longitude);
    }
    private double longitude = 0;
    private double latitude = 0;

    protected void selecionarEsporte(String esporte) {
        Intent i = new Intent(MainActivity.this,ListaActivity.class);
        Map<String, Object> data1 = new HashMap<>();
        data1.put("esporte", esporte);

        double coordX = longitude;
        double coordY = latitude;

        data1.put("coordX", coordX);
        data1.put("coordY", coordY);
        usuarios.document(id).set(data1, SetOptions.merge());

        i.putExtra("id",id);
        i.putExtra("esporte",esporte);
        i.putExtra("coordX",coordX);
        i.putExtra("coordY",coordY);
        startActivity(i); //TODO enviar para tela de erro caso as coordenadas sejam 0,0
    }

    private double distancia(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
