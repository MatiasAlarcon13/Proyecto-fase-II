package laboratorio.Modelos;
import laboratorio.Persistencia.PlanchaDAO;
import jakarta.persistence.*;

@Entity
@Table(name = "CortadoraLaser")
@PrimaryKeyJoinColumn(name = "idMaquina")
@DiscriminatorValue("CortadoraLaser")
public class CortadoraLaser extends Maquina {

    @Column(name = "vida_util_tubo_laser")
    private int vidaUtilTuboLaser;

    @Transient
    private SolicitudCorte solicitudActual;

    public CortadoraLaser() {
        super(estadoMaquina.EstadoMaquina.LIBRE, "CortadoraLaser");
        this.vidaUtilTuboLaser = 2500;
    }

    @Override
    public void solicitar(Solicitud solicitud) {
        // Implementar lógica de solicitud si es necesario
    }

    @Override
    public Double consumirRecurso(Solicitud solicitud) {
        if (solicitud instanceof SolicitudCorte) {
            int tiempoCorte = ((SolicitudCorte) solicitud).getTiempoTuboLaser();
            this.vidaUtilTuboLaser -= tiempoCorte;
            if (this.vidaUtilTuboLaser <= 0) {
                this.estado = estadoMaquina.EstadoMaquina.EN_MANTENIMIENTO;
            }
            return (double) tiempoCorte;
        }
        return 0.0;
    }

    public void iniciarCorte(SolicitudCorte solicitud, Plancha plancha) {
        if (this.estado == estadoMaquina.EstadoMaquina.LIBRE && plancha.getCantidadDisponible() >= solicitud.getCantPlanchas()) {
            this.estado = estadoMaquina.EstadoMaquina.IMPRIMIENDO; 
            this.solicitudActual = solicitud;
            
            // Descontar material
            plancha.setCantidadDisponible(plancha.getCantidadDisponible() - solicitud.getCantPlanchas());
            new PlanchaDAO().actualizar(plancha);
            
            consumirRecurso(solicitud);
        }
    }

    public int getVidaUtilTuboLaser() { return vidaUtilTuboLaser; }
    public void setVidaUtilTuboLaser(int vidaUtilTuboLaser) { this.vidaUtilTuboLaser = vidaUtilTuboLaser; }
}
