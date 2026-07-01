package laboratorio.Modelos;

import jakarta.persistence.*;
import laboratorio.Persistencia.AlumnoDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Alumno")
@PrimaryKeyJoinColumn(name = "dni")
@DiscriminatorValue("Alumno")

public class Alumno extends Usuario {
    @Column (name = "cuotaDisponible")
    private double cuotaDisponible;
    @Column (name = "cuotaMax")
    private double cuotaMax = 500.00;
    
    @Column(name = "fechaUltimoReset")
    private String fechaUltimoReset;

    public Alumno(String nombre, int dni, String correo, double cuotaMax) {
        super(nombre, dni, correo);
        this.cuotaMax = cuotaMax;
        this.cuotaDisponible = cuotaMax;
        this.fechaUltimoReset = LocalDate.now().toString();
    }
    public Alumno(){
        super();
    }

    private void reiniciarCuotaSiCorresponde() {
        LocalDate hoy = LocalDate.now();
        LocalDate ultimaFecha = (fechaUltimoReset == null) ? LocalDate.MIN : LocalDate.parse(fechaUltimoReset);

        if (hoy.getMonth() != ultimaFecha.getMonth() || hoy.getYear() != ultimaFecha.getYear()) {
            this.cuotaDisponible = this.cuotaMax;
            this.fechaUltimoReset = hoy.toString();
            
            // Persistir cambios
            new AlumnoDAO().actualizar(this);
        }
    }

    @Override
    public String getRol() { return "estudiante"; }

    @Override
    public boolean tieneCuotaDisponible(double gramos) {
        reiniciarCuotaSiCorresponde();
        return cuotaDisponible >= gramos;
    }

    @Override
    public double getCuota() {
        reiniciarCuotaSiCorresponde();
        return cuotaDisponible;
    }

    public void descontarCuota(SolicitudImpresion solicitud) {
        reiniciarCuotaSiCorresponde();
        if (cuotaDisponible >= solicitud.getGramosRequeridos()) {
            cuotaDisponible -= solicitud.getGramosRequeridos();
            new AlumnoDAO().actualizar(this);
        }
        // La vista maneja el mensaje de error
    }

    public double getCuotaDisponible() { return cuotaDisponible; }
    public void setCuotaDisponible(double cuotaDisponible) { this.cuotaDisponible = cuotaDisponible; }
    public double getCuotaMax() { return cuotaMax; }
    public void setCuotaMax(double cuotaMax) { this.cuotaMax = cuotaMax; }
}
