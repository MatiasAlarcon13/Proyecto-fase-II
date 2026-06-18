package laboratorio.Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import laboratorio.Modelos.SolicitudImpresion;
import laboratorio.Modelos.Alumno;

public class SolicitudImpresionDAO {
    public void guardar(SolicitudImpresion solicitudImpresion) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(solicitudImpresion);
        entityManager.getTransaction().commit();
    }

    public laboratorio.Modelos.SolicitudImpresion buscarSolicitudImpresion(int idSolicitud) {
        SolicitudImpresion solicitudImpresion = null;
        EntityManager entityManager = null;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            solicitudImpresion = entityManager.find(SolicitudImpresion.class, idSolicitud);
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

    public void actualizar(SolicitudImpresion solicitudImpresion) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(solicitudImpresion);
        em.getTransaction().commit();
        em.close();
    }
    // DELETE
    //try-catch
    public void eliminar(int idSolicitud) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        SolicitudImpresion solicitudImpresion= em.find(SolicitudImpresion.class, idSolicitud);
        em.remove(solicitudImpresion);
        em.getTransaction().commit();
        em.close();
    }
}
