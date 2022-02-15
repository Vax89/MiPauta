package es.ifp.mipauta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ValidacionActivity extends AppCompatActivity {

    protected TextView text1;
    protected TextView med;
    protected Button validar;
    protected Button posponer;

    protected DataBaseSQL db;

    protected Intent jumpAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacion);
        db = new DataBaseSQL(this);
        
        text1 = (TextView) findViewById(R.id.label1_validacion);
        med = (TextView) findViewById(R.id.label2_validacion);
        validar = (Button) findViewById(R.id.validar_validacion);
        posponer = (Button) findViewById(R.id.posponer_validacion);
    }
}