package laboratorio.Vistas;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        // Inicia flatlaf
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Error al iniciar FlatLaf");
        }

        // Registra las fuentes externas
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // Titulo: MADE Waffle Soft
        try {
            java.io.InputStream streamWaffle = Main.class.getResourceAsStream("/fuentes/MADE Waffle Soft PERSONAL USE.otf");
            if (streamWaffle != null) {
                Font fuenteWaffle = Font.createFont(Font.TRUETYPE_FONT, streamWaffle);
                ge.registerFont(fuenteWaffle);
                System.out.println("Fuente Título registrada con éxito: " + fuenteWaffle.getName());
            } else {
                System.err.println("No se encontró el recurso: /fuentes/MADE Waffle Soft PERSONAL USE.otf");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar fuente Waffle: " + e.getMessage());
        }

        // Subtítulo: Baliw
        try {
            java.io.InputStream streamBaliw = Main.class.getResourceAsStream("/fuentes/Baliw.ttf");
            if (streamBaliw != null) {
                Font fuenteBaliw = Font.createFont(Font.TRUETYPE_FONT, streamBaliw);
                ge.registerFont(fuenteBaliw);
                System.out.println("Fuente Subtítulo registrada con éxito: " + fuenteBaliw.getName());
            } else {
                System.err.println("No se encontró el recurso: /fuentes/Baliw.ttf");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar fuente Baliw: " + e.getMessage());
        }

        // Abre la app
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Laboratory Maker - Inicio");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Instanciamos la primera vista (VentanaSeleccion)
            VentanaSeleccion pantallaSeleccion = new VentanaSeleccion();
            frame.setContentPane(pantallaSeleccion.getPanelPrincipal());

            // Configuraciones de tamaño y centrado
            frame.setSize(900, 650);
            frame.setLocationRelativeTo(null); // La centra clavada en medio del monitor
            frame.setVisible(true);
        });
    }
}