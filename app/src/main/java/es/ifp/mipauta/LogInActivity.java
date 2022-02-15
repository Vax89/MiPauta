package es.ifp.mipauta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    protected ImageView logo;
    protected TextView texto1;
    protected TextView texto2;
    protected TextView texto3;
    protected Button logIn;
    protected Button registrar;
    protected EditText mail;
    protected EditText password;

    private String userCatch;
    private int userId;
    private String passCatch;
    private String passDB;

    protected DataBaseSQL db;

    protected Intent jumpAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DataBaseSQL(this);

        logo = (ImageView) findViewById(R.id.logoLogin_login);
        texto1 = (TextView) findViewById(R.id.label1_login);
        texto2 = (TextView) findViewById(R.id.label2_login);
        texto3 = (TextView) findViewById(R.id.label3_login);
        logIn = (Button) findViewById(R.id.logButton_login);
        registrar = (Button) findViewById(R.id.registroButton_login);
        mail = (EditText) findViewById(R.id.email_login);
        password = (EditText) findViewById(R.id.pass_login);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (password.getText().toString().equals("") || mail.getText().toString().equals("")) {
                    Toast.makeText(LogInActivity.this, ("Se ha dejado un campo"), Toast.LENGTH_SHORT).show();
                } else {
                    userCatch = mail.getText().toString();
                    passCatch = password.getText().toString();
                    //Chequeo de tabla Usuarios -> Comparar Usuario / Pass
                    try {
                        User u = db.getUser(userCatch);
                        userId= u.getId();
                        passDB = u.getPassword();
                        if (passCatch.equals(passDB)) {
                            jumpAct = new Intent(LogInActivity.this, MenuActivity.class);
                            jumpAct.putExtra("ID", userId);
                            finish();
                            startActivity(jumpAct);
                        } else {
                            Toast.makeText(LogInActivity.this, ("Email o contraseña equivocados"), Toast.LENGTH_SHORT).show();
                            password.setText("");

                        }

                    } catch (Exception e) {
                        Toast.makeText(LogInActivity.this, ("Email o contraseña equivocados"), Toast.LENGTH_SHORT).show();
                        password.setText("");
                    }

                }

            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpAct = new Intent(LogInActivity.this, RegistroActivity.class);
                finish();
                startActivity(jumpAct);
            }
        });

    }
}