package laboratorio.Persistencia;

import jakarta.persistence.EntityManager;
import laboratorio.Modelos.Impresora;
import laboratorio.Modelos.estadoMaquina;

import java.util.List;

public class ImpresoraDAO {

    // ─── CREATE ───────────────────────────────────────────────────────────────

    public void guardar(Impresora impresora) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(impresora);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al guardar la impresora", e);
        } finally {
            em.close();
        }
    }

    // ─── READ ─────────────────────────────────────────────────────────────────

    public Impresora buscarPorId(String idImpresora) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Impresora.class, idImpresora);
        } catch (Exception e) {
            System.out.println("Error al buscar impresora con ID " + idImpresora + ": " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public List<Impresora> listarTodas() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT i FROM Impresora i", Impresora.class)
                     .getResultList();
        } catch (Exception e) {
            System.out.println("Error al listar impresoras: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    public List<Impresora> buscarPorEstado(estadoMaquina.EstadoMaquina estado) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT i FROM Impresora i WHERE i.estado = :estado", Impresora.class)
                    .setParameter("estado", estado)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error al buscar impresoras por estado: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    public void actualizar(Impresora impresora) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(impresora);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al actualizar la impresora", e);
        } finally {
            em.close();
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    public void eliminar(String idImpresora) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Impresora impresora = em.find(Impresora.class, idImpresora);
            if (impresora != null) {
                em.remove(impresora);
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
                System.out.println("No se encontró impresora con ID: " + idImpresora);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al eliminar la impresora", e);
        } finally {
            em.close();
        }
    }
}
