package laboratorio.Modelos;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "maquinas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class  Maquina {

    @Id
    @Column(name = "idMaquina")
    protected String idMaquina;

    @Enumerated(EnumType.STRING)
    protected estadoMaquina.EstadoMaquina estado;
    
    @Column(name = "tipoMaquina")
    protected String tipoMaquina;

    public Maquina(){}

    public Maquina(estadoMaquina.EstadoMaquina estado, String tipoMaquina){
        this.estado = estado;
        this.tipoMaquina = tipoMaquina;
    }

    public abstract void solicitar(Solicitud solicitud);

    public abstract Double consumirRecurso(Solicitud solicitud);

    public boolean estaDisponible()      { return estado == estadoMaquina.EstadoMaquina.LIBRE; }
    public boolean estaEnMantenimiento() { return estado == estadoMaquina.EstadoMaquina.EN_MANTENIMIENTO; }
    
    public boolean ponerEnMantenimiento() {
        if (estado == estadoMaquina.EstadoMaquina.IMPRIMIENDO) return false;
        estado = estadoMaquina.EstadoMaquina.EN_MANTENIMIENTO;
        return true;
    }

    public boolean liberarMantenimiento() {
        if (estado != estadoMaquina.EstadoMaquina.EN_MANTENIMIENTO) return false;
        estado = estadoMaquina.EstadoMaquina.LIBRE;
        return true;
    }

    public void setIdMaquina(String idMaquina) {
        this.idMaquina = idMaquina;
    }

    public void setEstado(estadoMaquina.EstadoMaquina estado) {
        this.estado =  estado;
    }

    public void setTipoMaquina(String tipoMaquina) {this.tipoMaquina = tipoMaquina;}

    public String getIdMaquina() {
        return idMaquina;
    }

    public estadoMaquina.EstadoMaquina getEstado() {
        return estado;
    }

    public String getTipoMaquina() {
        return tipoMaquina;
    }
}
