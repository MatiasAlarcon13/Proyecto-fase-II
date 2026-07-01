package laboratorio.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "Bobina")
public class Bobina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBobina")
    private int idBobina;

    @Column(name = "material", nullable = false)
    private String material;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "gramos", nullable = false)
    private Double gramos;

    public Bobina() {}

    public Bobina(String material, Double gramos) {
        this.material = material;
        this.color = "Negro";
        setGramos(gramos);
    }


    public boolean tieneMaterial(SolicitudImpresion solicitud) {
        return this.gramos >= solicitud.getGramosRequeridos();
    }

    /**
     * Descuenta material. Retorna false si no hay suficiente.
     */
    public boolean descontarMaterial(SolicitudImpresion solicitud) {
        if (!tieneMaterial(solicitud)) return false;
        this.gramos -= solicitud.getGramosRequeridos();
        return true;
    }

    /**
     * Retorna false si el filamento <= 20g (requiere mantenimiento).
     */
    public boolean mantenimientoBobina() {
        return gramos > 20;
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────

    public int getidBobina()                    { return idBobina; }
    public String getmaterialBobina()           { return material; }
    public void setmaterialBobina(String m)     { this.material = m; }
    public String getbobinaColor()              { return color; }
    public void setbobinaColor(String c)        { this.color = c; }
    public Double getbobinaGramos()             { return gramos; }

    public void setGramos(Double gramos) {
        if (gramos != null && gramos >= 0) this.gramos = gramos;
    }

    // Alias mantenido para compatibilidad con código existente
    public void setbobinaGramos(Double gramos) { setGramos(gramos); }
}