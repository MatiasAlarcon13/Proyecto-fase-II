package laboratorio.modulos;

import jakarta.persistence.EntityManager;
import laboratorio.Modelos.Bobina;

import java.util.List;

public class BobinaDAO {

    // ─── CREATE ───────────────────────────────────────────────────────────────

    public void guardar(Bobina bobina) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(bobina);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al guardar la bobina", e);
        } finally {
            em.close();
        }
    }

    // ─── READ ─────────────────────────────────────────────────────────────────

    public Bobina buscarPorId(int idBobina) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Bobina.class, idBobina);
        } catch (Exception e) {
            System.out.println("Error al buscar bobina con ID " + idBobina + ": " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public List<Bobina> listarTodas() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Bobina b", Bobina.class)
                     .getResultList();
        } catch (Exception e) {
            System.out.println("Error al listar bobinas: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    public List<Bobina> buscarPorMaterial(String material) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT b FROM Bobina b WHERE b.material = :mat", Bobina.class)
                    .setParameter("mat", material)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error al buscar bobinas por material: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    public void actualizar(Bobina bobina) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(bobina);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al actualizar la bobina", e);
        } finally {
            em.close();
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    public void eliminar(int idBobina) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Bobina bobina = em.find(Bobina.class, idBobina);
            if (bobina != null) {
                em.remove(bobina);
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
                System.out.println("No se encontró bobina con ID: " + idBobina);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al eliminar la bobina", e);
        } finally {
            em.close();
        }
    }
}
