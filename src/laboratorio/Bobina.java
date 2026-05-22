package laboratorio;

public class Bobina {
    private int idBobina;
    private String material, color;
    private Double gramos;
    private static int contadorId = 1;


    public Bobina(){}

    public Bobina(int idBobina, String material, String color, Double gramos) {
        this.idBobina = contadorId++;
        this.material = material;
        this.color = color;
        this.gramos = gramos;
    }

    public boolean tieneMaterial (SolicitudImpresion solicitud)






    public int getIdBobina() {
        return idBobina;
    }

    public void setIdBobina(int idBobina) {
        this.idBobina = idBobina;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getGramos() {
        return gramos;
    }

    public void setGramos(Double gramos) {
        this.gramos = gramos;
    }

    public int getContadorId() {
        return contadorId;
    }

    public void setContadorId(int contadorId) {
        this.contadorId = contadorId;
    }
}
