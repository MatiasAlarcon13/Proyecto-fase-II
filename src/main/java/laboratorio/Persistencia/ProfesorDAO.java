package laboratorio.Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import laboratorio.Modelos.Profesor;

public class ProfesorDAO {
    public void guardar(Profesor profesor) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(profesor);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    //READ
    public Profesor buscarPorDni(int dni) {
        Profesor profesor = null;
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT p FROM Profesor p WHERE p.dni = :dniConsultado";
            profesor = em.createQuery(jpql, Profesor.class)
                    .setParameter("dniConsultado", dni)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No se encontro ningun profesor con el DNI: " + dni);
        } catch (Exception ex) {
            System.out.println("Error en la consulta: " + ex.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return profesor;
    }

    //UPDATE
    public void actualizar(Profesor profesor) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(profesor);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // DELETE

    public void eliminar(int dni) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Profesor profesor = em.find(Profesor.class, dni);
            if (profesor != null) {
                em.remove(profesor);
                em.getTransaction().commit();
            } else {
                System.out.println("No se encontró el profesor con DNI: " + dni);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}
