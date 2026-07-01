package laboratorio.Modelos;
import laboratorio.Modelos.Maquina;
import jakarta.persistence.*;

@Entity
@Table(name = "Impresora")
@PrimaryKeyJoinColumn(name = "idMaquina")
public class Impresora extends Maquina {
    // Relaciones en memoria (no persistidas): bobinaActual y solicitudActual
    // son estado transitorio de operación, no se guardan en BD.
    @Transient
    private Bobina bobinaActual;

    @Transient
    private SolicitudImpresion solicitudActual;

    public Impresora() {
        super(estadoMaquina.EstadoMaquina.LIBRE, "Impresora");
    }

    @Override
    public Double consumirRecurso(Solicitud solicitud) {
        if (!(solicitud instanceof SolicitudImpresion)) {
            return 0.0;
        }
        SolicitudImpresion solImpresion = (SolicitudImpresion) solicitud;

        if (this.bobinaActual != null) {
            this.bobinaActual.descontarMaterial(solImpresion);
        }
        return solImpresion.getGramosRequeridos();
    }
    @Override
    public void solicitar(Solicitud solicitud) {

    }

    /**
     * Inicia impresión. Retorna false si no puede iniciar.
     */
    public boolean iniciarImpresion(SolicitudImpresion solicitud, Bobina bobina) {
        if (this.estado != estadoMaquina.EstadoMaquina.LIBRE) return false;
        if (!bobina.mantenimientoBobina()) return false;
        if (!bobina.tieneMaterial(solicitud)) return false;

        this.bobinaActual = bobina;
        this.solicitudActual = solicitud;
        this.estado = estadoMaquina.EstadoMaquina.IMPRIMIENDO;
        this.consumirRecurso(solicitud);
        solicitud.setIdMaquina(this.idMaquina);
        return true;
    }
    
    public String getIdImpresora() {
        return getIdMaquina();
    }
    public boolean liberar() {
        return liberarMantenimiento();
    }

    /**
     * Finaliza la impresión en curso. Retorna false si no había impresión activa.
     */
    public boolean finalizarImpresion() {
        if (estado != estadoMaquina.EstadoMaquina.IMPRIMIENDO) return false;
        this.estado = estadoMaquina.EstadoMaquina.LIBRE;
        this.bobinaActual = null;
        this.solicitudActual = null;
        return true;
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────

    public Bobina getBobinaActual()                 { return bobinaActual; }
    public SolicitudImpresion getSolicitudActual()  { return solicitudActual; }
}
