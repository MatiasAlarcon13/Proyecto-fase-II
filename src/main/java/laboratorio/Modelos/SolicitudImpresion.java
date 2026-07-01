package laboratorio.Modelos;
import jakarta.persistence.*;

@Entity
@Table(name= "Solicitudes_Impresion")
@DiscriminatorValue("IMPRESION")
public class SolicitudImpresion extends Solicitud {
    @Column(name="modelo")
    private String modelo;
    @Column(name="capas")
    private int capas;
    @Column(name="tiempo_estimado")
    private double tiempoEstimado;
    @Column(name="gramos_requeridos")
    private double gramosRequeridos;

    public SolicitudImpresion() {}

    public SolicitudImpresion(String nombreArchivo, double tiempoEstimado, double gramosRequeridos, Usuario usuario, ModelosImpresion modelos) {
       super(nombreArchivo, usuario);
        this.tiempoEstimado = tiempoEstimado;
        this.gramosRequeridos = gramosRequeridos;
        this.fechaSolicitud = java.time.LocalDate.now().toString();
        this.capas = modelos.getTotalCapas();
        this.modelo = modelos.getNombreModelo();
    }

    @Override
    public Solicitud procesarSolicitud() {
        this.estado = EstadoSolicitud.FINALIZADA;
        System.out.println("La solicitud de impresión para el modelo " + this.modelo + " ha sido procesada.");
        return this;
    }

    public int getIdSolicitud() { return idSolicitud; }

    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public String getTitularSolicitud(){
        return (usuario != null) ? usuario.getNombre() : "Sin asignar";
    }

    public int getDni(){
        return (usuario != null) ? usuario.getDni() : 0;
    }

    public String getRol() {
        return (usuario != null) ? usuario.getRol() : "Sin asignar";
    }

    public String getCorreo() {
        return (usuario != null) ? usuario.getCorreo() : "Sin asignar";
    }

    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }

    public String getFechaSolicitud() { return fechaSolicitud; }

    public double getGramosRequeridos() { return gramosRequeridos; }
    public void setGramosRequeridos(double gramosRequeridos) { this.gramosRequeridos = gramosRequeridos; }

    public double getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(double tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }

    public int getCapas() {return capas;}

    public String getModelo() {return modelo;}
}
