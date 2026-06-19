package laboratorio.Controladores;
import laboratorio.Modelos.ModelosImpresion;
import laboratorio.Persistencia.ModelosImpresionDAO;

public class ModelosImpresionController {
    private final ModelosImpresionDAO  modelosDAO = new ModelosImpresionDAO();

    //buscar
    public ModelosImpresion obtenerModelosImpresion(String nombreModelo) {
        if (nombreModelo == null || nombreModelo.trim().isEmpty()){
            return null;
        }
        return modelosDAO.buscarModelos(nombreModelo);
    }
//agregar
     public ModelosImpresion agregarModelosImpresion(ModelosImpresion nuevoModelo) {
         if (nuevoModelo == null) {
             return null;
         }
         if (nuevoModelo.getNombreModelo() == null || nuevoModelo.getNombreModelo().trim().isEmpty()) {
             return null;
         }
         if (nuevoModelo.getGramosRequeridos() <= 0) {
             return null;
         }
         modelosDAO.guardarModelo(nuevoModelo);
         return nuevoModelo;
     }

     // modificar
     public ModelosImpresion modificarModelo(ModelosImpresion modeloModificado){
         if (modeloModificado == null || modeloModificado.getIdModelo() == 0){
             return null;
         }

         modelosDAO.actualizarModelo(modeloModificado);
         return modeloModificado;
     }

    public boolean eliminarModelo(int idModelo){
        if (idModelo <= 0){
            return false;
        }

        modelosDAO.eliminarModelo(idModelo);
        return true;
    }
}


