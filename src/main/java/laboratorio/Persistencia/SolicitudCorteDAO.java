package laboratorio.Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import laboratorio.Modelos.Solicitud;
import laboratorio.Modelos.SolicitudCorte;

import java.util.Collections;
import java.util.List;

public class SolicitudCorteDAO {

    public void guardar(SolicitudCorte solicitudCorte) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(solicitudCorte);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public SolicitudCorte buscarSolicitudCorte(int idSolicitud) {
        SolicitudCorte solicitudCorte = null;
        EntityManager entityManager = null;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT s FROM SolicitudCorte s " +
                    "JOIN FETCH s.usuario " +
                    "WHERE s.idSolicitud = :idSolicitud";
            solicitudCorte = entityManager.createQuery(jpql, SolicitudCorte.class)
                    .setParameter("idSolicitud", idSolicitud)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No se encontró la solicitud de corte con ID: " + idSolicitud);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return solicitudCorte;
    }

    public List<SolicitudCorte> listarPendientes() {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT s FROM SolicitudCorte s " +
                    "JOIN FETCH s.usuario " +
                    "WHERE s.estado = :estado " +
                    "ORDER BY s.fechaSolicitud ASC";
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

    public void actualizar(SolicitudCorte solicitudCorte) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(solicitudCorte);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    public void eliminar(int idSolicitud) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            SolicitudCorte solicitudCorte = em.find(SolicitudCorte.class, idSolicitud);

            if (solicitudCorte != null) {
                em.remove(solicitudCorte);
                em.getTransaction().commit();
                System.out.println("Solicitud de corte eliminada correctamente.");
            } else {
                System.out.println("No se puede eliminar: No existe la solicitud de corte con ID " + idSolicitud);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}
