package laboratorio.Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import laboratorio.Modelos.Solicitud;
import laboratorio.Modelos.SolicitudImpresion;

import java.util.Collections;
import java.util.List;

public class SolicitudImpresionDAO {
    public void guardar(SolicitudImpresion solicitudImpresion) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(solicitudImpresion);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if  (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }finally {
            if (entityManager!= null && entityManager.isOpen()) {
                entityManager.close();
            }
        }

    }


    public SolicitudImpresion buscarSolicitudImpresion(int idSolicitud) {
        SolicitudImpresion solicitudImpresion = null;
        EntityManager entityManager = null;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT s FROM SolicitudImpresion s " +
                    "JOIN FETCH s.usuario " +
                    "WHERE s.idSolicitud = :idSolicitud";
            solicitudImpresion = entityManager.createQuery(jpql, SolicitudImpresion.class)
                    .setParameter("idSolicitud", idSolicitud)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No se encontró la solicitud con ID: " + idSolicitud);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return solicitudImpresion;
    }

    public List<SolicitudImpresion> listarPendientes() {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT s FROM SolicitudImpresion s " +
                    "JOIN FETCH s.usuario " +
                    "WHERE s.estado = :estado " +
                    "ORDER BY s.fechaSolicitud ASC";
            return entityManager.createQuery(jpql, SolicitudImpresion.class)
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

    public void actualizar(SolicitudImpresion solicitudImpresion) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(solicitudImpresion);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    //eliminar
    public void eliminar(int idSolicitud) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            SolicitudImpresion solicitudImpresion = em.find(SolicitudImpresion.class, idSolicitud);

            if (solicitudImpresion != null) {
                em.remove(solicitudImpresion);
                em.getTransaction().commit();
                System.out.println("Solicitud eliminada correctamente.");
            } else {
                System.out.println("No se puede eliminar: No existe la solicitud con ID " + idSolicitud);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}
