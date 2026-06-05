package laboratorio.Vistas;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        // 1. REGISTRAR LAS FUENTES EXTERNAS
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // Cargar fuente para el Título (MADE Waffle Soft)
        try {
            File archivoWaffle = new File("imagenes/MADE Waffle Soft PERSONAL USE.otf");
            if (archivoWaffle.exists()) {
                Font fuenteWaffle = Font.createFont(Font.TRUETYPE_FONT, archivoWaffle);
                ge.registerFont(fuenteWaffle);
                System.out.println("Fuente Título registrada: " + fuenteWaffle.getName());
            } else {
                System.err.println("No se encontró el archivo de la fuente Waffle.");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar fuente Waffle: " + e.getMessage());
        }

        // Cargar fuente para el Subtítulo (Baliw)
        try {
            File archivoBaliw = new File("imagenes/Baliw.ttf");
            if (archivoBaliw.exists()) {
                Font fuenteBaliw = Font.createFont(Font.TRUETYPE_FONT, archivoBaliw);
                ge.registerFont(fuenteBaliw);
                System.out.println("Fuente Subtítulo registrada: " + fuenteBaliw.getName());
            } else {
                System.err.println("No se encontró el archivo de la fuente Baliw.");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar fuente Baliw: " + e.getMessage());
        }

        // 2. INICIAR FLATLAF
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Error al iniciar FlatLaf");
        }

        // 3. ABRIR LA VENTANA
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Laboratory Maker - Inicio");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            VentanaSeleccion pantallaSeleccion = new VentanaSeleccion();
            frame.setContentPane(pantallaSeleccion.getPanelPrincipal());


            frame.setSize(900, 650); // Tamaño grande predeterminado
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}