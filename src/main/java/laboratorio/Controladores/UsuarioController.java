package laboratorio.Controladores;

import laboratorio.Modelos.Alumno;
import laboratorio.Modelos.Profesor;
import laboratorio.Modelos.Usuario;
import laboratorio.Modelos.SolicitudImpresion;

public class UsuarioController {

    public Alumno crearAlumno(String nombre, int dni, String correo, double cuotaMax) {
        return new Alumno(nombre, dni, correo, cuotaMax);
    }

    public Profesor crearProfesor(String nombre, int dni, String correo, double cuotaDisponible) {
        return new Profesor(nombre, dni, correo, cuotaDisponible);
    }

    public boolean tieneCuota(Usuario usuario, double gramos) {
        return usuario.tieneCuotaDisponible(gramos);
    }

    public double getCuota(Usuario usuario) {
        return usuario.getCuota();
    }

    /**
     * Descuenta cuota a un Alumno.
     * Retorna false si no tiene cuota suficiente.
     */
    public boolean descontarCuotaAlumno(Alumno alumno, SolicitudImpresion solicitud) {
        if (!alumno.tieneCuotaDisponible(solicitud.getGramosRequeridos())) return false;
        alumno.descontarCuota(solicitud);
        return true;
    }
}
