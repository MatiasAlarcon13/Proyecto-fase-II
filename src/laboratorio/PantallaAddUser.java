package laboratorio;
import java.awt.Image;
import java.io.File;
import javax.swing.*;
import java.awt.Font;

public class PantallaAddUser {
    private JPanel PanelAdd;
    private JTextField addNombre;
    private JTextField AddDNI;
    private JRadioButton RadioDocente;
    private JRadioButton RadioAlumno;
    private JLabel Nombre;
    private JLabel DNI;
    private JLabel AddIlustracion;
    private JLabel SeleccionarTipo;
    private JLabel AddMEnsaje;
    private JButton btnback;
    private JButton registrarseButton;

    public PantallaAddUser() {
        try {
            // Mapeamos las rutas con los nombres EXACTOS de tus archivos reales
            File rutaaddilustracion = new File("imagenes/add usser.png");

            if (rutaaddilustracion.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(rutaaddilustracion.getAbsolutePath());

                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                AddIlustracion.setIcon(new ImageIcon(imgEscalada));
                AddIlustracion.setText(""); // Borra el texto "Button" genérico
            } else {
                System.err.println("No se encontró el archivo en: " + rutaaddilustracion.getAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("Error al cargar los íconos de usuario: " + e.getMessage());
        }

        try {
            // 1. Tomamos la fuente por defecto de FlatLaf para mantener la armonía
            Font fuenteBase = Nombre.getFont();

            // 2. Creamos un estilo más grande (ej: tamaño 18) y en negrita (Font.BOLD)
            Font fuenteCamposText = new Font(fuenteBase.getName(), Font.BOLD, 18);

            // 3. Se lo aplicamos a las dos etiquetas de texto
            Nombre.setFont(fuenteCamposText);
            DNI.setFont(fuenteCamposText);
            AddMEnsaje.setFont(fuenteCamposText);
            SeleccionarTipo.setFont(fuenteCamposText);
            RadioDocente.setFont(fuenteCamposText);
            RadioAlumno.setFont(fuenteCamposText);
            btnback.setFont(fuenteCamposText);
            registrarseButton.setFont(fuenteCamposText);

            // OPCIONAL: Si querés que el texto que escribe el usuario adentro de las cajas
            // también sea más grande y legible, podés sumarle esto:
            Font fuenteCajasBlancas = new Font(fuenteBase.getName(), Font.PLAIN, 16);
            addNombre.setFont(fuenteCajasBlancas);
            AddDNI.setFont(fuenteCajasBlancas);

        } catch (Exception e) {
            System.err.println("Error al agrandar los textos del login: " + e.getMessage());
        }

        ButtonGroup grupoRoles = new ButtonGroup();
        grupoRoles.add(RadioDocente);
        grupoRoles.add(RadioAlumno);

        btnback.addActionListener(e -> {
            // 1. Buscamos el marco (JFrame) actual donde está metido este panel
            // (Reemplazá "panelAddUser" por el nombre de tu Jpanel principal de esta pantalla)
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(PanelAdd);

            if (frameActual != null) {
                // 2. Instanciamos la pantalla de selección original
                laboratorio.VentanaSeleccion pantallaInicio = new laboratorio.VentanaSeleccion();

                // 3. Le volvemos a poner el PanelPrincipal del inicio a la ventana
                frameActual.setContentPane(pantallaInicio.getPanelPrincipal());

                // 4. Refrescamos la interfaz para que se redibuje al instante
                frameActual.revalidate();
                frameActual.repaint();
            }
        });
    }

    public JPanel getPanelAdd() {
        return PanelAdd;
    }
}
