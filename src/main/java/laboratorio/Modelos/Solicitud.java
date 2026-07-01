package laboratorio.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "Solicitud")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipoSolicitud", discriminatorType = DiscriminatorType.STRING)
public abstract class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSolicitud")
    protected int idSolicitud;

    @Column(name="id_maquina")
    protected int idMaquina;

    @Column(name = "nombre_Archivo")
    protected String nombreArchivo;

    @Column(name = "fecha")
    protected String fechaSolicitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    protected EstadoSolicitud estado;

    @Column(name = "dni")
    protected int dni;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario")
    protected Usuario usuario;

    public enum EstadoSolicitud {
        PENDIENTE, EN_PROCESO, FINALIZADA, CANCELADA
    }

    protected Solicitud() {}

    protected Solicitud(String nombreArchivo, Usuario usuario) {
        this.nombreArchivo = nombreArchivo;
        this.usuario = usuario;
        this.dni = (usuario != null) ? usuario.getDni() : 0;
        this.fechaSolicitud = java.time.LocalDate.now().toString();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    /**
     * Procesa la solicitud según el tipo concreto (impresión o corte).
     * Cada subclase define qué significa "procesar": iniciar impresión,
     * iniciar corte, etc.
     */
    public abstract Solicitud procesarSolicitud();

    public int getIdSolicitud() { return idSolicitud; }

    public int getIdMaquina(){return idMaquina;}
    public int setIdMaquina(int idMaquina) {this.idMaquina = idMaquina;
        return idMaquina;
    }

    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }

    public String getFechaSolicitud() { return fechaSolicitud; }

    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }

    public int getDni() { return dni; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.dni = (usuario != null) ? usuario.getDni() : this.dni;
    }

    public String getTitularSolicitud() {
        return (usuario != null) ? usuario.getNombre() : "Sin asignar";
    }

    public String getRol() {
        return (usuario != null) ? usuario.getRol() : "Sin asignar";
    }

    public String getCorreo() {
        return (usuario != null) ? usuario.getCorreo() : "Sin asignar";
    }
}
