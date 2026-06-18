package laboratorio.Persistencia;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil { //crea la conexion con la base de datos
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
    public static void close(){
        emf.close();
    }
}
