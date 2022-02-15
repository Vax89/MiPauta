package es.ifp.mipauta;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DataBaseSQL extends SQLiteOpenHelper {

    protected SQLiteDatabase db;

    public DataBaseSQL(Context context) {
        super(context, "mipauta", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table usuarios(id integer primary key autoincrement not null, usuario text, email text, password text)");
        db.execSQL("CREATE table medicamentos(medicamento_id integer primary key autoincrement not null, medicamento_nombre text not null, usuario_id integer," +
                "CONSTRAINT fk_usuarios" +
                "   FOREIGN KEY (usuario_id)" +
                "   REFERENCES usuarios(id))");
        db.execSQL("CREATE table tomas(toma_id integer primary key autoincrement not null," +
                "medicamento_nombre text not null," +
                "cantidad integer not null," +
                "repeticion integer not null," +
                "tipo integer not null," +
                "hora text not null," +
                "usuario_id integer not null," +
                "medicamento_id integer not null," +
                " CONSTRAINT fk_usuarios" +
                "    FOREIGN KEY (usuario_id)" +
                "    REFERENCES usuarios(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
    }

    public void insertUsuario(String usuario, String email, String password) {
        db = this.getReadableDatabase();
        db.execSQL("INSERT INTO usuarios (usuario, email, password) VALUES ('" + usuario + "', '" + email + "','" + password + "')");
    }

    public void insertToma(String medicamento_nombre, int cantidad, int repeticion, int tipo, String hora, int usuario_id, int medicamento_id) {
        db = this.getReadableDatabase();
        db.execSQL("INSERT INTO tomas (medicamento_nombre, cantidad, repeticion, tipo, hora, usuario_id, medicamento_id) VALUES ('" + medicamento_nombre + "','" + cantidad + "', '" + repeticion + "','" + tipo + "','" + hora + "', '" + usuario_id + "','"+medicamento_id +"') ");
    }

    public void insertMeds(String medicamento_nombre, int usuario_id) {
        db = this.getReadableDatabase();
        db.execSQL("INSERT INTO medicamentos (medicamento_nombre, usuario_id) VALUES ('" + medicamento_nombre + "','"+usuario_id+"') ");
    }

    public void deletUser(String usuario) {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM usuarios WHERE usuario= '" + usuario + "'");
    }

    public void deletToma(int toma_id) {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM tomas WHERE toma_id= '" + toma_id + "'");

    }
    public void deleteAlltomas(int usuario_id) {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM tomas WHERE usuario_id = '"+usuario_id+"'");
    }

    public void deletMeds(int medicamento_id) {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM medicamentos WHERE medicamento_id= '" + medicamento_id + "'");

    }

    public void deleteAllmeds(int usuario_id) {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM medicamentos WHERE usuario_id = '"+usuario_id+"'");
    }

    public void updateToma(int toma_id, int cantidad, int repeticion, String hora) {
        db = this.getWritableDatabase();
        db.execSQL("UPDATE tomas SET cantidad ='" + cantidad + "',repeticion ='" + repeticion + "',hora ='" + hora + "' WHERE toma_id=" + toma_id);
    }

    public int numberOfUsers() {
        int num = 0;
        db = this.getReadableDatabase();
        num = (int) DatabaseUtils.queryNumEntries(db, "usuarios");
        return num;

    }

    public int numberOfTomas() {
        int num = 0;
        db = this.getReadableDatabase();
        num = (int) DatabaseUtils.queryNumEntries(db, "tomas");
        return num;
    }

    public int numberOfMeds() {
        int num = 0;
        db = this.getReadableDatabase();
        num = (int) DatabaseUtils.queryNumEntries(db, "medicamentos");
        return num;
    }

    public boolean isEmpty() {
        boolean estaVacio = true;
        if (numberOfTomas() > 0) {
            return false;
        } else {

            return true;
        }
    }

    public User getUser(String usuario) {
        User n = null;
        Cursor res = null;
        String contenido = "";
        if (numberOfUsers() > 0) {
            db = this.getReadableDatabase();
            res = db.rawQuery("SELECT * FROM usuarios WHERE usuario ='" + usuario + "' ORDER BY id", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                n = new User(res.getInt(res.getColumnIndex("id")),
                        res.getString(res.getColumnIndex("usuario")),
                        res.getString(res.getColumnIndex("email")),
                        res.getString(res.getColumnIndex("password")));

                res.moveToNext();
            }
        }
        return n;
    }

    public User getUserById(int id) {
        User n = null;
        Cursor res = null;
        String contenido = "";
        if (numberOfUsers() > 0) {
            db = this.getReadableDatabase();
            res = db.rawQuery("SELECT * FROM usuarios WHERE id ='" + id + "' ORDER BY id", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                n = new User(res.getInt(res.getColumnIndex("id")),
                        res.getString(res.getColumnIndex("usuario")),
                        res.getString(res.getColumnIndex("email")),
                        res.getString(res.getColumnIndex("password")));

                res.moveToNext();
            }
        }
        return n;
    }
    public ArrayList<String> getAllTomas(int usuario_id) {
        ArrayList<String> filas = new ArrayList<String>();
        Cursor res = null;
        String contenido = "";
        if (numberOfTomas() > 0) {
            db = this.getReadableDatabase();
            res = db.rawQuery("SELECT * FROM tomas WHERE usuario_id='" + usuario_id + "' ORDER BY toma_id", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                contenido ="\n"+res.getString(res.getColumnIndex("medicamento_nombre"))+"\nPastillas: " + res.getInt(res.getColumnIndex("cantidad")) + "\nHora: " + res.getString(res.getColumnIndex("hora"))+"\n";
                filas.add(contenido);
                res.moveToNext();
            }
        } else {
            contenido = "Añadir nota";
            filas.add(contenido);
        }
        return filas;
    }
    public Toma getToma(String medicamento_nombre) {
        Toma u = null;
        Cursor res = null;
        String contenido = "";
        if (numberOfTomas() > 0) {
            db = this.getReadableDatabase();
            res = db.rawQuery("SELECT * FROM tomas WHERE medicamento_nombre ='" + medicamento_nombre + "' ORDER BY toma_id", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                u = new Toma(res.getInt(res.getColumnIndex("toma_id")),
                        res.getString(res.getColumnIndex("medicamento_nombre")),
                        res.getInt(res.getColumnIndex("cantidad")),
                        res.getInt(res.getColumnIndex("repeticion")),
                        res.getInt(res.getColumnIndex("tipo")),
                        res.getString(res.getColumnIndex("hora")),
                        res.getInt(res.getColumnIndex("usuario_id")),
                        res.getInt(res.getColumnIndex("medicamento_id")));

                res.moveToNext();
            }
        }
        return u;
    }
    public Meds getMeds(String medicamento_nombre) {
        Meds u = null;
        Cursor res = null;
        String contenido = "";
        if (numberOfMeds() > 0) {
            db = this.getReadableDatabase();
            res = db.rawQuery("SELECT * FROM medicamentos WHERE medicamento_nombre ='" + medicamento_nombre + "' ORDER BY medicamento_id", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                u = new Meds(res.getInt(res.getColumnIndex("medicamento_id")),
                        res.getString(res.getColumnIndex("medicamento_nombre")),
                        res.getInt(res.getColumnIndex("usuario_id")));

                res.moveToNext();
            }
        }
        return u;
    }
}
