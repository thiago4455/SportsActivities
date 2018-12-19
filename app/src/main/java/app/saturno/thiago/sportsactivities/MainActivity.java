package app.saturno.thiago.sportsactivities;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
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


    }

    protected void selecionarEsporte(String esporte) {
        Intent i = new Intent(MainActivity.this,ListaActivity.class);
        Map<String, Object> data1 = new HashMap<>();
        data1.put("esporte", esporte);

        double coordX = 0; //TODO: Inserir coordenadas do usuário
        double coordY = 0;

        data1.put("coordX", coordX);
        data1.put("coordY", coordY);
        usuarios.document(id).set(data1, SetOptions.merge());

        i.putExtra("id",id);
        i.putExtra("esporte",esporte);
        i.putExtra("coordX",coordX);
        i.putExtra("coordY",coordY);
        startActivity(i);
    }

}
