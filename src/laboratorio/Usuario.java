package laboratorio;

public abstract class Usuario {
    protected String nombre;
    protected String correo;
    protected String rol;
    protected int dni;

    public Usuario(String nombre, int dni, String correo) {
        this.nombre = nombre;
        this.dni = dni;
        this.correo = correo;
    }

    public abstract void asignarCuota();

    public abstract boolean tieneCuotaDisponible(double gramos);

    public abstract double getCuota();

    public abstract String getRol();



    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }
    public void setDni(int dni) {this.dni = dni;}

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setRol(String rol) {this.rol = rol;}


}
