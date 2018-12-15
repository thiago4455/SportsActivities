package app.saturno.thiago.sportsactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("email", user.getEmail());
            startActivity(intent);
        }
        finish();
    }
}