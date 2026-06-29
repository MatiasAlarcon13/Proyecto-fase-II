package laboratorio.Modelos;

public abstract class  Maquina {
    protected String idMaquina;
    protected String marca;
    protected Enum estado;
    protected String tipoMaquina;

    public Maquina(){}

    public Maquina(String idMaquina, String marca, Enum estado, String tipoMaquina){
        this.idMaquina = idMaquina;
        this.marca = marca;
        this.estado = estado;
        this.tipoMaquina = tipoMaquina;
    }

    //public abstract void solicitar(Solicitud solicitud);

    public abstract Double consumirRecurso();
    //metodos que antes eran de impresora
    public boolean estaDisponible()      { return estado == Impresora.EstadoImpresora.LIBRE; }
    public boolean estaEnMantenimiento() { return estado == Impresora.EstadoImpresora.EN_MANTENIMIENTO; }
    /**
     * Pone en mantenimiento. Retorna false si está imprimiendo.
     */
    public boolean ponerEnMantenimiento() {
        if (estado == Impresora.EstadoImpresora.IMPRIMIENDO) return false;
        estado = Impresora.EstadoImpresora.EN_MANTENIMIENTO;
        return true;
    }

    /**
     * Libera del mantenimiento. Retorna false si no estaba en mantenimiento.
     */
    public boolean liberarMantenimiento() {
        if (estado != Impresora.EstadoImpresora.EN_MANTENIMIENTO) return false;
        estado = Impresora.EstadoImpresora.LIBRE;
        return true;
    }

    public void setIdMaquina(String idMaquina) {
        this.idMaquina = idMaquina;
    }

    public void setEstado(Enum estado) {
        this.estado = estado;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setTipoMaquina(String tipoMaquina) {this.tipoMaquina = tipoMaquina;}

    public String getIdMaquina() {
        return idMaquina;
    }

    public Enum getEstado() {
        return estado;
    }

    public String getMarca() {
        return marca;
    }

    public String getTipoMaquina() {
        return tipoMaquina;
    }
}
