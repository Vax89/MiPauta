package es.ifp.mipauta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AddActivity extends AppCompatActivity {

    protected TextView text1;
    protected TextView text2;
    protected TextView text3;
    protected TextView text4;
    protected TextView text5;
    protected EditText medicamentoNombre;
    protected EditText hora;
    protected EditText min;
    protected TextView conector;
    protected Button duranteComida;
    protected Button trasComida;
    protected Button ayunas;
    protected Button confirmacion;
    protected EditText cantidad_med;
    protected EditText repeticion_med;
    protected TextView pastilla;
    protected TextView dia;

    private Bundle extras;

    private ArrayList<String> cuantia = new ArrayList<String>();
    private ArrayAdapter<String> adaptador;

    private ArrayList<String> frecuencia = new ArrayList<String>();
    private ArrayAdapter<String> adaptadorSelect;

    private int usuario_id = 0;
    private int comida = 0;
    private String nombreMed = "";
    private int cantidades = 0;
    private int repeticiones = 0;
    private String time = "";
    private int horas = 0;
    private int minutos = 0;
    private int medicamento_id = 0;


    protected DataBaseSQL db;

    protected Intent jumpAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db = new DataBaseSQL(this);

        text1 = (TextView) findViewById(R.id.label2_add);
        text2 = (TextView) findViewById(R.id.label3_add);
        text3 = (TextView) findViewById(R.id.label4_add);
        text4 = (TextView) findViewById(R.id.label1_add);
        text5 = (TextView) findViewById(R.id.label5_add);
        medicamentoNombre = (EditText) findViewById(R.id.med_add);
        hora = (EditText) findViewById(R.id.hora_add);
        min = (EditText) findViewById(R.id.min_add);
        conector = (TextView) findViewById(R.id.time_add);
        duranteComida = (Button) findViewById(R.id.boton1_add);
        trasComida = (Button) findViewById(R.id.boton3_add);
        ayunas = (Button) findViewById(R.id.boton1_add);
        confirmacion = (Button) findViewById(R.id.boton4_add);
        pastilla = (TextView) findViewById(R.id.pastilla_add);
        dia = (TextView) findViewById(R.id.dias_add);
        cantidad_med = (EditText) findViewById(R.id.cantidad_add);
        repeticion_med = (EditText) findViewById(R.id.repeticion_add);

        extras = getIntent().getExtras();
        usuario_id = extras.getInt("ID");

        ayunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comida = 1;

            }
        });
        duranteComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comida = 2;
            }
        });
        trasComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comida = 3;
            }
        });

        confirmacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreMed = medicamentoNombre.getText().toString();

                if (nombreMed.equals("") || hora.getText().toString().equals("") || min.getText().toString().equals("") || cantidad_med.getText().toString().equals("") || repeticion_med.getText().toString().equals("")) {
                    Toast.makeText(AddActivity.this, "Todos los campos deben estar completos", Toast.LENGTH_SHORT).show();
                } else {
                    horas = Integer.parseInt(hora.getText().toString());
                    minutos = Integer.parseInt(min.getText().toString());
                    cantidades = Integer.parseInt(cantidad_med.getText().toString());
                    repeticiones = Integer.parseInt(repeticion_med.getText().toString());
                    if (horas > 23 || minutos >= 60) {
                        Toast.makeText(AddActivity.this, "Los valores de hora no pueden ser ruperiores a 23. Los valores de minutos no pueden ser superiores a 59.", Toast.LENGTH_SHORT).show();

                    } else {
                        time = hora.getText().toString() + conector.getText().toString() + min.getText().toString();
                        db.insertMeds(nombreMed, usuario_id);
                        db.insertToma(nombreMed, cantidades, repeticiones, comida, time, usuario_id, medicamento_id);
                        Toma tx = db.getToma(nombreMed);
                        int c = tx.getRepeticion();
                        Toast.makeText(AddActivity.this, "Toma de medicamento creada", Toast.LENGTH_SHORT).show();
                        TimerTask tt = new TimerTask() {
                            @Override
                            public void run() {
                                jumpAct = new Intent(AddActivity.this, MenuActivity.class);
                                jumpAct.putExtra("ID", usuario_id);
                                finish();
                                startActivity(jumpAct);

                            }
                        };
                        Timer t = new Timer();
                        t.schedule(tt, 1000);
                    }

                }

            }

        });

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
                jumpAct = new Intent(AddActivity.this, MenuActivity.class);
                jumpAct.putExtra("ID", usuario_id);
                finish();
                startActivity(jumpAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}