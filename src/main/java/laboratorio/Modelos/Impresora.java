package laboratorio.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "Impresora")
@PrimaryKeyJoinColumn(name = "idMaquina")
@DiscriminatorValue("Impresora")
public class Impresora extends Maquina {
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

    @Override
    public Double consumirRecurso() {
        return 0.0;
    }

    // ─── Constructor de uso normal ────────────────────────────────────────────
   /* public Impresora(String modelo, String marca) {
        this.modelo = modelo;
        this.marca  = marca;
        this.estado = EstadoImpresora.LIBRE;
    }*/

    public boolean estaImprimiendo()     { return estado == EstadoImpresora.IMPRIMIENDO; }

    /**
     * Inicia impresión. Retorna false si no puede iniciar.
     */
    public boolean iniciarImpresion(SolicitudImpresion solicitud, Bobina bobina) {
        if (estado != EstadoImpresora.LIBRE)      return false;
        if (!bobina.mantenimientoBobina())         return false;
        if (!bobina.tieneMaterial(solicitud))      return false;

        bobina.descontarMaterial(solicitud);
        solicitud.setIdMaquina(Integer.parseInt(this.idMaquina));
        this.estado          = EstadoImpresora.IMPRIMIENDO;
        this.bobinaActual    = bobina;
        this.solicitudActual = solicitud;
        return true;
    }
    public boolean liberar() {
       if( liberarMantenimiento()) {
           return true;
       }else {
           return false;
       }
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

/*    public int getIdImpresora()                     { return idImpresora; }
    public String getModelo()                       { return modelo; }
    public void setModelo(String modelo)            { this.modelo = modelo; }
    public String getMarca()                        { return marca; }
    public void setMarca(String marca)              { this.marca = marca; }
  */  public EstadoImpresora getEstado()              { return estado; }
    public void setEstado(EstadoImpresora estado)   { this.estado = estado; }
    public Bobina getBobinaActual()                 { return bobinaActual; }
    public SolicitudImpresion getSolicitudActual()  { return solicitudActual; }
}
