package laboratorio.modulos;
import jakarta.persistence.EntityManager;

public class UsuarioTestDAO {
    //CREATE
    public void guardar(UsuarioTest usuario) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager(); //inicia la transaccion
        em.getTransaction().begin(); //abrimos la bveda
        em.persist(usuario); //guardamos el objeto
        em.getTransaction().commit(); //sellamos la transaccion
        em.close();//cierra la conexion
    }

    //READ
    public UsuarioTest buscarPorDni(int dni){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        UsuarioTest usuario = em.find(UsuarioTest.class, dni );
        em.close();
        return usuario;
    }

    //UPDATE
    public void actualizar(UsuarioTest usuario) {
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
        UsuarioTest usuario = em.find(UsuarioTest.class, dni);
        em.remove(usuario);
        em.getTransaction().commit();
        em.close();
    }
}
