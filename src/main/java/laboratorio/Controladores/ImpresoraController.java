package laboratorio.Controladores;

import laboratorio.Modelos.*;

public class ImpresoraController {

    public Impresora crearImpresora(String modelo, String marca) {
        return new Impresora();
    }

    /**
     * Inicia impresión.
     * Retorna un código de resultado:
     *   0 = éxito
     *   1 = impresora en mantenimiento
     *   2 = impresora ocupada
     *   3 = bobina requiere mantenimiento
     *   4 = material insuficiente en bobina
     */
    public int iniciarImpresion(Impresora impresora, SolicitudImpresion solicitud, Bobina bobina) {
        if (impresora.estaEnMantenimiento()) return 1;
        if (!impresora.estaDisponible())     return 2;
        if (!bobina.mantenimientoBobina())   return 3;
        if (!bobina.tieneMaterial(solicitud)) return 4;

        impresora.iniciarImpresion(solicitud, bobina);
        return 0;
    }

    /**
     * Finaliza impresión.
     * Retorna false si no hay impresión en curso.
     */
    public boolean finalizarImpresion(Impresora impresora) {
        return impresora.finalizarImpresion();
    }

    /**
     * Retorna false si está imprimiendo.
     */
    public boolean ponerEnMantenimiento(Impresora impresora) {
        return impresora.ponerEnMantenimiento();
    }

    /**
     * Retorna false si no estaba en mantenimiento.
     */
    public boolean liberarMantenimiento(Impresora impresora) {
        return impresora.liberarMantenimiento();
    }

    public estadoMaquina.EstadoMaquina getEstado(Impresora impresora) {
        return impresora.getEstado();
    }
}
