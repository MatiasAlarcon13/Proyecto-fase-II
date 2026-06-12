package laboratorio.modulos;

import laboratorio.Modelos.Alumno;
import laboratorio.Modelos.Usuario;

public class MainPrueba {
    public static void main(String[] args) {

        AlumnoDAO dao = new AlumnoDAO();
        // 1. Instanciamos directamente al Alumno (sin llaves abstractas)
        Alumno alumno = new Alumno();

        // 2. Le cargamos los datos que hereda de Usuario
        alumno.setDni(47213777);
        alumno.setNombre("Leo");
        alumno.setCorreo("leo@gmail.com");

        // 3. Le cargamos los datos propios de Alumno
        alumno.setCuotaDisponible(200);
        alumno.setCuotaMax(500);

        // 4. Guardamos al alumno a través del DAO
        dao.guardar(alumno);
        System.out.println("✅ Alumno guardado con ID: " + alumno.getDni());

        JPAUtil.close();
    }
}