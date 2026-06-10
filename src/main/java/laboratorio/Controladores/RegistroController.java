package laboratorio.Controladores;

import laboratorio.Modelos.Bobina;
import laboratorio.Modelos.Impresora;
import laboratorio.Modelos.Registro;
import laboratorio.Modelos.SolicitudImpresion;
import laboratorio.Modelos.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RegistroController {

    private final List<Registro> registros = new ArrayList<>();

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
        return Collections.unmodifiableList(registros);
    }

    public List<Registro> getRegistrosPorUsuario(String dni) {
        List<Registro> resultado = new ArrayList<>();
        for (Registro r : registros) {
            if (r.getDniUsuario().equals(dni)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    public Registro getRegistroPorImpresion(int idImpresion) {
        for (Registro r : registros) {
            if (r.getIdImpresion() == idImpresion) {
                return r;
            }
        }
        return null;
    }

    public List<Registro> getRegistrosPorBobina(int idBobina) {
        List<Registro> resultado = new ArrayList<>();
        for (Registro r : registros) {
            if (r.getIdBobina() == idBobina) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    private Registro crearYGuardar(SolicitudImpresion solicitud,
                                   Impresora impresora,
                                   Bobina bobina,
                                   Usuario usuario,
                                   String motivo) {
        Registro r = Registro.generarRegistro(solicitud, impresora, bobina, usuario, motivo);
        if (r != null) {
            registros.add(r);
        }
        return r; // null indica fallo.
    }
}