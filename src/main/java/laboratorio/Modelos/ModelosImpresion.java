package laboratorio.Modelos;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import org.hibernate.annotations.CollectionId;

@Entity
@Table(name="Modelos_Impresion")

public class ModelosImpresion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idModelo")
    private int idModelo;
    @Column(name="nombre")
    private String nombreModelo;
    @Column(name="tiempo_impresion")
    private double tiempoEstimado;
    @Column(name="gramos_requeridos")
    private double gramosRequeridos;
    @Column (name="altura_capa")
    private int alturaCapa;
    @Column(name= "total_Capas")
    private int totalCapas;
    @Column (name="velocidad_impresion")
    private Double velocidadImpresion = 40.0;

    public ModelosImpresion() {}

    public double getTiempoEstimado() { return tiempoEstimado; }
    public double getGramosRequeridos() { return gramosRequeridos; }
    public String getNombreModelo() { return nombreModelo; }
    public int getAlturaCapa() { return alturaCapa; }
    public int getTotalCapas() { return totalCapas; }
    public double getVelocidadImpresion() { return velocidadImpresion; }

    public void setTiempoEstimado(double tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }
    public void setGramosRequeridos(double gramosRequeridos) { this.gramosRequeridos = gramosRequeridos; }
    public void setNombreModelo(String nombreModelo) { this.nombreModelo = nombreModelo; }
    public void setAlturaCapa(int alturaCapa) { this.alturaCapa = alturaCapa; }
    public void setTotalCapas(int totalCapas) { this.totalCapas = totalCapas; }
    public void setVelocidadImpresion(double velocidadImpresion) { this.velocidadImpresion = velocidadImpresion; }

    /**
     * Selecciona y configura el modelo según el nombre.
     * Retorna false si el modelo no existe.
     */
    public boolean seleccionarModelo(String nombreModelo) {
        switch (nombreModelo.trim().toLowerCase()) {
            case "puente": puente(); return true;
            case "casa":   casa();   return true;
            case "pelota": pelota(); return true;
            default: return false;
        }
    }

    private void puente() {
        nombreModelo = "puente";
        alturaCapa = 2;
        totalCapas = 20;
        gramosRequeridos = 80.0;
        tiempoEstimado = (alturaCapa * totalCapas) / velocidadImpresion;
    }

    private void casa() {
        nombreModelo = "casa";
        alturaCapa = 4;
        totalCapas = 40;
        gramosRequeridos = 40.0;
        tiempoEstimado = (alturaCapa * totalCapas) / velocidadImpresion;
    }

    private void pelota() {
        nombreModelo = "pelota";
        alturaCapa = 6;
        totalCapas = 1;
        gramosRequeridos = 30.0;
        tiempoEstimado = (alturaCapa * totalCapas) / velocidadImpresion;
    }

    public int getIdModelo() {
        return idModelo;
    }
    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
    }
}
