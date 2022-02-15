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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MenuActivity extends AppCompatActivity {

    protected TextView texto1;
    protected TextView userName;
    protected ListView tomas;
    protected Button perfil;
    protected Button addToma;
    protected Button calendar;

    private Bundle extras;
    private String usuario = "";
    private int usuario_id = 0;
    private int toma_id = 0;
    private int med_id = 0;
    private String nombre = "";
    private char letraInicial = ' ';
    private String contenidoItem = "";
    private ArrayList<String> filas = new ArrayList<String>();
    private ArrayAdapter<String> adaptador;
    private String[] partes;

    protected DataBaseSQL db;

    protected Intent jumpAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = new DataBaseSQL(this);

        texto1 = (TextView) findViewById(R.id.label1_menu);
        userName = (TextView) findViewById(R.id.label2_menu);
        tomas = (ListView) findViewById(R.id.listado_menu);
        addToma = (Button) findViewById(R.id.add_menu);
        perfil = (Button) findViewById(R.id.usuario_menu);
        calendar = (Button) findViewById(R.id.calendario_menu);

        extras = getIntent().getExtras();
        if (extras != null) {
            usuario_id = extras.getInt("ID");
            User us = db.getUserById(usuario_id);
            usuario = us.getUsuario();
            nombre = usuario.toLowerCase(Locale.ROOT);
            letraInicial = nombre.charAt(0);
            letraInicial = Character.toUpperCase(letraInicial);
            nombre = letraInicial + nombre.substring(1);
            userName.setText(nombre);

        }
        filas = db.getAllTomas(usuario_id);
        if (db.isEmpty()) {
            filas.set(0, "No hay tomas programadas.");
        }
        adaptador = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_list_item_1, filas);
        tomas.setAdapter(adaptador);

        db.close();
        tomas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (db.isEmpty()) {
                    Toast.makeText(MenuActivity.this, "No existe ningúna toma", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    contenidoItem = parent.getItemAtPosition(position).toString();

                    jumpAct = new Intent(MenuActivity.this, UpdateActivity.class);
                    jumpAct.putExtra("ID", usuario_id);
                    jumpAct.putExtra("MED", contenidoItem);

                    finish();
                    startActivity(jumpAct);


                    return true;


                }
            }
        });

        addToma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jumpAct = new Intent(MenuActivity.this, AddActivity.class);
                jumpAct.putExtra("ID", usuario_id);
                finish();
                startActivity(jumpAct);

            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jumpAct = new Intent(MenuActivity.this, PerfilActivity.class);
                jumpAct.putExtra("ID", usuario_id);
                finish();
                startActivity(jumpAct);

            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logOff_menu:
                jumpAct = new Intent(MenuActivity.this, LogInActivity.class);
                finish();
                startActivity(jumpAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}