package app.saturno.thiago.sportsactivities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ((Button) findViewById(R.id.btnCadastrar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> usuario = new HashMap<>();
                usuario.put("nome",((TextView) findViewById(R.id.txtNome)).getText().toString());
                usuario.put("idade",Integer.parseInt(((TextView) findViewById(R.id.txtIdade)).getText().toString()));
                usuario.put("sexo",((TextView) findViewById(R.id.txtSexo)).getText().toString());
                usuario.put("telefone",((TextView) findViewById(R.id.txtTelefone)).getText().toString());

                TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                final String imei = telephonyManager.getDeviceId();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("usuarios").document(imei)
                        .set(usuario)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(CadastroActivity.this, MainActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startActivity(new Intent(CadastroActivity.this, ErroActivity.class));
                            }
                        });
            }
        });
    }
}
