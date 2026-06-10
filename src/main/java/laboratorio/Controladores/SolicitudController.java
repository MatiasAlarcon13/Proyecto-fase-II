package laboratorio.Controladores;

import laboratorio.Modelos.ModelosImpresion;
import laboratorio.Modelos.SolicitudImpresion;
import laboratorio.Modelos.Usuario;

public class SolicitudController {

    /**
     * Crea una SolicitudImpresion a partir de un nombre de modelo y un usuario.
     *
     * Retorna null si:
     *   - El modelo no existe (modeloValido = false)
     *   - El usuario no tiene cuota suficiente
     *
     * El resultado incluye el motivo del fallo mediante ResultadoSolicitud.
     */
    public ResultadoSolicitud crearSolicitud(String nombreModelo, Usuario usuario) {
        ModelosImpresion modelo = new ModelosImpresion();
        boolean existe = modelo.seleccionarModelo(nombreModelo);

        if (!existe) {
            return new ResultadoSolicitud(null, "Modelo no encontrado: " + nombreModelo);
        }

        double gramosRequeridos = modelo.getGramosRequeridos();

        if (!usuario.tieneCuotaDisponible(gramosRequeridos)) {
            return new ResultadoSolicitud(null,
                "Cuota insuficiente. Disponible: " + usuario.getCuota() + "g | Requerido: " + gramosRequeridos + "g");
        }

        SolicitudImpresion solicitud = new SolicitudImpresion(
                modelo.getNombreModelo(),
                modelo.getTiempoEstimado(),
                modelo.getGramosRequeridos(),
                usuario,
                modelo
                );

        return new ResultadoSolicitud(solicitud, null);
    }

    // --- Clase interna resultado ---
    public static class ResultadoSolicitud {
        private final SolicitudImpresion solicitud;
        private final String error;

        public ResultadoSolicitud(SolicitudImpresion solicitud, String error) {
            this.solicitud = solicitud;
            this.error = error;
        }

        public boolean exito() { return solicitud != null; }
        public SolicitudImpresion getSolicitud() { return solicitud; }
        public String getError() { return error; }
    }
}
