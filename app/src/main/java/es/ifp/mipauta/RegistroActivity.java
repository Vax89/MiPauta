package es.ifp.mipauta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    protected TextView registrate;
    protected TextView creaCuenta;
    protected Button registro;
    protected EditText mail;
    protected EditText password;
    protected EditText usuario;
    protected EditText passRepeat;

    private String nombreUser = "";
    private String passUser = "";
    private String mailUser = "";

    protected DataBaseSQL db;

    protected Intent jumpAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        db = new DataBaseSQL(this);
        registrate = (TextView) findViewById(R.id.label1_registro);
        creaCuenta = (TextView) findViewById(R.id.label2_registro);
        usuario = (EditText) findViewById(R.id.nombre_registro);
        mail = (EditText) findViewById(R.id.email_registro);
        password = (EditText) findViewById(R.id.pass_registro);
        passRepeat = (EditText) findViewById(R.id.pass2_registro);
        registro = (Button) findViewById(R.id.regButton_registro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreUser = usuario.getText().toString();
                mailUser = mail.getText().toString();


                if (password.getText().toString().equals("") || usuario.getText().toString().equals("") || mail.getText().toString().equals("") || passRepeat.getText().toString().equals("")) {
                    Toast.makeText(RegistroActivity.this, ("Todos los campos deben estar completos"), Toast.LENGTH_SHORT).show();
                } else {
                   try {
                        User u = db.getUser(nombreUser);

                        if (u.getUsuario().equals(nombreUser)) {
                            Toast.makeText(RegistroActivity.this, ("Usuario invalido"), Toast.LENGTH_SHORT).show();
                        }

                   } catch (Exception e) {
                        if (password.getText().toString().equals(passRepeat.getText().toString())) {
                            passUser = password.getText().toString();
                            jumpAct = new Intent(RegistroActivity.this, LogInActivity.class);
                            finish();
                            startActivity(jumpAct);
                            //Crear base de datos de usuarios
                            db.insertUsuario(nombreUser, mailUser, passUser);
                        } else {
                            Toast.makeText(RegistroActivity.this, ("La contraseña no coincide"), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_registro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.backButton_registro:
                jumpAct = new Intent(RegistroActivity.this, LogInActivity.class);
                finish();
                startActivity(jumpAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}