package laboratorio.Controladores;


import laboratorio.Modelos.*;
import laboratorio.Persistencia.*;

import java.util.List;

public class SolicitudController {
    private final ModelosImpresionController modelosController = new ModelosImpresionController();
    private final SolicitudImpresionDAO solicitudImpresionDAO = new SolicitudImpresionDAO();
    private final SolicitudCorteDAO solicitudCorteDAO = new SolicitudCorteDAO();
    private final ImpresoraDAO impresoraDAO = new ImpresoraDAO();
    private final PlanchaDAO planchaDAO = new PlanchaDAO();
    private final RegistroController registroController = new RegistroController();

    /**
     * Aprobar solicitud de impresión y asignar impresora.
     */
    public ResultadoSolicitud<SolicitudImpresion> aprobarSolicitud(SolicitudImpresion solicitud, Bobina bobina) {
        if (solicitud.getEstado() != Solicitud.EstadoSolicitud.PENDIENTE) {
            return new ResultadoSolicitud<>(null, "La solicitud no está en estado PENDIENTE.");
        }

        List<Impresora> impresorasLibres = impresoraDAO.buscarPorEstado(estadoMaquina.EstadoMaquina.LIBRE);
        if (impresorasLibres.isEmpty()) {
            return new ResultadoSolicitud<>(null, "No hay impresoras disponibles.");
        }

        Impresora impresora = impresorasLibres.get(0);
        ImpresoraController impresoraController = new ImpresoraController();
        int resultadoIniciacion = impresoraController.iniciarImpresion(impresora, solicitud, bobina);

        if (resultadoIniciacion == 0) {
            solicitud.setEstado(Solicitud.EstadoSolicitud.EN_PROCESO);
            solicitudImpresionDAO.actualizar(solicitud);
            impresoraDAO.actualizar(impresora);
            
            registroController.registrarImpresion(solicitud, impresora, bobina, solicitud.getUsuario());
            
            return new ResultadoSolicitud<>(solicitud, null);
        } else {
            solicitud.setEstado(Solicitud.EstadoSolicitud.CANCELADA);
            solicitudImpresionDAO.actualizar(solicitud);
            
            String motivoError = "Error al iniciar impresión (Código: " + resultadoIniciacion + ")";
            registroController.registrarRechazo(solicitud, impresora, bobina, solicitud.getUsuario(), motivoError);
            
            return new ResultadoSolicitud<>(null, motivoError);
        }
    }

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

        // Crear solicitud
        SolicitudImpresion solicitud = new SolicitudImpresion(
                modelo.getNombreModelo(),
                modelo.gettiempoEstimado(),
                modelo.getGramosRequeridos(),
                usuario,
                modelo
        );

        try {
            // Descontar cuota si el usuario es Alumno
            if (usuario instanceof Alumno) {
                ((Alumno) usuario).descontarCuota(solicitud);
            }
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
                                                                    int cantPlanchas, Usuario usuario) {
        if (tipoPlancha == null || tipoPlancha.isBlank()) {
            return new ResultadoSolicitud<>(null, "Debe indicar el tipo de plancha.");
        }
        if (cantPlanchas <= 0) {
            return new ResultadoSolicitud<>(null, "La cantidad de planchas debe ser mayor a 0.");
        }

        Plancha plancha = planchaDAO.buscarPorTipo(tipoPlancha);
        if (plancha == null || plancha.getCantidadDisponible() < cantPlanchas) {
            return new ResultadoSolicitud<>(null, "Stock insuficiente de material: " + tipoPlancha);
        }

        int tiempoTuboLaser = cantPlanchas * 4; // Lógica fija: 4 minutos por plancha

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

    public List<Solicitud> listarTodasLasSolicitudesPendientes() {
        List<Solicitud> todas = new java.util.ArrayList<>();
        todas.addAll(solicitudImpresionDAO.listarPendientes());
        todas.addAll(solicitudCorteDAO.listarPendientes()); // Necesitaré agregar listarPendientes en SolicitudCorteDAO
        return todas;
    }

    public List<SolicitudImpresion> listarSolicitudesAprobadas() {
        // Asumiendo que Aprobada es EN_PROCESO o similar según la lógica de negocio
        return solicitudImpresionDAO.listarPendientes().stream() // O crear método en DAO para listar EN_PROCESO
                .filter(s -> s.getEstado() == Solicitud.EstadoSolicitud.EN_PROCESO)
                .toList();
    }

    public List<SolicitudImpresion> listarSolicitudesPorAlumno(int dni) {
        // Necesitaremos implementar esto en el DAO o filtrar en memoria
        return solicitudImpresionDAO.listarPendientes().stream() // Simplificación: filtrar en memoria
                .filter(s -> s.getDni() == dni)
                .toList();
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
