package laboratorio.Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaNuevoArchivo extends JDialog {
    private JPanel PanelNuevoArchivo;
    private JButton btnSolicitar;
    private JButton buttonCancel;
    private JTextField titularSolicitud;
    private JTextField nombreArchivo;
    private JComboBox modelosDeImpresion;
    private JLabel headerSolicitud;

    public VentanaNuevoArchivo() {
        setContentPane(PanelNuevoArchivo);
        setModal(true);
        getRootPane().setDefaultButton(btnSolicitar);

        btnSolicitar.addActionListener(e -> {
            // 1. Usamos 'this' directamente ya que esta clase YA ES un JDialog (tu ventana padre)
            JDialog ventanaPadre = this;

            // 2. Creamos el SEGUNDO JDialog (el de éxito o carga completa)
            JDialog dialogoExito = new JDialog(ventanaPadre, "Solicitud Exitosa", Dialog.ModalityType.APPLICATION_MODAL);

            // 3. Instanciamos la vista del segundo cartel
            VentanaConfirmacion ventanaExito = new VentanaConfirmacion();
            dialogoExito.setContentPane(ventanaExito.getPanelConfirmacion());

            // 4. Configuramos tamaño y posición del segundo cartel
            dialogoExito.setResizable(false);
            dialogoExito.setSize(350, 350);
            dialogoExito.setLocationRelativeTo(ventanaPadre); // Lo centra perfecto arriba de este formulario

            // [OPCIONAL] Si querés que el formulario de filamento se cierre automáticamente
            // al presionar "Solicitar", descomentá la siguiente línea:
            // dispose();

            // 5. mostrar confirmacion
            dialogoExito.setVisible(true);
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        PanelNuevoArchivo.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        try {
            // 1. Tomamos la fuente por defecto de FlatLaf para mantener la armonía
            Font fuenteBase = headerSolicitud.getFont();

            // 2. Creamos un estilo más grande (ej: tamaño 18) y en negrita (Font.BOLD)
            Font fuentecuerpo = new Font(fuenteBase.getName(), Font.PLAIN, 16);
            Font fuenteTitulos = new Font(fuenteBase.getName(), Font.BOLD, 18);


            // 3. Se lo aplicamos a las dos etiquetas de texto
            titularSolicitud.setFont(fuentecuerpo);
            nombreArchivo.setFont(fuentecuerpo);
            headerSolicitud.setFont(fuenteTitulos);
            btnSolicitar.setFont(fuentecuerpo);
            buttonCancel.setFont(fuentecuerpo);

        } catch (Exception e) {
            System.err.println("Error al agrandar los textos del login: " + e.getMessage());
        }
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        VentanaNuevoArchivo dialog = new VentanaNuevoArchivo();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public JPanel getPanelNuevoArchivo() {
        return PanelNuevoArchivo;
    }
}
