package laboratorio;

public class Bobina {
    private int idBobina;
    private String material, color;
    private Double gramos;
    private static int contadorBobina = 1;

    public Bobina() {
    }

    // constructor
    public Bobina(int idBobina, String material, String color, Double gramos) {
        this.idBobina = contadorBobina++;
        this.material = material;
        this.color = color;
        setbobinaGramos(gramos);
    }

    //comprobar si la bobina tiene el filamento necesario (true/false)
    public boolean tieneMaterial(SolicitudImpresion solicitud) {
        return this.gramos >= solicitud.getGramosRequeridos();
    }

    public void descontarMaterial(SolicitudImpresion solicitud) {
        //si no tiene material suficiente cancela
        if (!tieneMaterial(solicitud)) {
            System.out.println("Solicitud rechazada. Material insuficiente.");
            return;
        }
        this.gramos -= solicitud.getGramosRequeridos();
        System.out.println("Gramos descontados. Disponible actualmente: " + this.gramos + "grs");
    }

    public boolean mantenimientoBobina() {
        if (gramos > 20) {
            return true;
        } else {
            System.out.println("Filamento insuficiente.");
        }
        return false;
    }

    // getters and setters
    public int getidBobina() {
        return idBobina;
    }

    public String getmaterialBobina() {
        return material;
    }

    public void setmaterialBobina(String material) {
        this.material = material;
    }

    public String getbobinaColor() {
        return color;
    }

    public void setbobinaColor(String color) {
        this.color = color;
    }

    public Double getbobinaGramos() {
        return gramos;
    }

    //verificacion de entrada de datos valido
    public void setbobinaGramos(Double gramos) {
        if (gramos < 0) {
            System.out.println("Gramos no puede ser negativo.");
            return;
        }
        this.gramos = gramos;
    }
}