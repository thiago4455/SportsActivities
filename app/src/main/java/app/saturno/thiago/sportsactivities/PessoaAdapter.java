package app.saturno.thiago.sportsactivities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by Aluno on 18/12/2018.
 */
public class PessoaAdapter extends ArrayAdapter<Pessoa> {
    Context context;
    int layoutResourceId;
    List<Pessoa> dados;

    public PessoaAdapter(Context context, int layoutResourceId, List<Pessoa> dados) {
        super(context, layoutResourceId, dados);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.dados = dados;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(layoutResourceId, parent, false);
        }

        TextView txtNome = (TextView)view.findViewById(R.id.txtNome);
        TextView txtIdade = (TextView)view.findViewById(R.id.txtIdade);
        TextView txtTelefone = (TextView)view.findViewById(R.id.txtTelefone);
        ImageView imageView = (ImageView)view.findViewById(R.id.perfilLista) ;

        Pessoa pessoa = dados.get(position);

        String urlImage = pessoa.getFotoPerfil();

        Glide.with(context)
                .load(urlImage)
                .into(imageView);


        txtNome.setText("Nome " + pessoa.getNome()+"  ");
        txtIdade.setText("Idade " + pessoa.getIdade());
        txtTelefone.setText("  " + pessoa.getTelefone());
        if(pessoa.getSexo().equals("Masculino")){
            ((TextView)view.findViewById(R.id.txtNome)).setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_masculine,0);
        }else if(pessoa.getSexo().equals("Feminino")){
            ((TextView)view.findViewById(R.id.txtNome)).setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_femenine,0);
        }

        return view;
    }
    public void setDados(List<Pessoa> dados){
        this.clear();
        this.addAll(dados);
        this.dados = dados;
        this.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position){
        return dados.get(position).getId();
    }
}
