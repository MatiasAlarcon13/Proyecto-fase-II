package laboratorio.modulos;

import laboratorio.Modelos.Alumno;
import laboratorio.Modelos.Usuario;

public class MainPrueba {
    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();

        // Crear y guardar un usuario
        Usuario usuario = new Usuario() {
            @Override
            public boolean tieneCuotaDisponible(double gramos) {
                return false;
            }
            @Override
            public double getCuota() {
                return 0;
            }
            @Override
            public String getRol() {
                return "";
            }
        };
        usuario.setDni(45456290);
        usuario.setNombre("Mati");
        usuario.setCorreo("mati@gmail.com");

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