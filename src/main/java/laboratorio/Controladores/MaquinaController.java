package laboratorio.Controladores;

import laboratorio.Modelos.*;
import laboratorio.Persistencia.*;
import java.util.UUID;

public class MaquinaController {
    private final ImpresoraDAO impresoraDAO = new ImpresoraDAO();
    private final CortadoraLaserDAO cortadoraDAO = new CortadoraLaserDAO();

    public void crearImpresora() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        Impresora i = new Impresora();
        i.setIdMaquina(id);
        impresoraDAO.guardar(i);
        System.out.println("Impresora creada con ID: " + id);
    }

    public void crearCortadora() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        CortadoraLaser c = new CortadoraLaser();
        c.setIdMaquina(id);
        cortadoraDAO.guardar(c);
        System.out.println("Cortadora creada con ID: " + id);
    }

    public boolean ponerEnMantenimiento(Impresora impresora) {
        return impresora.ponerEnMantenimiento();
    }

    public boolean liberarMantenimiento(Impresora impresora) {
        return impresora.liberarMantenimiento();
    }

    public estadoMaquina.EstadoMaquina getEstado(Impresora impresora) {
        return impresora.getEstado();
    }
}

