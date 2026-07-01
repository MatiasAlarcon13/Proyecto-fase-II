package laboratorio.Persistencia;
import jakarta.persistence.EntityManager;

public class UsuarioTestDAO {
    //CREATE
    public void guardar(UsuarioTest usuarioTest) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager(); //inicia la transaccion
        em.getTransaction().begin(); //abrimos la bveda
        em.persist(usuarioTest); //guardamos el objeto
        em.getTransaction().commit(); //sellamos la transaccion
        em.close();//cierra la conexion
    }

    //READ
    public UsuarioTest buscarPorDni(int dni){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        UsuarioTest usuarioTest = em.find(UsuarioTest.class, dni );
        em.close();
        return usuarioTest;
    }

    //UPDATE
    public void actualizar(UsuarioTest usuarioTest) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(usuarioTest);
        em.getTransaction().commit();
        em.close();
    }

    // DELETE
    public void eliminar(int dni) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        UsuarioTest usuarioTest = em.find(UsuarioTest.class, dni);
        em.remove(usuarioTest);
        em.getTransaction().commit();
        em.close();
    }
}
