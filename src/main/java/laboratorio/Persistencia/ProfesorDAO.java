package laboratorio.Persistencia;
import jakarta.persistence.EntityManager;
import laboratorio.Modelos.Alumno;
import laboratorio.Modelos.Profesor;

public class ProfesorDAO {
    public void guardar(Profesor profesor) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager(); //inicia la transaccion
        em.getTransaction().begin(); //abrimos la bveda
        em.persist(profesor); //guardamos el objeto
        em.getTransaction().commit(); //sellamos la transaccion
        em.close();//cierra la conexion
    }

    //READ
    public laboratorio.Modelos.Alumno buscarPorDni(int dni){
        Alumno profesor = null;
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT a FROM Alumno a WHERE a.dni = :dniConsultado";
            profesor = em.createQuery(jpql, Alumno.class)
                    .setParameter("dniConsultado", dni)
                    .getSingleResult();

        }
        catch(jakarta.persistence.NoResultException e){
            System.out.println("No se encontro ningun alumno con el DNI: " +dni);
        }
        catch(Exception ex){
            System.out.println("Error en la consulta: " + ex.getMessage());
        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return profesor;
    }

    //UPDATE
    public void actualizar(Profesor profesor) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(profesor);
        em.getTransaction().commit();
        em.close();
    }

    // DELETE
    //try-catch
    public void eliminar(int dni) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Alumno usuario = em.find(Alumno.class, dni);
        em.remove(usuario);
        em.getTransaction().commit();
        em.close();
    }
}

