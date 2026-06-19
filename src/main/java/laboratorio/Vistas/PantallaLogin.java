package laboratorio.Vistas;

import javax.swing.*;
import java.awt.*;
import laboratorio.Modelos.Usuario;

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

    public PantallaLogin() {
        try {
            // cargamos la imagen como recurso
            java.net.URL urlLogUser = getClass().getResource("/imagenes/logusser.png");

            if (urlLogUser != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlLogUser);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imgilustracion.setIcon(new ImageIcon(imgEscalada));
                imgilustracion.setText("");
            } else {
                System.err.println("No se encontró el recurso: /imagenes/logusser.png");
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

            // texto dentro de las cajas de 'datos'
            Font fuenteCajasBlancas = new Font(fuenteBase.getName(), Font.PLAIN, 16);
            textField1.setFont(fuenteCajasBlancas);
            passwordField1.setFont(fuenteCajasBlancas);

        } catch (Exception e) {
            System.err.println("Error al agrandar los textos del login: " + e.getMessage());
        }

        atrasButton.addActionListener(e -> {
            // Busca el marco (JFrame) actual donde está metido este panel
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(panelLog);

            if (frameActual != null) {
                // Instancia la pantalla de selección original
                VentanaSeleccion pantallaInicio = new VentanaSeleccion();

                // vuelve el PanelPrincipal del inicio a la ventana
                frameActual.setContentPane(pantallaInicio.getPanelPrincipal());

                // Refresca la interfaz para que se redibuje al instante
                frameActual.revalidate();
                frameActual.repaint();
            }
        });

        ingresarButton.addActionListener(e -> {
            String username = textField1.getText().trim();
            String password = new String(passwordField1.getPassword());
            laboratorio.Persistencia.UsuarioDAO usuarioDAO = new laboratorio.Persistencia.UsuarioDAO();

            Usuario usuarioLogueado = usuarioDAO.verificarCredenciales(username, password);

            if (usuarioLogueado != null) {
                String rolDB = usuarioLogueado.getRol();
                JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(panelLog);

                if (frameActual != null) {
                    if (rolDB != null && rolDB.equalsIgnoreCase("ADMIN")) {
                        // Va a tu nueva pantalla de control de administrador
                        PantallaAdministrador pantallaAdmin = new PantallaAdministrador(usuarioLogueado);
                        frameActual.setContentPane(pantallaAdmin.getPanelPrincipalAdm());
                    } else if (rolDB != null && rolDB.equalsIgnoreCase("Docente")) {
                        PantallaInicioDocente pantallaInicioDocente = new PantallaInicioDocente(usuarioLogueado);
                        frameActual.setContentPane(pantallaInicioDocente.getPanelDocente());
                    } else {
                        PantallaInicioAlumnos pantallaInicioAlumnos = new PantallaInicioAlumnos(usuarioLogueado);
                        frameActual.setContentPane(pantallaInicioAlumnos.getPanelAlumno());
                    }
                    frameActual.revalidate();
                    frameActual.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(panelLog, "DNI o contraseña incorrectos.", "Error de Login", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JPanel getPanelLog() {
        return panelLog;
    }
}
