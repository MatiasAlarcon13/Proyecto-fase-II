package laboratorio.Persistencia;

import jakarta.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import laboratorio.Modelos.ModelosImpresion;

public class ModelosImpresionDAO {
    private static final Logger logger = Logger.getLogger(ModelosImpresionDAO.class.getName());

    public void guardarModelo(ModelosImpresion modelosImpresion) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(modelosImpresion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Deshace cambios si falla
            }
            logger.log(Level.SEVERE, "Error al guardar el modelo", ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public ModelosImpresion buscarModelos(String nombreModelo) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT m FROM ModelosImpresion m WHERE m.nombreModelo = :nombre", ModelosImpresion.class)
                    .setParameter("nombre", nombreModelo)
                    .getSingleResult();
        } catch (Exception ex) {
            logger.log(Level.WARNING, "No se encontró el modelo: " + nombreModelo);
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    public void actualizarModelo(ModelosImpresion modelosImpresion) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(modelosImpresion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Error al actualizar el modelo", ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void eliminarModelo(int idModelo) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            // Buscamos el modelo dentro del contexto de persistencia actual usando su ID entero
            ModelosImpresion modelosImpresion = em.find(ModelosImpresion.class, idModelo);
            if (modelosImpresion != null) {
                em.remove(modelosImpresion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Error defensivo al eliminar el modelo", ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
