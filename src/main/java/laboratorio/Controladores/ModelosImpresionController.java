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
     public modelo agregarModelosImpresion(ModelosImpresion nuevoModelo) {
         if (nuevoModelo.getNombreModelo() == null || nuevoModelo.getNombreModelo().trim().isEmpty()) {
             return new modelo(false, "El nombre no puede estar vacio.");
         }
         if (nuevoModelo.getGramosRequeridos() <= 0) {
             return new modelo(false, "Insertar los gramos requeridos");
         }
         modelosDAO.guardarModelo(nuevoModelo);
         return new modelo(true, "Modelo agregado exitosamente");
     }

     // modificar
    public modelo modificarModelo(ModelosImpresion modeloModificado){
        if (modeloModificado == null || modeloModificado.getIdModelo()==0){
            return new modelo(false,"El modelo no existe");
        }
        modelosDAO.guardarModelo(modeloModificado);
        return new modelo(true, "Modificacion aplicada correctamente");
    }

    public modelo eliminarModelo(int idModelo){
    if (idModelo <= 0){
        return new modelo(false, "ID no valido");
    }
    modelosDAO.eliminarModelo(idModelo);
    return new modelo(true,"Modelo eliminado correctamente");
    }
}



