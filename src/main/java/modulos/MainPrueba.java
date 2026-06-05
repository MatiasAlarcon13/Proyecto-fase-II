package modulos;

import laboratorio.Controladores.Alumno;

public class MainPrueba {
    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();

        // Crear y guardar un usuario
        Usuario usuario= new Usuario();
        usuario.setDni(44567123);
        usuario.setNombre("Juan");
        usuario.setCorreo("juan@gmail.com");

        Alumno alumno= new Alumno();
        alumno.setCuotaDisponible(24);
        alumno.setCuotaMax(500);

        dao.guardar(usuario);
        System.out.println("✅ Guardado con ID: " + usuario.getDni());
        dao.guardar(alumno);
        System.out.println("Se guardo datos");

        JPAUtil.close();
    }
}