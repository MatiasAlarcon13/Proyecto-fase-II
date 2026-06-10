package laboratorio.Modelos;

public class Impresora {
    private String modelo, marca;
    private EstadoImpresora estado;
    private int idImpresora;
    private static int contadorId = 1;
    private Bobina bobinaActual;
    private SolicitudImpresion solicitudActual;

    public enum EstadoImpresora {
        LIBRE, IMPRIMIENDO, EN_MANTENIMIENTO
    }

    public Impresora(String modelo, String marca) {
        this.idImpresora = contadorId++;
        this.modelo = modelo;
        this.marca = marca;
        this.estado = EstadoImpresora.LIBRE;
        this.bobinaActual = null;
        this.solicitudActual = null;
    }

    // --- Lógica pura de estado (sin System.out) ---

    public boolean estaDisponible() {
        return estado == EstadoImpresora.LIBRE;
    }

    public boolean estaEnMantenimiento() {
        return estado == EstadoImpresora.EN_MANTENIMIENTO;
    }

    public boolean estaImprimiendo() {
        return estado == EstadoImpresora.IMPRIMIENDO;
    }

    /**
     * Intenta poner en mantenimiento.
     * Retorna false si está imprimiendo.
     */
    public boolean ponerEnMantenimiento() {
        if (estado == EstadoImpresora.IMPRIMIENDO) return false;
        estado = EstadoImpresora.EN_MANTENIMIENTO;
        return true;
    }

    /**
     * Libera del mantenimiento.
     * Retorna false si no estaba en mantenimiento.
     */
    public boolean liberarMantenimiento() {
        if (estado != EstadoImpresora.EN_MANTENIMIENTO) return false;
        estado = EstadoImpresora.LIBRE;
        return true;
    }

    /**
     * Inicia impresión. Retorna false con motivo si no puede iniciar.
     * El controller valida antes de llamar este método.
     */
    public boolean iniciarImpresion(SolicitudImpresion solicitud, Bobina bobina) {
        if (estado != EstadoImpresora.LIBRE) return false;
        if (!bobina.mantenimientoBobina()) return false;
        if (!bobina.tieneMaterial(solicitud)) return false;

        bobina.descontarMaterial(solicitud);
        solicitud.setIdImpresora(this.idImpresora);
        this.estado = EstadoImpresora.IMPRIMIENDO;
        this.bobinaActual = bobina;
        this.solicitudActual = solicitud;
        return true;
    }

    /**
     * Finaliza la impresión en curso.
     * Retorna false si no hay impresión activa.
     */
    public boolean finalizarImpresion() {
        if (estado != EstadoImpresora.IMPRIMIENDO) return false;
        this.estado = EstadoImpresora.LIBRE;
        this.bobinaActual = null;
        this.solicitudActual = null;
        return true;
    }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public int getIdImpresora() { return idImpresora; }
    public EstadoImpresora getEstado() { return estado; }
    public Bobina getBobinaActual() { return bobinaActual; }
    public SolicitudImpresion getSolicitudActual() { return solicitudActual; }
}
