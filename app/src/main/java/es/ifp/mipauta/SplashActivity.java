package es.ifp.mipauta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    protected ImageView logo;

    protected Intent jumpAct;

    protected DataBaseSQL db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBaseSQL(this);


        logo = (ImageView) findViewById(R.id.logo_start);

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                jumpAct = new Intent(SplashActivity.this, LogInActivity.class);
                finish();
                startActivity(jumpAct);


            }
        };
        Timer t = new Timer();
        t.schedule(tt, 2000);
    }
}