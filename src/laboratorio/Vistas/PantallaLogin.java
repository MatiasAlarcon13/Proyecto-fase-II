package laboratorio.Vistas;

import java.awt.Image;
import java.io.File;
import javax.swing.*;
import java.awt.Font;

public class PantallaLogin {
    private JPanel panelLog;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JLabel nombreuser;
    private JLabel userpass;
    private JLabel imgilustracion;
    private JLabel LoginMensaje;
    private JButton atrasButton;
    private JButton ingresarButton;

    public PantallaLogin(String rolSeleccionado) {
        try {
            // Mapeamos las rutas con los nombres EXACTOS de tus archivos reales
            File rutaimgilustracion = new File("imagenes/logusser.png");

            if (rutaimgilustracion.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(rutaimgilustracion.getAbsolutePath());

                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                imgilustracion.setIcon(new ImageIcon(imgEscalada));
                imgilustracion.setText(""); // Borra el texto "Button" genérico
            } else {
                System.err.println("No se encontró el archivo en: " + rutaimgilustracion.getAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("Error al cargar los íconos de usuario: " + e.getMessage());
        }


        try {
            // 1. Tomamos la fuente por defecto de FlatLaf para mantener la armonía
            Font fuenteBase = nombreuser.getFont();

            // 2. Creamos un estilo más grande (ej: tamaño 18) y en negrita (Font.BOLD)
            Font fuenteCamposText = new Font(fuenteBase.getName(), Font.BOLD, 18);

            // 3. Se lo aplicamos a las dos etiquetas de texto
            nombreuser.setFont(fuenteCamposText);
            userpass.setFont(fuenteCamposText);
            LoginMensaje.setFont(fuenteCamposText);
            atrasButton.setFont(fuenteCamposText);
            ingresarButton.setFont(fuenteCamposText);

            // OPCIONAL: Si querés que el texto que escribe el usuario adentro de las cajas
            // también sea más grande y legible, podés sumarle esto:
            Font fuenteCajasBlancas = new Font(fuenteBase.getName(), Font.PLAIN, 16);
            textField1.setFont(fuenteCajasBlancas);
            passwordField1.setFont(fuenteCajasBlancas);

        } catch (Exception e) {
            System.err.println("Error al agrandar los textos del login: " + e.getMessage());
        }

        atrasButton.addActionListener(e -> {
            // 1. Buscamos el marco (JFrame) actual donde está metido este panel
            // (Reemplazá "panelAddUser" por el nombre de tu Jpanel principal de esta pantalla)
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(panelLog);

            if (frameActual != null) {
                // 2. Instanciamos la pantalla de selección original
                VentanaSeleccion pantallaInicio = new VentanaSeleccion();

                // 3. Le volvemos a poner el PanelPrincipal del inicio a la ventana
                frameActual.setContentPane(pantallaInicio.getPanelPrincipal());

                // 4. Refrescamos la interfaz para que se redibuje al instante
                frameActual.revalidate();
                frameActual.repaint();
            }
        });

        ingresarButton.addActionListener(e -> {
            // 1. Buscamos el marco (JFrame) actual donde está metido este panel
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(panelLog);

            if (frameActual != null) {
                // 2. 2. Instanciamos directamente tu nueva pantalla de Alumn
                PantallaInicioAlumnos pantallaInicioALumno = new PantallaInicioAlumnos();
                // 2. Instanciamos directamente tu nueva pantalla de Alumno
                frameActual.setContentPane(pantallaInicioALumno.getPanelAlumno());

                // 4. Refrescamos la interfaz para que se redibuje al instante
                frameActual.revalidate();
                frameActual.repaint();
            }
        });
    }

    // =============== AGREGA ESTE GETTER ABRAJO DE TODO ===============
    public JPanel getPanelLog() {
        return panelLog;
    }
}
