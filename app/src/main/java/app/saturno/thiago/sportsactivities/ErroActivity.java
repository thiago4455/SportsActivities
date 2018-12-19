package app.saturno.thiago.sportsactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ErroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erro);

        Intent i = getIntent();
        if (i.getStringExtra("tiṕo")=="permissao"){
            ((TextView) findViewById(R.id.txtErro)).setText("@string/permission_error");
        }

        ((RelativeLayout) findViewById(R.id.lytGeral)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ErroActivity.this,SplashActivity.class));
                finish();
            }
        });
    }
}
