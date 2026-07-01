package laboratorio.Persistencia;

import laboratorio.Modelos.Alumno;
import laboratorio.Modelos.Profesor;

public class MainPrueba {
    public static void main(String[] args) {

        AlumnoDAO dao = new AlumnoDAO();
        ProfesorDAO daoProf = new ProfesorDAO();
        // 1. Instanciamos directamente al Alumno (sin llaves abstractas)
        Alumno alumno = new Alumno();
        Profesor profesor = new Profesor();

        // 2. Le cargamos los datos que hereda de Usuario
        profesor.setDni(22432554);
        profesor.setNombre("Mario Luis");
        profesor.setCorreo("marioluis@gmail.com");

        // 3. Le cargamos los datos propios de Alumno
        profesor.setCuotaDisponible(200);

        // 4. Guardamos al alumno a través del DAO
        daoProf.guardar(profesor);
        System.out.println("✅ Profesor guardado con ID: " + profesor.getDni());

        JPAUtil.close();
    }
}