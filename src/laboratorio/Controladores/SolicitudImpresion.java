package laboratorio.Controladores;

import java.util.Scanner;

public class SolicitudImpresion {
    private String nombreArchivo, titularSolicitud, rol, correo;
    private int idImpresion, idImpresora, dni;
    private String fechaSolicitud;
    private double tiempoEstimado, gramosRequeridos;
    private static int contadorImpresion = 1;

    public SolicitudImpresion(String nombreArchivo, double tiempoEstimado, double gramosRequeridos, Usuario usuario) {
        this.nombreArchivo = nombreArchivo;
        this.tiempoEstimado = tiempoEstimado;
        this.gramosRequeridos = gramosRequeridos;
        this.titularSolicitud = usuario.getNombre();
        this.rol = usuario.getRol();
        this.dni = usuario.getDni();
        this.correo = usuario.getCorreo();
        this.idImpresion = contadorImpresion++;
        this.fechaSolicitud = java.time.LocalDate.now().toString();// se asigna cuando la impresora toma la solicitud
    }

    //metodo estatico procesar (debe cumplir con todos los parametros para existir)
    public static SolicitudImpresion procesarImpresion(Usuario usuario, Scanner teclado) {
        System.out.println("-----Solicitud de impresion-----");
        System.out.println("Seleccionar modelo de impresion-> Puente | Casa | Pelota");

        ModelosImpresion modelo = new ModelosImpresion();
        boolean existe = modelo.SeleccionarModelo(teclado);
        if (!existe) {
            return null;
        }
        double gramosRequeridos = modelo.getGramosRequeridos();
        double tiempoEstimado = modelo.getTiempoEstimado();

        // cuota suficiente?
        if (!usuario.tieneCuotaDisponible(gramosRequeridos)){
            System.out.println("Cuota insuficiente | Disponible: "+usuario.getCuota()+ "g. | Requerido: "+gramosRequeridos+"g");
            return null;
        }
        System.out.println("Tiempo estimado: " + tiempoEstimado + " horas.");
        System.out.println("Solicitando impresion...");
        return new SolicitudImpresion(modelo.getNombreModelo(), tiempoEstimado, gramosRequeridos, usuario);
    }



    public String getNombreArchivo() {return nombreArchivo;}
    public void setNombreArchivo(String nombreArchivo) {this.nombreArchivo = nombreArchivo;}

    public String getTitularSolicitud() {return titularSolicitud;}
    public void setTitularSolicitud(String titularSolicitud) {this.titularSolicitud = titularSolicitud;}

    public int getIdImpresion() {return idImpresion;}

    public int getIdImpresora() {return idImpresora;}
    public void setIdImpresora(int idImpresora) {this.idImpresora = idImpresora;}

    public int getDni() {return dni;}

    public String getRol() {return rol;}

    public String getCorreo() {return correo;}

    public String getFechaSolicitud() {return fechaSolicitud;}

    public double getGramosRequeridos() {return gramosRequeridos;}
    public void setGramosRequeridos(double gramosRequeridos) {this.gramosRequeridos = gramosRequeridos;}

    public double getTiempoEstimado() {return tiempoEstimado;}
    public void setTiempoEstimado(double tiempoEstimado) {this.tiempoEstimado = tiempoEstimado; }

}
