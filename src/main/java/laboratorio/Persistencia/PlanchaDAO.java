package laboratorio.Persistencia;

import jakarta.persistence.EntityManager;
import laboratorio.Modelos.Plancha;
import java.util.List;

public class PlanchaDAO {

    public void guardar(Plancha plancha) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(plancha);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al guardar la plancha", e);
        } finally {
            em.close();
        }
    }

    public List<Plancha> listarTodas() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Plancha p", Plancha.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Plancha buscarPorTipo(String tipo) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Usamos LIKE para ser más permisivos y trim para limpiar espacios
            return em.createQuery("SELECT p FROM Plancha p WHERE p.tipoMaterial = :tipo", Plancha.class)
                     .setParameter("tipo", tipo)
                     .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void actualizar(Plancha plancha) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(plancha);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al actualizar la plancha", e);
        } finally {
            em.close();
        }
    }
}
