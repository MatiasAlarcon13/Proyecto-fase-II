package laboratorio.Persistencia;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JPAUtil { //crea la conexion con la base de datos
    private static final Logger logger = Logger.getLogger(JPAUtil.class.getName()); //logger que clasfica el error
    private static final EntityManagerFactory emf; // el 'final' declara que nadie puede modificar o destruir el valor almacenado

    static {
        try {
            emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        } catch (Throwable ex) {
            logger.log(Level.SEVERE, "Error critico al inicializar el EntityManagerFactory", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    //constructor privado - no se puede instanciar esta clase por fuera de la misma
    private JPAUtil() {
        throw new IllegalStateException("Clase utilitaria - No se debe instanciar");
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void close(){
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
