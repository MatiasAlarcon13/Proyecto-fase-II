package laboratorio.Vistas;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PantallaInicioAlumnos {

    private JPanel PanelAlumno;
    private JPanel contenedor;
    private JTable table1;
    private JPanel PanelDataUser;
    private JPanel PanelImpresion;
    private JPanel infoModelos;
    private JPanel PanelModelos;
    private JLabel headerModelos;
    private JPanel modeloCasa;
    private JPanel modeloPuente;
    private JPanel modeloPelota;
    private JLabel usuarioNombre;
    private JLabel cuotaDisponible;
    private JLabel nombre;
    private JLabel cuota;
    private JLabel imgCasa;
    private JLabel imgPuente;
    private JLabel imgPelota;
    private JLabel tiempoImpresionC;
    private JLabel totalCapasC;
    private JLabel materialRequeridoC;
    private JLabel tiempoImpresionPu;
    private JLabel totalCapasPu;
    private JLabel materialRequeridoPu;
    private JLabel tiempoImpresionPe;
    private JLabel totalCapasPe;
    private JLabel materialRequeridosPe;
    private JLabel tituloCasa;
    private JPanel tituloPuente;
    private JLabel tituloPelota;
    private JButton btnNuevoArchivo;

    public PantallaInicioAlumnos() {
        try {
            File rutaCasa = new File("imagenes/casa.png");
            File rutaPuente = new File("imagenes/puente.png");
            File rutaPelota = new File("imagenes/pelota.png");

            //cargar imagen correspondiente
            if (rutaCasa.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(rutaCasa.getAbsolutePath());
                // Escalamos a 100*100 para que mantenga buena proporción
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imgCasa.setIcon(new ImageIcon(imgEscalada));
                imgCasa.setText(""); // Borra el texto "Button" genérico
            } else {
                System.err.println("No se encontró el archivo en: " + rutaCasa.getAbsolutePath());
            }

            // 2. Cargar 'add usser.png' en el botón de Alumno / Invitado
            if (rutaPuente.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(rutaPuente.getAbsolutePath());
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imgPuente.setIcon(new ImageIcon(imgEscalada));
                imgPuente.setText(""); // Borra el texto genérico
            } else {
                System.err.println("No se encontró el archivo en: " + rutaPuente.getAbsolutePath());
            }

            if (rutaPelota.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(rutaPelota.getAbsolutePath());
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imgPelota.setIcon(new ImageIcon(imgEscalada));
                imgPelota.setText(""); // Borra el texto genérico
            } else {
                System.err.println("No se encontró el archivo en: " + rutaPelota.getAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("Error al cargar los íconos de usuario: " + e.getMessage());
        }

        // Acá vas a poder programar lo que pasa cuando el alumno
        // quiera subir un "Nuevo Archivo" STL para imprimir.
        btnNuevoArchivo.addActionListener(e -> {
            // Ejemplo: Abrir un selector de archivos de la compu
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(PanelAlumno);
        });
    }
    // El puente indispensable para que el JFrame lo pueda mostrar en pantalla
    public JPanel getPanelAlumno () {
        return PanelAlumno;
    }

}

