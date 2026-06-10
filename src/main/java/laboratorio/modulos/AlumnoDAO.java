package laboratorio.modulos;
import jakarta.persistence.EntityManager;
import laboratorio.Controladores.Alumno;

public class AlumnoDAO {
    //CREATE
    public void guardar(Alumno alumno) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager(); //inicia la transaccion
        em.getTransaction().begin(); //abrimos la bveda
        em.persist(alumno); //guardamos el objeto
        em.getTransaction().commit(); //sellamos la transaccion
        em.close();//cierra la conexion
    }

    //READ
    public laboratorio.Controladores.Alumno buscarPorDni(int dni){
        Alumno alumno = null;
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            String jpql = "SELECT a FROM Alumno a WHERE a.dni = :dniConsultado";
            alumno = em.createQuery(jpql, Alumno.class)
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
        return alumno;
    }

    //UPDATE
    public void actualizar(Alumno alumno) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(alumno);
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