package laboratorio.Controladores;
import java.time.LocalDateTime;
public class Registro {
    private int idRegistro;
    private LocalDateTime fechaHora;
    private String motivo;
    private int dniUsuario;
    private int idGeneral;

    public Registro(int idRegistro, LocalDateTime fechaHora, String motivo, int dniUsuario, int idGeneral){
        this.idRegistro = idRegistro;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.dniUsuario = dniUsuario;
        this.idGeneral = idGeneral;
    }
    //getters y setters

    public int getIdRegistro() {return idRegistro;}
    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }
    public LocalDateTime getFechaHora() {return fechaHora;}
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public void setDniUsuario(int dniUsuario) {
        this.dniUsuario = dniUsuario;
    }
    public void setIdGeneral(int idGeneral) {
        this.idGeneral = idGeneral;
    }
}
