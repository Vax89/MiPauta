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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class PerfilActivity extends AppCompatActivity {

    protected TextView text1;
    protected TextView text2;
    protected TextView text3;
    protected TextView user;
    protected TextView email;
    protected Button eliminar;

    private Bundle extras;
    private String usuario = "";
    private int usuario_id = 0;
    private String nombre = "";
    private char letraInicial = ' ';

    protected DataBaseSQL db;

    protected Intent jumpAct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        db = new DataBaseSQL(this);
        extras = getIntent().getExtras();
        usuario_id = extras.getInt("ID");

        text1 = (TextView) findViewById(R.id.label1_perfil);
        text2 = (TextView) findViewById(R.id.label2_perfil);
        text3 = (TextView) findViewById(R.id.label4_perfil);
        user = (TextView) findViewById(R.id.label3_perfil);
        email = (TextView) findViewById(R.id.label5_perfil);
        eliminar = (Button) findViewById(R.id.delete_perfil);

        User u = db.getUserById(usuario_id);

        usuario = u.getUsuario();
        nombre = usuario.toLowerCase(Locale.ROOT);
        letraInicial = nombre.charAt(0);
        letraInicial = Character.toUpperCase(letraInicial);
        nombre = letraInicial + nombre.substring(1);
        user.setText(nombre);

        email.setText(u.getEmail().toString());

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
                builder.setTitle("Eliminar usuario");
                builder.setMessage("¿Seguro que quiere elimnar el usuario? Se eliminaran todas las notas");
                builder.setNegativeButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deletUser(usuario);
                        db.deleteAlltomas(usuario_id);
                        db.deleteAllmeds(usuario_id);
                        Toast.makeText(PerfilActivity.this, ("Usuario eliminado"), Toast.LENGTH_SHORT).show();
                        TimerTask tt = new TimerTask() {
                            @Override
                            public void run() {
                                jumpAct = new Intent(PerfilActivity.this, LogInActivity.class);
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
        inflater.inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.back_perfil:
                jumpAct = new Intent(PerfilActivity.this, MenuActivity.class);
                jumpAct.putExtra("ID", usuario_id);
                finish();
                startActivity(jumpAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}