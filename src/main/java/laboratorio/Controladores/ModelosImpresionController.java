package laboratorio.Controladores;

import laboratorio.Modelos.ModelosImpresion;
import laboratorio.Persistencia.ModelosImpresionDAO;
import java.util.List;

public class ModelosImpresionController {

    private final ModelosImpresionDAO modelosDAO = new ModelosImpresionDAO();

    public List<ModelosImpresion> listarTodos() {
        return modelosDAO.buscarTodosModelos();
    }


    public ModelosImpresion guardarModelo(ModelosImpresion modelo) {
        try {
            if (modelo.getIdModelo() == 0) {
                modelosDAO.guardarModelo(modelo);
            } else {
                modelosDAO.actualizarModelo(modelo);
            }
            return modelo;
        } catch (Exception e) {
            return null; // Retorno defensivo por si falla la persistencia
        }
    }

    public boolean eliminarModelo(int id) {
        try {
            modelosDAO.eliminarModelo(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ModelosImpresion obtenerModelosImpresion(String nombreModelo) {
        try {
            return modelosDAO.buscarModelos(nombreModelo);
        } catch (Exception e) {
            return null; // Retorno defensivo por si no existe o falla la BD
        }
    }
}