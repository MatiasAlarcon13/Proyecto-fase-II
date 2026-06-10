package laboratorio.modulos;
import jakarta.persistence.EntityManager;
import laboratorio.Controladores.Usuario;

public class UsuarioTestDAO {
    //CREATE
    public void guardar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager(); //inicia la transaccion
        em.getTransaction().begin(); //abrimos la bveda
        em.persist(usuario); //guardamos el objeto
        em.getTransaction().commit(); //sellamos la transaccion
        em.close();//cierra la conexion
    }

    //READ
    public Usuario buscarPorDni(int dni){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Usuario usuario = em.find(Usuario.class, dni );
        em.close();
        return usuario;
    }

    //UPDATE
    public void actualizar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
        em.close();
    }

    // DELETE
    public void eliminar(int dni) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Usuario usuario = em.find(Usuario.class, dni);
        em.remove(usuario);
        em.getTransaction().commit();
        em.close();
    }
}
