package laboratorio.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "Plancha")
public class Plancha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPlancha;

    @Column(name = "tipo_material", nullable = false)
    private String tipoMaterial;

    @Column(name = "cantidad_disponible", nullable = false)
    private int cantidadDisponible;

    public Plancha() {}

    public Plancha(String tipoMaterial, int cantidadDisponible) {
        this.tipoMaterial = tipoMaterial;
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getIdPlancha() { return idPlancha; }
    public String getTipoMaterial() { return tipoMaterial; }
    public void setTipoMaterial(String tipoMaterial) { this.tipoMaterial = tipoMaterial; }
    public int getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }
}
