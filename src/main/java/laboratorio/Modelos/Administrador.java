package laboratorio.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "Administrador")
@DiscriminatorValue("ADMIN") // Este valor se guardará automáticamente en la columna 'Categoria'
public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String nombre, int dni, String correo) {
        super(nombre, dni, correo);
    }

    @Override
    public boolean tieneCuotaDisponible(double gramos) {
        return true;
    }

    @Override
    public double getCuota() {
        return 999;
    }

    @Override
    public String getRol() {
        return "ADMIN";
    }
}