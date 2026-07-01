package laboratorio.Controladores;

import laboratorio.Modelos.Alumno;
import laboratorio.Modelos.Profesor;
import laboratorio.Modelos.Usuario;
import laboratorio.Modelos.SolicitudImpresion;
import laboratorio.Persistencia.AlumnoDAO;
import laboratorio.Persistencia.ProfesorDAO;

public class UsuarioController {

    private final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private final ProfesorDAO profesorDAO = new ProfesorDAO();

    public Alumno crearAlumno(String nombre, int dni, String correo, double cuotaMax) {
        Alumno nuevoAlumno = new Alumno(nombre, dni, correo, cuotaMax);
        alumnoDAO.guardar(nuevoAlumno); //PERSISTE EN LA BD
        return nuevoAlumno;
    }

    public Profesor crearProfesor(String nombre, int dni, String correo, int cuotaDisponible) {
        Profesor nuevoProfesor = new Profesor(nombre, dni, correo, cuotaDisponible);
        profesorDAO.guardar(nuevoProfesor);
        return nuevoProfesor;
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
        alumnoDAO.actualizar(alumno);
        return true;
    }
}
