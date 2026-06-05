package laboratorio.Controladores;
import jakarta.persistence.*;

@Entity
@Table(name = "Alumno")
@PrimaryKeyJoinColumn(name = "dni")

public class Alumno extends Usuario {
    @Column(name = "cuotaDisponible")
    private double cuotaDisponible;
    @Column(name = "cuotaMax")
    private double cuotaMax = 500.00;

    public Alumno(String nombre, int dni, String correo, double cuotaMax) {
        super(nombre, dni, correo);
        this.cuotaMax = cuotaMax;
        cuotaDisponible = cuotaMax;
    }
    public Alumno(){
        super();
    }

    @Override
    public String getRol() {return "estudiante";}

    @Override
    public boolean tieneCuotaDisponible(double gramos) {
        return cuotaDisponible >= gramos;
    }

    @Override
    public double getCuota() {
        return cuotaDisponible;
    }

    public void calculoCuota(SolicitudImpresion solicitud) {
        if (cuotaDisponible >= solicitud.getGramosRequeridos()) {
            cuotaDisponible -= solicitud.getGramosRequeridos();
        } else {
            System.out.println("Limite de cuota alcanzado.");
        }
    }

    public double getCuotaDisponible() {return cuotaDisponible;}
    public void setCuotaDisponible(double cuotaDisponible) {this.cuotaDisponible = cuotaDisponible;}

    public double getCuotaMax() {return cuotaMax;}
    public void setCuotaMax(double cuotaMax) {this.cuotaMax = cuotaMax;}

}
