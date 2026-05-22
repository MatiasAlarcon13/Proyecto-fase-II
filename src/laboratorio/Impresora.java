package laboratorio;

public class Impresora {
    private String modelo, marca;
    private EstadoImpresora estado;
    private int idImpresora;
    private static int contadorId = 1;
    //asociacion → impresora "conoce" a las otras clases
    private Bobina bobinaActual;
    private SolicitudImpresion solicitudActual;

    public enum EstadoImpresora {
        LIBRE, IMPRIMIENDO, EN_MANTENIMIENTO;
    }

    // Constructor
    public Impresora(String modelo, String marca) {
        this.idImpresora = contadorId++;
        this.modelo = modelo;
        this.marca = marca;
        this.estado = EstadoImpresora.LIBRE;
        this.bobinaActual = null;
        this.solicitudActual = null;
    }

    // metodo para poner en mantenimiento
    public void ponerEnMantenimiento() {
        if (this.estado == EstadoImpresora.IMPRIMIENDO) {
            System.out.println("No se puede poner en mantenimiento: la impresora está en uso.");
            return;
        }
        this.estado = EstadoImpresora.EN_MANTENIMIENTO;
        System.out.println("Impresora " + idImpresora + " puesta en mantenimiento.");
    }

    public void liberarMantenimiento() {
        if (this.estado != EstadoImpresora.EN_MANTENIMIENTO) {
            System.out.println("La impresora no está en mantenimiento.");
            return;
        }
        this.estado = EstadoImpresora.LIBRE;
        System.out.println("Impresora " + idImpresora + " disponible nuevamente.");
    }

    public void imprimir(SolicitudImpresion solicitud, Bobina bobina) {

        //validar estado de la impresora
        if (this.estado == EstadoImpresora.EN_MANTENIMIENTO) {
            System.out.println("La impresora está en mantenimiento, no se puede asignar.");
            return;
        }
        if (this.estado == EstadoImpresora.IMPRIMIENDO) {
            System.out.println("La impresora ya está en uso.");
            return;
        }

        if (!bobina.mantenimientoBobina()) {
            System.out.println("Bobina requiere mantenimiento.");
            return;
        }


        //bobina cumple con el minimo indispensable?
        if (!bobina.tieneMaterial(solicitud)) {
            System.out.println("Material insuficiente.");
            System.out.println("Necesarios: " + solicitud.getGramosRequeridos() + " g");
            System.out.println("Disponibles: " + bobina.getbobinaGramos() + " g");
            return;
        }

        this.estado = EstadoImpresora.IMPRIMIENDO;
        this.bobinaActual = bobina;
        this.solicitudActual = solicitud;

        bobina.descontarMaterial(solicitud);
        solicitud.setIdImpresora(this.idImpresora);

        System.out.println("---- IMPRIMIENDO ----");
        System.out.println("Archivo: " + solicitud.getNombreArchivo());
        System.out.println("Tiempo estimado: " + solicitud.getTiempoEstimado());
        System.out.println("Material utilizado: " + solicitud.getGramosRequeridos() + " g");
        System.out.println("Material restante: " + bobina.getbobinaGramos() + " g");
    }

    // metodo separado para finalizar impresión
    public void finalizarImpresion() {
        if (this.estado != EstadoImpresora.IMPRIMIENDO) {
            System.out.println("No hay impresión en curso.");
            return;
        }
        System.out.println("Impresión finalizada: " + solicitudActual.getNombreArchivo());
        this.estado = EstadoImpresora.LIBRE;
        this.bobinaActual = null;
        this.solicitudActual = null;
    }

    // Getters y Setters
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public int getIdImpresora() { return idImpresora; }

    public EstadoImpresora getEstado() { return estado; }

    public void setEstado(EstadoImpresora estado) {
        if (estado == EstadoImpresora.EN_MANTENIMIENTO) {
            ponerEnMantenimiento();
        } else if (estado == EstadoImpresora.LIBRE) {
            liberarMantenimiento();
        } else {
            System.out.println("Use imprimir() para cambiar a IMPRIMIENDO.");
        }
    }

    public Bobina getBobinaActual() { return bobinaActual; }
    public SolicitudImpresion getSolicitudActual() { return solicitudActual; }

}
