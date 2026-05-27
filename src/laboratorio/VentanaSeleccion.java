package laboratorio;
import java.awt.Image;    // <-- Para solucionar el error de 'Image'
import java.io.File;      // <-- Para solucionar el error de 'File'
import javax.swing.*;
import java.awt.Font;

public class VentanaSeleccion {
    private JLabel NombrePrograma;
    private JLabel Subtitulo;
    private JButton profesorButton;
    private JButton alumnoInvitadoButton;
    private JButton nuevoUsuarioButton;
    private JButton alumno;
    private JButton add;
    private JPanel PanelPrincipal;
    private JButton docente;
    private JLabel SelectDocente;
    private JLabel SelectALumno;
    private JLabel SelectAdd;
    private JLabel indicaciones;

    public VentanaSeleccion() {
        try {
            // Mapeamos las rutas con los nombres EXACTOS de tus archivos reales
            File rutaLogusser = new File("imagenes/logusser.png");
            File rutaAddUsser = new File("imagenes/add usser.png");

            // 1. Cargar 'logusser.png' en el botón de Profesor
            if (rutaLogusser.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(rutaLogusser.getAbsolutePath());
                // Escalamos a 64x64 para que mantenga buena proporción
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                SelectDocente.setIcon(new ImageIcon(imgEscalada));
                SelectDocente.setText(""); // Borra el texto "Button" genérico
            } else {
                System.err.println("No se encontró el archivo en: " + rutaLogusser.getAbsolutePath());
            }

            // 2. Cargar 'add usser.png' en el botón de Alumno / Invitado
            if (rutaAddUsser.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(rutaLogusser.getAbsolutePath());
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                SelectALumno.setIcon(new ImageIcon(imgEscalada));
                SelectALumno.setText(""); // Borra el texto genérico
            } else {
                System.err.println("No se encontró el archivo en: " + rutaAddUsser.getAbsolutePath());
            }

            if (rutaAddUsser.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(rutaAddUsser.getAbsolutePath());
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                SelectAdd.setIcon(new ImageIcon(imgEscalada));
                SelectAdd.setText(""); // Borra el texto genérico
            } else {
                System.err.println("No se encontró el archivo en: " + rutaAddUsser.getAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("Error al cargar los íconos de usuario: " + e.getMessage());
        }

        try {
            // 1. Aplicamos "MADE Waffle Soft" al título principal (Laboratory Maker)
            // El nombre oficial en Java suele ser "MADE Waffle Soft" o "MADE Waffle Soft Personal Use"
            Font fuenteTitulo = new Font("MADE Waffle Soft", Font.BOLD, 75);
            NombrePrograma.setFont(fuenteTitulo);

            // 2. Aplicamos "Baliw" al subtítulo (Sala de impresoras 3D)
            Font fuenteSubtitulo = new Font("Baliw", Font.BOLD, 35);
            Subtitulo.setFont(fuenteSubtitulo);

        } catch (Exception e) {
            System.err.println("Error al aplicar las fuentes a los títulos: " + e.getMessage());
        }

        try {
            // 1. Obtenemos la fuente actual de FlatLaf para no perder el estilo limpio
            Font fuenteActual = indicaciones.getFont();

            // 2. Creamos una nueva versión más grande (ej: tamaño 18 o 20) y en negrita (Font.BOLD)
            Font fuenteIdentificarse = new Font(fuenteActual.getName(), Font.BOLD, 18);

            // 3. Se la aplicamos a la etiqueta
            // (Asegurate de cambiar "button2" por el nombre exacto de la variable de ese texto)
            indicaciones.setFont(fuenteIdentificarse);

        } catch (Exception e) {
            System.err.println("No se pudo agrandar el texto de identificación: " + e.getMessage());
        }

        try {
            // 1. Definimos una fuente más grande y en negrita para los botones
            // Usamos la tipografía del sistema (FlatLaf) pero en tamaño 16
            Font fuenteBotones = new Font(docente.getFont().getName(), Font.BOLD, 16);

            // 2. Se la aplicamos a los tres botones de abajo
            docente.setFont(fuenteBotones);
            alumno.setFont(fuenteBotones);
            add.setFont(fuenteBotones);

        } catch (Exception e) {
            System.err.println("Error al cambiar el tamaño de texto de los botones: " + e.getMessage());
        }

        docente.addActionListener(e -> abrirPantallaLogin("Docente"));
        alumno.addActionListener(e -> abrirPantallaLogin("Alumno"));
        add.addActionListener(e -> abrirPantallaAdd());

    }

    private void abrirPantallaLogin(String rolSeleccionado) {
        JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(PanelPrincipal);

        if (frameActual != null) {
            PantallaLogin pantallaLogin = new PantallaLogin(rolSeleccionado);
            frameActual.setContentPane(pantallaLogin.getPanelLog());
            frameActual.revalidate();
            frameActual.repaint();
        }
    }

    private void abrirPantallaAdd() {
        JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(PanelPrincipal);

        if (frameActual != null) {
            PantallaAddUser pantallaAddUser = new PantallaAddUser();
            frameActual.setContentPane(pantallaAddUser.getPanelAdd());
            frameActual.revalidate();
            frameActual.repaint();
        }
    }


    public JLabel getNombrePrograma() {
        return NombrePrograma;
    }

    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        PanelPrincipal = panelPrincipal;
    }

    public void setNombrePrograma(JLabel nombrePrograma) {
        NombrePrograma = nombrePrograma;
    }

    public JLabel getSubtitulo() {
        return Subtitulo;
    }

    public void setSubtitulo(JLabel subtitulo) {
        Subtitulo = subtitulo;
    }

    public JButton getProfesorButton() {
        return profesorButton;
    }

    public void setProfesorButton(JButton profesorButton) {
        this.profesorButton = profesorButton;
    }

    public JButton getAlumnoInvitadoButton() {
        return alumnoInvitadoButton;
    }

    public void setAlumnoInvitadoButton(JButton alumnoInvitadoButton) {
        this.alumnoInvitadoButton = alumnoInvitadoButton;
    }

    public JButton getNuevoUsuarioButton() {
        return nuevoUsuarioButton;
    }

    public void setNuevoUsuarioButton(JButton nuevoUsuarioButton) {
        this.nuevoUsuarioButton = nuevoUsuarioButton;
    }

    public JButton getButton2() {
        return alumno;
    }

    public void setButton2(JButton button2) {
        this.alumno = button2;
    }

    public JButton getButton3() {
        return add;
    }

    public void setButton3(JButton button3) {
        this.add = button3;
    }
}
