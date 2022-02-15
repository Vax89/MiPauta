package es.ifp.mipauta;

public class Meds {

    protected int med_id = 0;
    protected String medicament_nombre = "";
    protected int usuario_id=0;

    public Meds(int med_id, String medicament_nombre, int usuario_id){
        this.med_id = med_id;
        this.medicament_nombre=medicament_nombre;
        this.usuario_id= this.usuario_id;
    }

    public int getMed_id() {
        return this.med_id;
    }

    public String getMedicament_nombre() {
        return this.medicament_nombre;
    }
    public int getUser_id() {
        return this.usuario_id;
    }

    public void setMeds_id(int med_id) {
        this.med_id = med_id;
    }

    public void setMedicament_nombre(String medicamento_nombre) {
        this.medicament_nombre = medicamento_nombre;
    }
    public void setUser_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}
