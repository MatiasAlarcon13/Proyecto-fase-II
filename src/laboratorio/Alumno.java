package laboratorio;

public class Alumno extends Usuario {
    private double cuotaDisponible;
    private double cuotaMax = 500.00;
    private double descontarCuota;

    @Override
    public void asignarCuota() {
        cuotaDisponible = cuotaMax;
    }

    @Override
    public boolean tieneCuotaDisponible(double gramos) {
        return cuotaDisponible >= gramos ;
    }

    @Override
    public double getCuota() {
        return cuotaDisponible;
    }

    public Alumno(String nombre, int dni, String correo, double  cuotaMax) {
        super(nombre, dni, correo);
        this.cuotaMax = cuotaMax;
    }


    public void calculoCuota(SolicitudImpresion solicitud) {
        if (cuotaDisponible > 0){
            descontarCuota = solicitud.getGramosRequeridos();
            cuotaDisponible -= descontarCuota;
        }
    }

    public double getCuotaDisponible() {return cuotaDisponible;}
    public void setCuotaDisponible(double cuotaDisponible) {this.cuotaDisponible = cuotaDisponible;}

    public double getCuotaMax() {return cuotaMax;}
    public void setCuotaMax(double cuotaMax) {this.cuotaMax = cuotaMax;}

    public double getDescontarCuota() {return descontarCuota;}
    public void setDescontarCuota(double descontarCuota) {this.descontarCuota = descontarCuota;}
}
