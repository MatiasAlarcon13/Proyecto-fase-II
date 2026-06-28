package laboratorio.Modelos;

import jakarta.persistence.*;

/**
 * Solicitud concreta de impresión 3D. Hereda de Solicitud (estrategia JOINED):
 * los campos comunes (idSolicitud, nombreArchivo, fecha, estado, dni, usuario)
 * viven en la tabla "Solicitud"; los campos propios de impresión viven en
 * "Solicitudes_Impresion", unida por idSolicitud.
 *
 * Respecto del UML:
 *  - Se agrega la relación `impresora: Impresora` (objeto, no solo el id),
 *    tal como lo pide el diagrama. Se conserva `idImpresora` como columna
 *    espejo de la FK, igual criterio que `dni` en Solicitud, para no
 *    depender de tener el objeto Impresora cargado al solo consultar el id.
 *  - Se agrega `velocidadImpresion`, subrayado en el UML (= campo estático,
 *    compartido por todas las instancias de SolicitudImpresion). Un campo
 *    `static` no puede ser columna de una entidad JPA (no varía por fila),
 *    por eso se marca `@Transient`: vive en memoria de la JVM, no en la
 *    tabla. Se expone con getter/setter estáticos, igual que el subrayado
 *    del UML indica.
 */
@Entity
@Table(name = "Solicitudes_Impresion")
@PrimaryKeyJoinColumn(name = "idSolicitud")
@DiscriminatorValue("IMPRESION")
public class SolicitudImpresion extends Solicitud {

    // Campo de clase (UML: subrayado) -> estático, no mapeado por JPA.
    @Transient
    private static Double velocidadImpresion = 0.0;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "id_impresora")
    private int idImpresora;

    // Relación real con Impresora, tal como pide el UML (impresora: Impresora).
    // LAZY + nullable porque la asignación de impresora física puede ocurrir
    // después de crear la solicitud (a cargo del Controller correspondiente).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_impresora", insertable = false, updatable = false)
    private Impresora impresora;

    @Column(name = "capas")
    private int capas;
    @Column(name = "tiempo_estimado")
    private double tiempoEstimado;
    @Column(name = "gramos_requeridos")
    private double gramosRequeridos;

    public SolicitudImpresion() {
        super();
    }

    public SolicitudImpresion(String nombreArchivo, double tiempoEstimado, double gramosRequeridos,
                               Usuario usuario, ModelosImpresion modelos) {
        super(nombreArchivo, usuario);
        this.tiempoEstimado = tiempoEstimado;
        this.gramosRequeridos = gramosRequeridos;
        this.capas = modelos.getTotalCapas();
        this.modelo = modelos.getNombreModelo();
    }

    @Override
    public Solicitud procesarSolicitud() {
        if (estado == EstadoSolicitud.PENDIENTE) {
            estado = EstadoSolicitud.EN_PROCESO;
        }
        return this;
    }

    public int getIdImpresora() { return idImpresora; }
    public void setIdImpresora(int idImpresora) { this.idImpresora = idImpresora; }

    public Impresora getImpresora() { return impresora; }
    public void setImpresora(Impresora impresora) {
        this.impresora = impresora;
        this.idImpresora = (impresora != null) ? impresora.getContadorId() : this.idImpresora;
    }

    public double getGramosRequeridos() { return gramosRequeridos; }
    public void setGramosRequeridos(double gramosRequeridos) { this.gramosRequeridos = gramosRequeridos; }

    public double getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(double tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }

    public int getCapas() { return capas; }

    public String getModelo() { return modelo; }

    public static Double getVelocidadImpresion() { return velocidadImpresion; }
    public static void setVelocidadImpresion(Double velocidadImpresion) {
        SolicitudImpresion.velocidadImpresion = velocidadImpresion;
    }
}
