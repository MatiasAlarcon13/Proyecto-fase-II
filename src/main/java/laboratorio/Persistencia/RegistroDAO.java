package laboratorio.Persistencia;

import jakarta.persistence.EntityManager;
import laboratorio.Modelos.Registro;

import java.util.List;

public class RegistroDAO {

    // ─── CREATE ───────────────────────────────────────────────────────────────

    public void guardar(Registro registro) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(registro);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al guardar el registro", e);
        } finally {
            em.close();
        }
    }

    // ─── READ ─────────────────────────────────────────────────────────────────

    public Registro buscarPorId(int idRegistro) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Registro.class, idRegistro);
        } catch (Exception e) {
            System.out.println("Error al buscar registro con ID " + idRegistro + ": " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public List<Registro> listarTodos() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Registro r ORDER BY r.idRegistro DESC", Registro.class)
                     .getResultList();
        } catch (Exception e) {
            System.out.println("Error al listar registros: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    public List<Registro> buscarPorUsuario(String dniUsuario) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT r FROM Registro r WHERE r.dniUsuario = :dni ORDER BY r.idRegistro DESC",
                    Registro.class)
                    .setParameter("dni", dniUsuario)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error al buscar registros por usuario: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    public List<Registro> buscarPorBobina(int idBobina) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT r FROM Registro r WHERE r.idBobina = :idBobina ORDER BY r.idRegistro DESC",
                    Registro.class)
                    .setParameter("idBobina", idBobina)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error al buscar registros por bobina: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }

    public Registro buscarPorImpresion(int idImpresion) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT r FROM Registro r WHERE r.idImpresion = :idImpresion",
                    Registro.class)
                    .setParameter("idImpresion", idImpresion)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Error al buscar registro por impresión: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    // Los registros son inmutables (no tienen UPDATE por diseño).

    public void eliminar(int idRegistro) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Registro registro = em.find(Registro.class, idRegistro);
            if (registro != null) {
                em.remove(registro);
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
                System.out.println("No se encontró registro con ID: " + idRegistro);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al eliminar el registro", e);
        } finally {
            em.close();
        }
    }
}
