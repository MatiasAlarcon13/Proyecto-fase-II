package laboratorio.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
@DiscriminatorColumn(name="Categoria", discriminatorType=DiscriminatorType.STRING)//columna para declarar el tipo de usuario

public abstract class Usuario {
    @Column (name = "nombre")
    protected String nombre;
    @Column (name = "correo")
    protected String correo;
    @Id
    @Column (name = "dni")
    protected int dni;

    public Usuario(String nombre, int dni, String correo) {
        this.nombre = nombre;
        this.dni = dni;
        this.correo = correo;
    }
    public Usuario(){

    }

    public abstract boolean tieneCuotaDisponible(double gramos);
    public abstract double getCuota();
    public abstract String getRol();

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getDni() { return dni; }
    public void setDni(int dni) { this.dni = dni; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}
