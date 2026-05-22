package laboratorio;
import java.util.Scanner;

public class SolicitudImpresion {
    private String nombreArchivo, titularSolicitud;
    private int idImpresion, idImpresora, dni;
    private String rol, correo;
    private String  fechaSolicitud;
    private Double gramosRequeridos;
    private Double tiempoEstimado = 0.0;

    private static Double velocidadImpresion = 40.0;
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
    public static SolicitudImpresion procesarImpresion(Usuario usuario) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("-----Solicitud de impresion-----");
        System.out.println("Seleccionar modelo de impresion-> Puente | Casa | Pelota");
        String archivoNombre = teclado.nextLine().trim().toLowerCase();//trim elimina espacios para que no rompa el switch

        double gramosRequeridos;
        double tiempoEstimado;
        int alturaCapa, totalCapas;

        switch(archivoNombre) {
            case ("puente"):
                alturaCapa = 2;
                totalCapas = 20;
                gramosRequeridos = 80.0;
                tiempoEstimado = (alturaCapa * totalCapas)/velocidadImpresion;
                break;
            case ("casa"):
                alturaCapa = 4;
                totalCapas = 40;
                gramosRequeridos = 40.0;
                tiempoEstimado = (alturaCapa * totalCapas)/velocidadImpresion;
                break;
            case ("pelota"):
                alturaCapa = 6;
                totalCapas = 1;
                gramosRequeridos = 30.0;
                tiempoEstimado = (alturaCapa * totalCapas)/velocidadImpresion;
                break;
            default: System.out.println("Ese modelo no esta cargado actualmente");
                teclado.close();
                return null;
        }

        usuario.asignarCuota();
        // cuota suficiente?
        if (!usuario.tieneCuotaDisponible(gramosRequeridos)){
            System.out.println("Cuota insuficiente | Disponible: "+usuario.getCuota()+ "g. | Requerido: "+gramosRequeridos+"g");
            System.out.println( );
            teclado. close();
            return null;
        }
        System.out.println("Tiempo estimado: " + tiempoEstimado + " horas.");
        System.out.println("Solicitando impresion...");
        return new SolicitudImpresion(archivoNombre, tiempoEstimado, gramosRequeridos, usuario);
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

    public static double getVelocidadImpresion() {return velocidadImpresion;}
    public static void setVelocidadImpresion(double velocidadImpresion) {SolicitudImpresion.velocidadImpresion = velocidadImpresion;}
}
