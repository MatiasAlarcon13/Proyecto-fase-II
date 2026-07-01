package laboratorio.Persistencia;
import jakarta.persistence.*;
@Entity
@Table(name = "Usuario")

public class UsuarioTest {
    @Id
    @Column(name = "dni")
    protected int dni;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "correo")
    private String correo;

    public UsuarioTest() {
    }
    //setters
    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //gettters
    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }
}
