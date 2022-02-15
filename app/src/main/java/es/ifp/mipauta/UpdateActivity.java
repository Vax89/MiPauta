package es.ifp.mipauta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateActivity extends AppCompatActivity {

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
    protected Button actualizar;
    protected Button borrar;
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
    private int id = 0;
    private String horario = "";
    private String[] tiempo;
    private int toma_id = 0;
    private int med_id = 0;
    private int horas = 0;
    private int minutos = 0;
    private String time = "";
    private String[] partes;
    private int repeticiones=0;
    private int cantidades=0;

    protected DataBaseSQL db;

    protected Intent jumpAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        db = new DataBaseSQL(this);

        text1 = (TextView) findViewById(R.id.label2_update);
        text2 = (TextView) findViewById(R.id.label3_update);
        text3 = (TextView) findViewById(R.id.label4_update);
        text4 = (TextView) findViewById(R.id.label1_update);
        text5 = (TextView) findViewById(R.id.label5_update);
        medicamentoNombre = (EditText) findViewById(R.id.med_update);
        hora = (EditText) findViewById(R.id.hora_update);
        min = (EditText) findViewById(R.id.min_update);
        conector = (TextView) findViewById(R.id.timer_update);
        duranteComida = (Button) findViewById(R.id.boton1_update);
        trasComida = (Button) findViewById(R.id.boton3_update);
        ayunas = (Button) findViewById(R.id.boton1_update);
        actualizar = (Button) findViewById(R.id.boton4_update);
        borrar = (Button) findViewById(R.id.boton5_update);
        pastilla = (TextView) findViewById(R.id.pastilla_update);
        dia = (TextView) findViewById(R.id.dias_update);
        cantidad_med = (EditText) findViewById(R.id.cantidad_update);
        repeticion_med = (EditText) findViewById(R.id.repeticion_update);

        extras = getIntent().getExtras();
        usuario_id = extras.getInt("ID");
        nombreMed = extras.getString("MED");

        partes = nombreMed.split("\n");
        Toma m = db.getToma(partes[1]);
        nombreMed=partes[1];

        medicamentoNombre.setText(m.getMedicamento_nombre());
        medicamentoNombre.setFocusable(false);
        cantidad_med.setText(""+m.getCantidad());
        repeticion_med.setText(""+m.getRepeticion());
        horario = m.getHora();
        tiempo = horario.split(":");
        hora.setText(""+tiempo[0]);
        min.setText(""+tiempo[1]);
        toma_id = m.getToma_id();

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nombreMed.equals("") || hora.getText().toString().equals("") || min.getText().toString().equals("") || cantidad_med.getText().toString().equals("") || repeticion_med.getText().toString().equals("")) {
                    Toast.makeText(UpdateActivity.this, "Todos los campos deben estar completos", Toast.LENGTH_SHORT).show();
                } else {
                    horas = Integer.parseInt(hora.getText().toString());
                    minutos = Integer.parseInt(min.getText().toString());
                    cantidades = Integer.parseInt(cantidad_med.getText().toString());
                    repeticiones = Integer.parseInt(repeticion_med.getText().toString());
                    if (horas > 23 || minutos >= 60) {
                        Toast.makeText(UpdateActivity.this, "Los valores de hora no pueden ser ruperiores a 23. Los valores de minutos no pueden ser superiores a 59.", Toast.LENGTH_SHORT).show();

                    } else {
                        time = hora.getText().toString() + conector.getText().toString() + min.getText().toString();
                        db.updateToma(toma_id, cantidades, repeticiones, time);
                        Toast.makeText(UpdateActivity.this, "Toma actualizada", Toast.LENGTH_SHORT).show();
                        TimerTask tt = new TimerTask() {
                            @Override
                            public void run() {
                                jumpAct = new Intent(UpdateActivity.this, MenuActivity.class);
                                jumpAct.putExtra("ID", usuario_id);
                                jumpAct.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(jumpAct);

                            }
                        };
                        Timer t = new Timer();
                        t.schedule(tt, 1000);
                    }
                }
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                builder.setTitle("Eliminar toma");
                builder.setMessage("¿Quiere eliminar esta toma?");
                builder.setNegativeButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        med_id = m.getMeds_id();
                        db.deletToma(toma_id);
                        db.deletMeds(med_id);

                        Toast.makeText(UpdateActivity.this, "Toma eliminada", Toast.LENGTH_SHORT).show();
                        TimerTask tt = new TimerTask() {
                            @Override
                            public void run() {
                                jumpAct = new Intent(UpdateActivity.this, MenuActivity.class);
                                jumpAct.putExtra("ID", usuario_id);
                                finish();
                                startActivity(jumpAct);

                            }
                        };
                        Timer t = new Timer();
                        t.schedule(tt, 1000);


                    }
                });
                builder.setPositiveButton("No", null);

                AlertDialog dialog = builder.create();
                dialog.show();


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
                jumpAct = new Intent(UpdateActivity.this, MenuActivity.class);
                jumpAct.putExtra("ID", usuario_id);
                finish();
                startActivity(jumpAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}