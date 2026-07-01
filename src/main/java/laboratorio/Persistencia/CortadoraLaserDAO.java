package laboratorio.Persistencia;

import jakarta.persistence.EntityManager;
import laboratorio.Modelos.CortadoraLaser;
import java.util.List;

public class CortadoraLaserDAO {

    public void guardar(CortadoraLaser cortadora) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cortadora);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al guardar la cortadora", e);
        } finally {
            em.close();
        }
    }

    public CortadoraLaser buscarPorId(String id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(CortadoraLaser.class, id);
        } finally {
            em.close();
        }
    }

    public void actualizar(CortadoraLaser cortadora) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cortadora);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al actualizar la cortadora", e);
        } finally {
            em.close();
        }
    }

    public List<CortadoraLaser> listarTodas() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CortadoraLaser c", CortadoraLaser.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void eliminar(String id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            CortadoraLaser c = em.find(CortadoraLaser.class, id);
            if (c != null) em.remove(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
