package laboratorio.Modelos;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "registros")
public class Registro {

    // ─── Id generado por MySQL ────────────────────────────────────────────────

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT en MySQL
    private int idRegistro;

    // ─── Atributos de auditoría (solo valores, sin referencias a objetos) ─────

    @Column(nullable = false)
    private String fechaHora;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private String dniUsuario;

    @Column(nullable = false)
    private int idImpresora;

    @Column(nullable = false)
    private int idImpresion;

    @Column(nullable = false)
    private int idBobina;

    // ─── Constructor vacío requerido por JPA ──────────────────────────────────

    protected Registro() {}

    // ─── Factory method ───────────────────────────────────────────────────────

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Registro generarRegistro(SolicitudImpresion solicitud,
                                           Impresora impresora,
                                           Bobina bobina,
                                           Usuario usuario,
                                           String motivo) {
        if (solicitud == null || impresora == null || bobina == null
                || usuario == null || motivo == null || motivo.isBlank()) {
            return null;
        }

        Registro r = new Registro();
        // idRegistro lo asigna MySQL al persistir — no se toca acá
        r.fechaHora   = LocalDateTime.now().format(FORMATTER);
        r.motivo      = motivo.trim();
        r.dniUsuario  = String.valueOf(usuario.getDni());
        r.idImpresora = impresora.getIdImpresora();
        r.idImpresion = solicitud.getIdImpresion();
        r.idBobina    = bobina.getidBobina();

        return r;
    }

    // ─── Getters (sin setters — registro inmutable) ───────────────────────────

    public int    getIdRegistro()  { return idRegistro;  }
    public String getFechaHora()   { return fechaHora;   }
    public String getMotivo()      { return motivo;      }
    public String getDniUsuario()  { return dniUsuario;  }
    public int    getIdImpresora() { return idImpresora; }
    public int    getIdImpresion() { return idImpresion; }
    public int    getIdBobina()    { return idBobina;    }

    @Override
    public String toString() {
        return String.format(
                "[Registro #%d | %s] DNI: %s | Impresora: %d | Impresión: %d | Bobina: %d | Motivo: %s",
                idRegistro, fechaHora, dniUsuario, idImpresora, idImpresion, idBobina, motivo
        );
    }
}
