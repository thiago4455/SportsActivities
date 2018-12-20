package app.saturno.thiago.sportsactivities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Pessoa> pessoas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        String esporte = intent.getStringExtra("esporte");
        final double longitudeAtual = intent.getDoubleExtra("coordX",0);
        final double latitudeAtual = intent.getDoubleExtra("coordY",0);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.e("USUARIO:", "Id: "+id);
        Log.e("USUARIO:", "Esporte: "+esporte);


        pessoas = new ArrayList<Pessoa>();
        final PessoaAdapter adapter = new PessoaAdapter(getApplicationContext(),R.layout.activity_pessoa_item,pessoas);
        ListView listaPessoas = (ListView)findViewById(R.id.listPessoas);
        listaPessoas.setAdapter(adapter);

        db.collection("usuarios")
                .whereEqualTo("esporte", esporte)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                double longitude = Double.parseDouble(String.valueOf(document.get("coordX")));
                                double latitude = Double.parseDouble(String.valueOf(document.get("coordY")));
                                double distancia = distancia(latitudeAtual,longitudeAtual,latitude,longitude);
                                if(!id.equals(document.getId()) && distancia < 5){
                                    Pessoa pessoa = new Pessoa();
                                    pessoa.setFotoPerfil(document.getString("fotoPerfil"));
                                    pessoa.setIdade(String.valueOf(document.get("idade")));
                                    pessoa.setNome(String.valueOf(document.getString("nome")));
                                    pessoa.setSexo(String.valueOf(document.getString("sexo")));
                                    pessoa.setTelefone(String.valueOf(document.getString("telefone")));
                                    Log.e("PESSOA:", "Comparação: "+!id.equals(document.getId()));
                                    Log.e("PESSOA:", "Pessoa encontrada: "+document.getId());
                                    Log.e("PESSOA:", "Nome: "+document.getString("nome"));
                                    Log.e("PESSOA:", "Distancia: "+distancia);
                                    Log.e("PESSOA:", "Idade: "+document.get("idade"));
                                    Log.e("PESSOA:", "Telefone: "+document.getString("telefone"));
                                    Log.e("PESSOA:", "Sexo: "+document.getString("sexo"));
                                    pessoas.add(pessoa);
                                }
                                adapter.notifyDataSetChanged();
                            }
                            if(pessoas.size() == 0){
                                ((TextView)ListaActivity.this.findViewById(R.id.txtVazio)).setVisibility(View.VISIBLE);
                            }
                        } else {
                            Log.e("ERRO:", "Erro ao acessar os documentos: ", task.getException());
                        }
                    }
                });

        listaPessoas.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Pessoa pessoaClicada = pessoas.get(position);
        String nome = pessoaClicada.getNome();
        String numero = pessoaClicada.getTelefone();
        Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
        contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        contactIntent
                .putExtra(ContactsContract.Intents.Insert.NAME, nome)
                .putExtra(ContactsContract.Intents.Insert.PHONE, numero);
        startActivityForResult(contactIntent, 1);
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