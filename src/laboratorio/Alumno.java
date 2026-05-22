package laboratorio;

public class Alumno extends Usuario {
    private int cuotaDisponible;
    private int cuotaMax;
    private int descontarCuota;

    @Override
    public boolean puedeImprimir() {
        return cuotaDisponible > 0;
    }

    public Alumno(String nombre, String dni, String correo, int cuotaMax) {
        super(nombre, dni, correo);
        this.cuotaMax = cuotaMax;

    }
}
