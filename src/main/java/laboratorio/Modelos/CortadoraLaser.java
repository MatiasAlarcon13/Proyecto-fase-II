package laboratorio.Modelos;
import jakarta.persistence.*;
@Entity
@Table(name = "CortadoraLaser")
@PrimaryKeyJoinColumn(name = "idMaquina")
public class CortadoraLaser extends Maquina{
    @Transient
    private int vidaTuboLaser;
    @Transient
    private SolicitudCorte solicitudActual;

    public CortadoraLaser() {
        this.estado = estadoMaquina.EstadoMaquina.LIBRE;
    }

    @Override
    public Double consumirRecurso(Solicitud solicitud) {
        SolicitudCorte solCorte = (SolicitudCorte) solicitud;
        this.vidaTuboLaser -= solCorte.getTiempoTuboLaser();
        return (double) solCorte.getTiempoTuboLaser();
    }
    @Override
    public void solicitar(Solicitud solicitud) {

    }

    public boolean estaCortando() { return estado == estadoMaquina.EstadoMaquina.CORTANDO; }

    public boolean iniciarCorte(SolicitudCorte solicitud, Plancha plancha) {
        if (this.estado != estadoMaquina.EstadoMaquina.LIBRE) return false;
        if (!plancha.mantenimientoPlancha()) return false;
        if (!plancha.(solicitud)) return false;

        this.PlanchaActual = plancha;
        this.solicitudActual = solicitud;
        this.estado = estadoMaquina.EstadoMaquina.CORTANDO;
        this.consumirRecurso(solicitud);
        solicitud.setIdMaquina(Integer.parseInt(this.idMaquina));
        return true;
    }
    public boolean liberar() {
        return liberarMantenimiento();
    }

    public boolean finalizarCorte() {
        if (estado != estadoMaquina.EstadoMaquina.CORTANDO) return false;
        this.estado = estadoMaquina.EstadoMaquina.LIBRE;
        this.PlanchaActual = null;
        this.solicitudActual = null;
        return true;
    }

    public int getVidaTuboLaser() {return vidaTuboLaser;}
    public void setVidaTuboLaser(int vidaTuboLaser) {this.vidaTuboLaser = vidaTuboLaser;}

    public Plancha getPlanchaActual()                 { return PlanchaActual; }
    public SolicitudCorte getSolicitudActual()  { return solicitudActual; }
}
