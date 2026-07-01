package laboratorio.Modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "Solicitudes_Corte")
@PrimaryKeyJoinColumn(name = "idSolicitud")
@DiscriminatorValue("CORTE")
public class SolicitudCorte extends Solicitud {

    @Column(name = "tipo_plancha")
    private String tipoPlancha;
    @Column(name = "cant_planchas")
    private int cantPlanchas;
    @Column(name = "tiempo_tubo_laser")
    private int tiempoTuboLaser;

    public SolicitudCorte() {
        super();
    }

    public SolicitudCorte(String nombreArchivo, String tipoPlancha, int cantPlanchas,
                           int tiempoTuboLaser, Usuario usuario) {
        super(nombreArchivo, usuario);
        this.tipoPlancha = tipoPlancha;
        this.cantPlanchas = cantPlanchas;
        this.tiempoTuboLaser = tiempoTuboLaser;
    }

    public String nombreCorte() {
        return nombreArchivo;
    }

    public Solicitud procesarCorte(Usuario usuario) {
        if (usuario != null) {
            setUsuario(usuario);
        }
        return procesarSolicitud();
    }

    @Override
    public Solicitud procesarSolicitud() {
        if (estado == EstadoSolicitud.PENDIENTE) {
            estado = EstadoSolicitud.EN_PROCESO;
        }
        return this;
    }

    public String getTipoPlancha() { return tipoPlancha; }
    public void setTipoPlancha(String tipoPlancha) { this.tipoPlancha = tipoPlancha; }

    public int getCantPlanchas() { return cantPlanchas; }
    public void setCantPlanchas(int cantPlanchas) { this.cantPlanchas = cantPlanchas; }

    public int getTiempoTuboLaser() { return tiempoTuboLaser; }
    public void setTiempoTuboLaser(int tiempoTuboLaser) { this.tiempoTuboLaser = tiempoTuboLaser; }
}
