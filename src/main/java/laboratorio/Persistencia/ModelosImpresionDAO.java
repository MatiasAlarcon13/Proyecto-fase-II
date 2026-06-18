package laboratorio.Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import laboratorio.Modelos.Alumno;
import laboratorio.Modelos.ModelosImpresion;

public class ModelosImpresionDAO {
    public void guardar(ModelosImpresion modelosImpresion) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(modelosImpresion);
        entityManager.getTransaction().commit();
    }

    public laboratorio.Modelos.ModelosImpresion buscarModelos(int idModelo) {
        ModelosImpresion modelosImpresion = null;
        EntityManager entityManager = null;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            modelosImpresion = entityManager.find(ModelosImpresion.class, idModelo);
        } catch (NoResultException e) {

            System.out.println("No se encontró el modelo con ID: " + idModelo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return modelosImpresion;
    }
    public void actualizar(ModelosImpresion modelosImpresion) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(modelosImpresion);
        em.getTransaction().commit();
        em.close();
    }

    public void eliminar(int idModelo) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        ModelosImpresion modelosImpresion= em.find(ModelosImpresion.class, idModelo);
        em.remove(modelosImpresion);
        em.getTransaction().commit();
        em.close();
    }
}
