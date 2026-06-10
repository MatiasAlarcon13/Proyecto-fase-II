package laboratorio.Controladores;

import laboratorio.Modelos.Bobina;
import laboratorio.Modelos.SolicitudImpresion;

public class BobinaController {

    public Bobina crearBobina(int id, String material, String color, double gramos) {
        return new Bobina(id, material, color, gramos);
    }

    public boolean tieneMaterial(Bobina bobina, SolicitudImpresion solicitud) {
        return bobina.tieneMaterial(solicitud);
    }

    public boolean necesitaMantenimiento(Bobina bobina) {
        return !bobina.mantenimientoBobina();
    }

    /**
     * Descuenta material de la bobina.
     * Retorna false si no hay suficiente material.
     */
    public boolean descontarMaterial(Bobina bobina, SolicitudImpresion solicitud) {
        return bobina.descontarMaterial(solicitud);
    }
}
