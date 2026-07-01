package laboratorio.Modelos;
import jakarta.persistence.*;

@Entity
@Table(name = "maquinas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class  Maquina {
    @Id
    protected String idMaquina;
    protected String marca;
    @Enumerated(EnumType.STRING)
    protected estadoMaquina.EstadoMaquina estado;
    protected String tipoMaquina;

    public Maquina(){}

    public Maquina(String idMaquina, String marca, estadoMaquina.EstadoMaquina estado, String tipoMaquina){
        this.idMaquina = idMaquina;
        this.marca = marca;
        this.estado = estado;
        this.tipoMaquina = tipoMaquina;
    }

    public abstract void solicitar(Solicitud solicitud);

    public abstract Double consumirRecurso(Solicitud solicitud);
    //metodos que antes eran de impresora
    public boolean estaDisponible()      { return estado == estadoMaquina.EstadoMaquina.LIBRE; }
    public boolean estaEnMantenimiento() { return estado == estadoMaquina.EstadoMaquina.EN_MANTENIMIENTO; }
    /**
     * Pone en mantenimiento. Retorna false si está imprimiendo.
     */
    public boolean ponerEnMantenimiento() {
        if (estado == estadoMaquina.EstadoMaquina.IMPRIMIENDO) return false;
        estado = estadoMaquina.EstadoMaquina.EN_MANTENIMIENTO;
        return true;
    }

    /**
     * Libera del mantenimiento. Retorna false si no estaba en mantenimiento.
     */
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

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setTipoMaquina(String tipoMaquina) {this.tipoMaquina = tipoMaquina;}

    public String getIdMaquina() {
        return idMaquina;
    }

    public estadoMaquina.EstadoMaquina getEstado() {
        return estado;
    }

    public String getMarca() {
        return marca;
    }

    public String getTipoMaquina() {
        return tipoMaquina;
    }
}
