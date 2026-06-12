package laboratorio.Vistas;

import javax.swing.*;
import java.awt.*;

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
    private JLabel SelectUser;
    private JLabel SelectAdm;
    private JLabel SelectAdd;
    private JLabel indicaciones;

    public VentanaSeleccion() {
        try {
            // se carga las imagenes como recurso
            java.net.URL urlLogUser = getClass().getResource("/imagenes/logusser.png");
            java.net.URL urlAddUser = getClass().getResource("/imagenes/addUsser.png");
            java.net.URL urlAdm = getClass().getResource("/imagenes/admUser.png");

            // Agrega 'logusser.png' en el botón de Profesor y Alumno
            if (urlLogUser != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlLogUser);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                SelectUser.setIcon(new ImageIcon(imgEscalada));
                SelectUser.setText("");
            } else {
                System.err.println("No se encontró el recurso: /imagenes/logusser.png");
            }

            if  (urlAdm != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlAdm);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                SelectAdm.setIcon(new ImageIcon(imgEscalada));
                SelectAdm.setText("");
            } else {
                System.err.println("No se encontró el recurso: /imagenes/admUser.png");
            }

            // lo mismo pero con 'add user'
            if (urlAddUser != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlAddUser);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                SelectAdd.setIcon(new ImageIcon(imgEscalada));
                SelectAdd.setText("");
            } else {
                System.err.println("No se encontró el recurso: /imagenes/addUser.png");
            }

        } catch (Exception e) {
            System.err.println("Error al cargar los íconos de usuario: " + e.getMessage());
        }

        try {
            // aplica MADE Waffle Soft al título principal
            Font fuenteTituloWaffle = new Font("MADEWaffleSoft", Font.BOLD, 75);
            NombrePrograma.setFont(fuenteTituloWaffle);

            // aplica Baliw al subtítulo
            Font fuenteSubtituloBaliw = new Font("Baliw", Font.BOLD, 35);
            Subtitulo.setFont(fuenteSubtituloBaliw);

        } catch (Exception e) {
            System.err.println("Error al asignar las fuentes especiales en la vista: " + e.getMessage());
        }

        try {
            // Obtenemos la fuente actual de FlatLaf para no perder el estilo
            Font fuenteActual = indicaciones.getFont();

            // crea una nueva versión más grande (ej: tamaño 18 o 20) y en negrita (Font.BOLD)
            Font fuenteIdentificarse = new Font(fuenteActual.getName(), Font.BOLD, 18);

            // aplicado a la etiqueta
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

        docente.addActionListener(e -> abrirPantallaLogin());
        alumno.addActionListener(e -> abrirPantallaLogin());
        add.addActionListener(e -> abrirPantallaAdd());

    }

    private void abrirPantallaLogin() {
        JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(PanelPrincipal);

        if (frameActual != null) {
            PantallaLogin pantallaLogin = new PantallaLogin();
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
