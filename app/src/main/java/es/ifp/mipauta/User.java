package es.ifp.mipauta;

public class User {

    protected int id = 0;
    protected String usuario = "";
    protected String email = "";
    protected String password = "";

    public User(int id, String usuario, String email, String password) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.password = password
        ;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String id) {
        this.password = password;
    }

}

