package es.ifp.mipauta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MedicamentoActivity extends AppCompatActivity {

    protected TextView text1;
    protected TextView text2;
    protected TextView text3;
    protected TextView text4;
    protected EditText medicamentoNombre;
    protected Button confirmacion;

    protected Intent jumpAct;

    private int usuario_id = 0;

    private Bundle extras;

    protected DataBaseSQL db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
        db = new DataBaseSQL(this);

        extras = getIntent().getExtras();
        usuario_id = extras.getInt("ID");

        text1 = (TextView) findViewById(R.id.label1_med);
        text2 = (TextView) findViewById(R.id.label2_med);
        text3 = (TextView) findViewById(R.id.label3_med);
        text4 = (TextView) findViewById(R.id.label4_med);
        medicamentoNombre = (EditText) findViewById(R.id.med_add);
        confirmacion = (Button) findViewById(R.id.boton1_med);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.back_add:
                jumpAct = new Intent(MedicamentoActivity.this, AddActivity.class);
                jumpAct.putExtra("ID", usuario_id);
                finish();
                startActivity(jumpAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
