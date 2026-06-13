package laboratorio.Modelos;
import jakarta.persistence.*;

@Entity
@Table(name = "Profesor")
@PrimaryKeyJoinColumn(name = "dni")
@DiscriminatorValue("Docente")
public class Profesor extends Usuario {
    @Column (name = "cuotaDisponible")
    private int cuotaDisponible;

    public Profesor(String nombre, int dni, String correo, int cuotaDisponible) {
        super(nombre, dni, correo);
        this.cuotaDisponible = cuotaDisponible;
    }
    public Profesor(){
    }

    @Override
    public String getRol() { return "docente"; }

    @Override
    public boolean tieneCuotaDisponible(double gramos) { return true; }

    @Override
    public double getCuota() { return cuotaDisponible; }

    public int getcuotaDisponible() { return cuotaDisponible; }
    public void setCuotaDisponible(int cuotaDisponible) { this.cuotaDisponible = cuotaDisponible; }
}
