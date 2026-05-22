package laboratorio;

public class Profesor extends Usuario {
    private Double cuotaDisponible;

    @Override
    public String getRol() {
        return "docente";
    }

    @Override
    public boolean tieneCuotaDisponible(double gramos) {
        return true;
    }

    @Override
    public double getCuota() {
        return cuotaDisponible;
    }



    public Profesor(String nombre, int dni, String correo, double cuotaDisponible) {
        super(nombre, dni, correo);
        this.cuotaDisponible = cuotaDisponible;
    }

    public Double getcuotaDisponible() {return cuotaDisponible;}
    public void setCuotaDisponible(Double cuotaDisponible) {this.cuotaDisponible = cuotaDisponible;}
}
