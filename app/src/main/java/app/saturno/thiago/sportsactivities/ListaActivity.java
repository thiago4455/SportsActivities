package app.saturno.thiago.sportsactivities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Pessoa> pessoas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        pessoas = new ArrayList<Pessoa>();
        PessoaAdapter adapter = new PessoaAdapter(getApplicationContext(),R.layout.activity_pessoa_item,pessoas);
        ListView listaPessoas = (ListView)findViewById(R.id.listPessoas);
        listaPessoas.setAdapter(adapter);
        for(int i = 0; i <=4 ; i++){
            Pessoa pessoa = new Pessoa();
            pessoa.setIdade(String.valueOf(i));
            pessoa.setNome(String.valueOf(i));
            pessoa.setTelefone(String.valueOf(i));
            pessoas.add(pessoa);
        }
        adapter.notifyDataSetChanged();
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
}