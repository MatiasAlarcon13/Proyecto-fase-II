package laboratorio.Controladores;

import laboratorio.Modelos.ModelosImpresion;
import laboratorio.Modelos.Solicitud;
import laboratorio.Modelos.SolicitudCorte;
import laboratorio.Modelos.SolicitudImpresion;
import laboratorio.Modelos.Usuario;
import laboratorio.Persistencia.SolicitudCorteDAO;
import laboratorio.Persistencia.SolicitudImpresionDAO;

public class SolicitudController {
    private final ModelosImpresionController modelosController = new ModelosImpresionController();
    private final SolicitudImpresionDAO solicitudImpresionDAO = new SolicitudImpresionDAO();
    private final SolicitudCorteDAO solicitudCorteDAO = new SolicitudCorteDAO();

    /**
     * Crea una SolicitudImpresion a partir de un nombre de modelo y un usuario.
     *
     * Retorna null si:
     *   - El modelo no existe (modeloValido = false)
     *   - El usuario no tiene cuota suficiente
     *
     * El resultado incluye el motivo del fallo mediante ResultadoSolicitud.
     */
    public ResultadoSolicitud<SolicitudImpresion> crearSolicitud(String nombreModelo, Usuario usuario) {
        ModelosImpresion modelo = modelosController.obtenerModelosImpresion(nombreModelo);
        if (modelo == null) {
            return new ResultadoSolicitud<>(null, "Modelo no encontrado: " + nombreModelo);
        }

        double gramosRequeridos = modelo.getGramosRequeridos();

        if (!usuario.tieneCuotaDisponible(gramosRequeridos)) {
            return new ResultadoSolicitud<>(null,
                "Cuota insuficiente. Disponible: " + usuario.getCuota() + "g | Requerido: " + gramosRequeridos + "g");
        }

        // REVISAR: este método valida que haya cuota suficiente, pero no
        // descuenta la cuota del usuario (ej. usuario.descontarCuota(gramos)).
        // Si esa llamada no se hace en otro punto del flujo (al asignar la
        // Impresora física, por ejemplo), el usuario podría seguir creando
        // solicitudes sin que su cuota disponible disminuya nunca.
        // No lo agrego acá sin ver Usuario/Alumno.java para no duplicar el
        // descuento si ya se hace en otro Controller.

        SolicitudImpresion solicitud = new SolicitudImpresion(
                modelo.getNombreModelo(),
                modelo.getTiempoEstimado(),
                modelo.getGramosRequeridos(),
                usuario,
                modelo
                );

        try {
            solicitudImpresionDAO.guardar(solicitud);
        } catch (Exception e) {
            return new ResultadoSolicitud<>(null, "Error al guardar en la base de datos: " + e.getMessage());
        }

        return new ResultadoSolicitud<>(solicitud, null);
    }

    /**
     * Crea una SolicitudCorte a partir de los datos de la plancha y un usuario.
     *
     * A diferencia de la impresión 3D, el corte láser no depende de un
     * catálogo de modelos (ModelosImpresion): los datos (tipo de plancha,
     * cantidad, tiempo estimado de tubo láser) los provee directamente
     * quien solicita el corte. Por eso no hay validación de cuota de
     * gramos aquí — esa lógica es propia de impresión 3D y no aplica a
     * corte láser tal como está definido en el UML actual.
     *
     * Retorna null si los datos del corte son inválidos o falla la persistencia.
     */
    public ResultadoSolicitud<SolicitudCorte> crearSolicitudCorte(String nombreArchivo, String tipoPlancha,
                                                                    int cantPlanchas, int tiempoTuboLaser,
                                                                    Usuario usuario) {
        if (tipoPlancha == null || tipoPlancha.isBlank()) {
            return new ResultadoSolicitud<>(null, "Debe indicar el tipo de plancha.");
        }
        if (cantPlanchas <= 0) {
            return new ResultadoSolicitud<>(null, "La cantidad de planchas debe ser mayor a 0.");
        }
        if (tiempoTuboLaser <= 0) {
            return new ResultadoSolicitud<>(null, "El tiempo estimado de tubo láser debe ser mayor a 0.");
        }

        SolicitudCorte solicitud = new SolicitudCorte(
                nombreArchivo,
                tipoPlancha,
                cantPlanchas,
                tiempoTuboLaser,
                usuario
        );

        try {
            solicitudCorteDAO.guardar(solicitud);
        } catch (Exception e) {
            return new ResultadoSolicitud<>(null, "Error al guardar en la base de datos: " + e.getMessage());
        }

        return new ResultadoSolicitud<>(solicitud, null);
    }

    /**
     * Procesa cualquier Solicitud (impresión o corte) cambiando su estado
     * mediante el polimorfismo de Solicitud.procesarSolicitud(), y persiste
     * el cambio en la tabla correspondiente.
     */
    public boolean procesarSolicitud(Solicitud solicitud) {
        if (solicitud == null) return false;
        solicitud.procesarSolicitud();

        if (solicitud instanceof SolicitudImpresion impresion) {
            solicitudImpresionDAO.actualizar(impresion);
            return true;
        }
        if (solicitud instanceof SolicitudCorte corte) {
            solicitudCorteDAO.actualizar(corte);
            return true;
        }
        return false;
    }

    // --- Clase interna resultado (genérica: sirve para impresión y corte) ---
    public static class ResultadoSolicitud<T extends Solicitud> {
        private final T solicitud;
        private final String error;

        public ResultadoSolicitud(T solicitud, String error) {
            this.solicitud = solicitud;
            this.error = error;
        }

        public boolean exito() { return solicitud != null; }
        public T getSolicitud() { return solicitud; }
        public String getError() { return error; }
    }
}
