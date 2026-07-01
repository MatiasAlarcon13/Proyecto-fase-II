package laboratorio.Persistencia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import laboratorio.Modelos.Alumno;

public class AlumnoDAO {
    //CREATE
    public void guardar(Alumno alumno) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin(); // Inicia la transacción
            em.persist(alumno);          // Guarda el objeto
            em.getTransaction().commit(); // Sella la transacción
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Cancela los cambios si algo falló
            }
            System.out.println("Error al guardar el alumno: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close(); //cierra la conexión
            }
        }
    }

    //READ
    public Alumno buscarPorDni(int dni) {
        Alumno alumno = null;
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT a FROM Alumno a WHERE a.dni = :dniConsultado";
            alumno = em.createQuery(jpql, Alumno.class)
                    .setParameter("dniConsultado", dni)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No se encontró ningún alumno con el DNI: " + dni);
        } catch (Exception ex) {
            System.out.println("Error en la consulta: " + ex.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return alumno;
    }

    //UPDATE
    public void actualizar(Alumno alumno) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(alumno);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error al actualizar el alumno: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // DELETE
    public void eliminar(int dni) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Alumno usuario = em.find(Alumno.class, dni);

            // Validación crítica antes de eliminar
            if (usuario != null) {
                em.remove(usuario);
                em.getTransaction().commit();
                System.out.println("Alumno eliminado correctamente.");
            } else {
                System.out.println("No se puede eliminar: No existe ningún alumno con DNI " + dni);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error al eliminar el alumno: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}