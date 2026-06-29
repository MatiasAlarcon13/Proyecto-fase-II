package laboratorio.Controladores;

import laboratorio.Modelos.Impresora;

public class MaquinaController {
    public boolean ponerEnMantenimiento(Impresora impresora) {
        return impresora.ponerEnMantenimiento();
    }

    /**
     * Retorna false si no estaba en mantenimiento.
     */
    public boolean liberarMantenimiento(Impresora impresora) {
        return impresora.liberarMantenimiento();
    }

    public Impresora.EstadoImpresora getEstado(Impresora impresora) {
        return impresora.getEstado();
    }
}


