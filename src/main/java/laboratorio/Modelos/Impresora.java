package laboratorio.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "Impresora")
public class Impresora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idImpresora")
    private int idImpresora;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoImpresora estado;

    // Relaciones en memoria (no persistidas): bobinaActual y solicitudActual
    // son estado transitorio de operación, no se guardan en BD.
    @Transient
    private Bobina bobinaActual;

    @Transient
    private SolicitudImpresion solicitudActual;


    public enum EstadoImpresora {
        LIBRE, IMPRIMIENDO, EN_MANTENIMIENTO
    }

    public Impresora() {
        this.estado = EstadoImpresora.LIBRE;
    }

    // ─── Constructor de uso normal ────────────────────────────────────────────
    public Impresora(String modelo, String marca) {
        this.modelo = modelo;
        this.marca  = marca;
        this.estado = EstadoImpresora.LIBRE;
    }


    public boolean estaDisponible()      { return estado == EstadoImpresora.LIBRE; }
    public boolean estaEnMantenimiento() { return estado == EstadoImpresora.EN_MANTENIMIENTO; }
    public boolean estaImprimiendo()     { return estado == EstadoImpresora.IMPRIMIENDO; }

    /**
     * Pone en mantenimiento. Retorna false si está imprimiendo.
     */
    public boolean ponerEnMantenimiento() {
        if (estado == EstadoImpresora.IMPRIMIENDO) return false;
        estado = EstadoImpresora.EN_MANTENIMIENTO;
        return true;
    }

    /**
     * Libera del mantenimiento. Retorna false si no estaba en mantenimiento.
     */
    public boolean liberarMantenimiento() {
        if (estado != EstadoImpresora.EN_MANTENIMIENTO) return false;
        estado = EstadoImpresora.LIBRE;
        return true;
    }

    /**
     * Inicia impresión. Retorna false si no puede iniciar.
     */
    public boolean iniciarImpresion(SolicitudImpresion solicitud, Bobina bobina) {
        if (estado != EstadoImpresora.LIBRE)      return false;
        if (!bobina.mantenimientoBobina())         return false;
        if (!bobina.tieneMaterial(solicitud))      return false;

        bobina.descontarMaterial(solicitud);
        solicitud.setIdImpresora(this.idImpresora);
        this.estado          = EstadoImpresora.IMPRIMIENDO;
        this.bobinaActual    = bobina;
        this.solicitudActual = solicitud;
        return true;
    }

    /**
     * Finaliza la impresión en curso. Retorna false si no había impresión activa.
     */
    public boolean finalizarImpresion() {
        if (estado != EstadoImpresora.IMPRIMIENDO) return false;
        this.estado          = EstadoImpresora.LIBRE;
        this.bobinaActual    = null;
        this.solicitudActual = null;
        return true;
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────

    public int getIdImpresora()                     { return idImpresora; }
    public String getModelo()                       { return modelo; }
    public void setModelo(String modelo)            { this.modelo = modelo; }
    public String getMarca()                        { return marca; }
    public void setMarca(String marca)              { this.marca = marca; }
    public EstadoImpresora getEstado()              { return estado; }
    public void setEstado(EstadoImpresora estado)   { this.estado = estado; }
    public Bobina getBobinaActual()                 { return bobinaActual; }
    public SolicitudImpresion getSolicitudActual()  { return solicitudActual; }
}
