package laboratorio.Modelos;
import jakarta.persistence.*;

@Entity
@Table(name= "Solicitudes_Impresion")
public class SolicitudImpresion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSolicitud;

    @Column(name="nombre_Archivo")
    private String nombreArchivo;
    @Column(name="modelo")
    private String modelo;
    @Column(name="id_impresora")
    private int idImpresora;
    @Column(name="capas")
    private int capas;
    @Column(name="tiempo_estimado")
    private double tiempoEstimado;
    @Column(name="gramos_requeridos")
    private double gramosRequeridos;
    @Column(name="fecha")
    private String fechaSolicitud;

    @ManyToOne (fetch = FetchType.LAZY)//carga el usuario solo si lo necesita
    @JoinColumn(name="idUsuario")
    private Usuario usuario;

    public SolicitudImpresion() {}

    public SolicitudImpresion(String nombreArchivo, double tiempoEstimado, double gramosRequeridos, Usuario usuario, ModelosImpresion modelos) {
        this.usuario = usuario;
        this.nombreArchivo = nombreArchivo;
        this.tiempoEstimado = tiempoEstimado;
        this.gramosRequeridos = gramosRequeridos;
        this.fechaSolicitud = java.time.LocalDate.now().toString();
        this.capas = modelos.getTotalCapas();
        this.modelo = modelos.getNombreModelo();
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

    public int getIdImpresora() { return idImpresora; }
    public void setIdImpresora(int idImpresora) { this.idImpresora = idImpresora; }

    public String getFechaSolicitud() { return fechaSolicitud; }

    public double getGramosRequeridos() { return gramosRequeridos; }
    public void setGramosRequeridos(double gramosRequeridos) { this.gramosRequeridos = gramosRequeridos; }

    public double getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(double tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }

    public int getCapas() {return capas;}

    public String getModelo() {return modelo;}
}
