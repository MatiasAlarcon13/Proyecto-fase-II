package laboratorio.Modelos;

public class Profesor extends Usuario {
    private Double cuotaDisponible;

    public Profesor(String nombre, int dni, String correo, double cuotaDisponible) {
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

    public Double getcuotaDisponible() { return cuotaDisponible; }
    public void setCuotaDisponible(Double cuotaDisponible) { this.cuotaDisponible = cuotaDisponible; }
}
