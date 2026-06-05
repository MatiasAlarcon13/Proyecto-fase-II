package laboratorio.Controladores;
import java.util.Scanner;
public class ModelosImpresion{
    private  double tiempoEstimado;
    private  double gramosRequeridos;
    private String nombreModelo;
    private  int alturaCapa, totalCapas;
    private  Double velocidadImpresion = 40.0;

    public double getTiempoEstimado() {return tiempoEstimado;}
    public double getGramosRequeridos() {return gramosRequeridos;}
    public String getNombreModelo() {return nombreModelo;}
    public int getAlturaCapa() {return alturaCapa;}
    public int getTotalCapas() {return totalCapas;}
    public double getVelocidadImpreison() {return velocidadImpresion;}

    public void setTiempoEstimado(double tiempoEstimado) {this.tiempoEstimado = tiempoEstimado;}
    public void setGramosRequeridos(double gramosRequeridos) {this.gramosRequeridos = gramosRequeridos;}
    public void setNombreModelo(String nombreModelo) {this.nombreModelo = nombreModelo;}
    public void setAlturaCapa(int alturaCapa) {this.alturaCapa = alturaCapa;}
    public void setTotalCapas(int totalCapas) {this.totalCapas = totalCapas;}
    public void setVelocidadImpresion(double velocidadImpresion) {this.velocidadImpresion = velocidadImpresion;}

    public boolean SeleccionarModelo(Scanner teclado) {
        String modelosImpresion = teclado.nextLine().trim().toLowerCase();
        switch (modelosImpresion) {
            case "puente": puente(); break;
            case "casa": casa(); break;
            case ("pelota"): pelota(); break;
            default:
                System.out.println("Ese modelo no esta cargado actualmente");
                teclado.close();
                return false;
        }

        return true;
    }
    private void puente(){
        nombreModelo = "puente";
        alturaCapa = 2;
        totalCapas = 20;
        gramosRequeridos = 80.0;
        tiempoEstimado = (alturaCapa * totalCapas) / velocidadImpresion;
    }
    private void casa(){
        nombreModelo = "casa";
        alturaCapa = 4;
        totalCapas = 40;
        gramosRequeridos = 40.0;
        tiempoEstimado = (alturaCapa * totalCapas) / velocidadImpresion;
    }
    private void pelota(){
        nombreModelo = "pelota";
        alturaCapa = 6;
        totalCapas = 1;
        gramosRequeridos = 30.0;
        tiempoEstimado = (alturaCapa * totalCapas) / velocidadImpresion;
    }
}