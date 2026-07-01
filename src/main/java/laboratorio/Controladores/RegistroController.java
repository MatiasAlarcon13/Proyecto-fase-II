package laboratorio.Controladores;

import laboratorio.Modelos.Bobina;
import laboratorio.Modelos.Impresora;
import laboratorio.Modelos.Registro;
import laboratorio.Modelos.SolicitudImpresion;
import laboratorio.Modelos.Usuario;
import laboratorio.Persistencia.RegistroDAO;

import java.util.Collections;
import java.util.List;


public class RegistroController {

    private final RegistroDAO registroDAO = new RegistroDAO();

    // ─── Creación ─────────────────────────────────────────────────────────────

    public Registro registrarImpresion(SolicitudImpresion solicitud,
                                       Impresora impresora,
                                       Bobina bobina,
                                       Usuario usuario) {
        return crearYGuardar(solicitud, impresora, bobina, usuario,
                "Impresión finalizada correctamente");
    }

    public Registro registrarRechazo(SolicitudImpresion solicitud,
                                     Impresora impresora,
                                     Bobina bobina,
                                     Usuario usuario,
                                     String motivo) {
        return crearYGuardar(solicitud, impresora, bobina, usuario, motivo);
    }

    // ─── Consultas ────────────────────────────────────────────────────────────

    public List<Registro> getRegistros() {
        return registroDAO.listarTodos();
    }

    public List<Registro> getRegistrosPorUsuario(String dni) {
        return registroDAO.buscarPorUsuario(dni);
    }

    public Registro getRegistroPorImpresion(int idImpresion) {
        return registroDAO.buscarPorImpresion(idImpresion);
    }

    public List<Registro> getRegistrosPorBobina(int idBobina) {
        return registroDAO.buscarPorBobina(idBobina);
    }

    private Registro crearYGuardar(SolicitudImpresion solicitud,
                                   Impresora impresora,
                                   Bobina bobina,
                                   Usuario usuario,
                                   String motivo) {
        Registro r = Registro.generarRegistro(solicitud, impresora, bobina, usuario, motivo);
        if (r != null) {
            registroDAO.guardar(r);
        }
        return r; // null indica fallo.
    }
}