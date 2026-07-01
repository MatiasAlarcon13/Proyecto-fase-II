package laboratorio.Persistencia;

import jakarta.persistence.EntityManager;
import laboratorio.Modelos.Solicitud;
import laboratorio.Modelos.SolicitudCorte;

import java.util.Collections;
import java.util.List;

public class SolicitudCorteDAO {
    
    public void guardar(SolicitudCorte solicitudCorte) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(solicitudCorte);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al guardar la solicitud de corte", e);
        } finally {
            em.close();
        }
    }

    public void actualizar(SolicitudCorte solicitudCorte) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(solicitudCorte);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al actualizar la solicitud de corte", e);
        } finally {
            em.close();
        }
    }

    public SolicitudCorte buscarPorId(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(SolicitudCorte.class, id);
        } finally {
            em.close();
        }
    }

    public List<SolicitudCorte> listarTodas() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT s FROM SolicitudCorte s", SolicitudCorte.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void eliminar(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            SolicitudCorte sc = em.find(SolicitudCorte.class, id);
            if (sc != null) em.remove(sc);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<SolicitudCorte> listarPendientes() {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT s FROM SolicitudCorte s WHERE s.estado = :estado ORDER BY s.fechaSolicitud ASC";
            return entityManager.createQuery(jpql, SolicitudCorte.class)
                    .setParameter("estado", Solicitud.EstadoSolicitud.PENDIENTE)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}
