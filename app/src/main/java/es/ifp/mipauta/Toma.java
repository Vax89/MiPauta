package es.ifp.mipauta;

public class Toma {
    protected int toma_id = 0;
    protected String medicamento_nombre = "";
    protected int cantidad = 0;
    protected int repeticion = 0;
    protected int tipo = 0;
    protected int usuario_id = 0;
    protected int meds_id=0;
    protected String hora="";


    public Toma(int toma_id, String medicamento_nombre, int cantidad, int repeticion, int tipo,  String hora, int usuario_id, int meds_id) {
        this.toma_id = toma_id;
        this.medicamento_nombre = medicamento_nombre;
        this.cantidad = cantidad;
        this.repeticion = repeticion;
        this.tipo = tipo;
        this.usuario_id = usuario_id;
        this.meds_id=meds_id;
        this.hora=hora;

    }

    public int getToma_id() {
        return this.toma_id;
    }

    public void setTomas_id(int toma_id) {
        this.toma_id = toma_id;
    }

    public String getMedicamento_nombre() {
        return this.medicamento_nombre;
    }



    public void setMedicamento_nombre(String medicamento_nombre) {
        this.medicamento_nombre = medicamento_nombre;
    }

    public String getHora() {
        return this.hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getTipo() {
        return this.tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getRepeticion() {
        return this.repeticion;
    }

    public void setRepeticion(int repeticion) {
        this.repeticion = repeticion;
    }

    public int getUsuario_id() {
        return this.usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getMeds_id() {
        return this.meds_id;
    }

    public void setMeds_id(int meds_id) {
        this.meds_id = meds_id;
    }

}
