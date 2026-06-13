package laboratorio.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "Alumno")
@PrimaryKeyJoinColumn(name = "dni")
@DiscriminatorValue("Alumno")

public class Alumno extends Usuario {
    @Column (name = "cuotaDisponible")
    private double cuotaDisponible;
    @Column (name = "cuotaMax")
    private double cuotaMax = 500.00;

    public Alumno(String nombre, int dni, String correo, double cuotaMax) {
        super(nombre, dni, correo);
        this.cuotaMax = cuotaMax;
        this.cuotaDisponible = cuotaMax;
    }
    public Alumno(){
        super();
    }

    @Override
    public String getRol() { return "estudiante"; }

    @Override
    public boolean tieneCuotaDisponible(double gramos) {
        return cuotaDisponible >= gramos;
    }

    @Override
    public double getCuota() { return cuotaDisponible; }

    public void descontarCuota(SolicitudImpresion solicitud) {
        if (cuotaDisponible >= solicitud.getGramosRequeridos()) {
            cuotaDisponible -= solicitud.getGramosRequeridos();
        }
        // La vista maneja el mensaje de error
    }

    public double getCuotaDisponible() { return cuotaDisponible; }
    public void setCuotaDisponible(double cuotaDisponible) { this.cuotaDisponible = cuotaDisponible; }
    public double getCuotaMax() { return cuotaMax; }
    public void setCuotaMax(double cuotaMax) { this.cuotaMax = cuotaMax; }
}
