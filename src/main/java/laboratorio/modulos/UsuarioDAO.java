package laboratorio.modulos;
import jakarta.persistence.EntityManager;
import laboratorio.Modelos.Usuario;

public class UsuarioDAO {
    public void guardar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager(); //inicia la transaccion
        em.getTransaction().begin(); //abrimos la bveda
        em.persist(usuario); //guardamos el objeto
        em.getTransaction().commit(); //sellamos la transaccion
        em.close();//cierra la conexion
    }

    public laboratorio.Modelos.Usuario buscarPorDni(int dni){
        Usuario usuario = null;
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT u FROM Usuario u WHERE u.dni = :dniConsultado";
            usuario = em.createQuery(jpql, Usuario.class)
                    .setParameter("dniConsultado", dni)
                    .getSingleResult();

        }
        catch(jakarta.persistence.NoResultException e){
            System.out.println("No se encontro ningun usuario con el DNI: " +dni);
        }
        catch(Exception ex){
            System.out.println("Error en la consulta: " + ex.getMessage());
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return usuario;
    }

    public void actualizar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
        em.close();
    }
    public void eliminar(int dni) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Usuario usuario = em.find(Usuario.class, dni);
        em.remove(usuario);
        em.getTransaction().commit();
        em.close();
    }
}
