package app.saturno.thiago.sportsactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle b = getIntent().getExtras();

        ((TextView) findViewById(R.id.txtEmail)).setText("Logado como: \n"+b.getString("email"));
    }
}
