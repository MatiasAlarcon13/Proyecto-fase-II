package laboratorio.Modelos;
import laboratorio.Modelos.SolicitudImpresion;
import jakarta.persistence.*;

@Entity
@Table(name= "Solicitudes_Impresion")
public class SolicitudImpresion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String nombreArchivo, titularSolicitud, rol, correo, modelo;
    private int idImpresion, idImpresora, dni, capas;
    private String fechaSolicitud;
    private double tiempoEstimado, gramosRequeridos;
    private static int contadorImpresion = 1;

    @ManyToOne (fetch = FetchType.LAZY)//carga el usuario solo si lo necesita
    @JoinColumn(name="idUsuario")
    private Usuario usuario;

    public SolicitudImpresion(String nombreArchivo, double tiempoEstimado, double gramosRequeridos, Usuario usuario, ModelosImpresion modelos) {
        this.nombreArchivo = nombreArchivo;
        this.tiempoEstimado = tiempoEstimado;
        this.gramosRequeridos = gramosRequeridos;
        this.titularSolicitud = usuario.getNombre();
        this.rol = usuario.getRol();
        this.dni = usuario.getDni();
        this.correo = usuario.getCorreo();
        this.idImpresion = contadorImpresion++;
        this.fechaSolicitud = java.time.LocalDate.now().toString();
        this.capas = modelos.getTotalCapas();
        this.modelo = modelos.getNombreModelo();
    }

    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}


    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }

    public String getTitularSolicitud() { return titularSolicitud; }
    public void setTitularSolicitud(String titularSolicitud) { this.titularSolicitud = titularSolicitud; }

    public int getIdImpresion() { return idImpresion; }

    public int getIdImpresora() { return idImpresora; }
    public void setIdImpresora(int idImpresora) { this.idImpresora = idImpresora; }

    public int getDni() { return dni; }
    public String getRol() { return rol; }
    public String getCorreo() { return correo; }
    public String getFechaSolicitud() { return fechaSolicitud; }

    public double getGramosRequeridos() { return gramosRequeridos; }
    public void setGramosRequeridos(double gramosRequeridos) { this.gramosRequeridos = gramosRequeridos; }

    public double getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(double tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }

    public int getCapas() {return capas;}

    public String getModelo() {return modelo;}
}
