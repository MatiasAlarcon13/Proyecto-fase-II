package laboratorio.Modelos;

public class Bobina {
    private int idBobina;
    private String material, color;
    private Double gramos;
    private static int contadorBobina = 1;

    public Bobina() {}

    public Bobina(int idBobina, String material, String color, Double gramos) {
        this.idBobina = contadorBobina++;
        this.material = material;
        this.color = color;
        setbobinaGramos(gramos);
    }

    public boolean tieneMaterial(SolicitudImpresion solicitud) {
        return this.gramos >= solicitud.getGramosRequeridos();
    }

    /**
     * Descuenta material. Retorna false si no hay suficiente material.
     */
    public boolean descontarMaterial(SolicitudImpresion solicitud) {
        if (!tieneMaterial(solicitud)) return false;
        this.gramos -= solicitud.getGramosRequeridos();
        return true;
    }

    /**
     * Retorna false si filamento <= 20g (requiere mantenimiento).
     */
    public boolean mantenimientoBobina() {
        return gramos > 20;
    }

    public int getidBobina() { return idBobina; }
    public String getmaterialBobina() { return material; }
    public void setmaterialBobina(String material) { this.material = material; }
    public String getbobinaColor() { return color; }
    public void setbobinaColor(String color) { this.color = color; }
    public Double getbobinaGramos() { return gramos; }

    public void setbobinaGramos(Double gramos) {
        if (gramos < 0) return; // validación: controller/view manejan el mensaje
        this.gramos = gramos;
    }
}
