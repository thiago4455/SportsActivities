package app.saturno.thiago.sportsactivities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
                usuario.put("nome", ((TextView) findViewById(R.id.txtNome)).getText().toString());
                usuario.put("idade", Integer.parseInt(((TextView) findViewById(R.id.txtIdade)).getText().toString()));
                usuario.put("sexo", ((TextView) findViewById(R.id.txtSexo)).getText().toString());
                usuario.put("telefone", ((TextView) findViewById(R.id.txtTelefone)).getText().toString());

                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(CadastroActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(CadastroActivity.this,ErroActivity.class);
                    i.putExtra("tipo","permissao");
                    return;
                }
                final String imei = telephonyManager.getDeviceId();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("usuarios").document(imei)
                        .set(usuario)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent i = new Intent(CadastroActivity.this, MainActivity.class);
                                i.putExtra("id", imei);
                                startActivity(i);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startActivity(new Intent(CadastroActivity.this, ErroActivity.class));
                                finish();
                            }
                        });
            }
        });
    }
}
