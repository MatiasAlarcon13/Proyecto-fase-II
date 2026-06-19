package laboratorio.Vistas;

import laboratorio.Modelos.Usuario;
import laboratorio.Modelos.ModelosImpresion;
import laboratorio.Controladores.ModelosImpresionController;
import laboratorio.Controladores.SolicitudController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaNuevoArchivo extends JDialog {
    private JPanel PanelNuevoArchivo;
    private JButton btnSolicitar;
    private JButton buttonCancel;
    private JTextField titularSolicitud;
    private JTextField nombreArchivo;
    private JComboBox<String> modelosDeImpresion;
    private JLabel headerSolicitud;

    private final SolicitudController solicitudController = new SolicitudController();
    private final ModelosImpresionController modelosController = new ModelosImpresionController();
    private final Usuario usuarioActual;

    public VentanaNuevoArchivo(Usuario usuario) {
        this.usuarioActual = usuario;

        setContentPane(PanelNuevoArchivo);
        setModal(true);
        getRootPane().setDefaultButton(btnSolicitar);

        // ─── 1. POPULAR EL DESPLEGABLE (Como tu boceto) ───
        modelosDeImpresion.addItem("Casa");
        modelosDeImpresion.addItem("Pelota");
        modelosDeImpresion.addItem("Puente");

        // ─── 2. SELECCIÓN INICIAL POR DEFECTO ───
        actualizarCamposPorModelo("Casa");

        // ─── 3. OYENTE DE CAMBIOS (Actualiza los textos al desplegar y cambiar de opción) ───
        modelosDeImpresion.addActionListener(e -> {
            String opcionSeleccionada = (String) modelosDeImpresion.getSelectedItem();
            actualizarCamposPorModelo(opcionSeleccionada);
        });

        btnSolicitar.addActionListener(e -> {
            String modeloSeleccionado = (String) modelosDeImpresion.getSelectedItem();
            SolicitudController.ResultadoSolicitud resultado =
                    solicitudController.crearSolicitud(modeloSeleccionado, usuarioActual);
            if (resultado.exito()) {
                JDialog dialogoExito = new JDialog(this, "Solicitud Exitosa", Dialog.ModalityType.APPLICATION_MODAL);
                VentanaConfirmacion ventanaExito = new VentanaConfirmacion();
                dialogoExito.setContentPane(ventanaExito.getPanelConfirmacion());
                dialogoExito.setResizable(false);
                dialogoExito.setSize(350, 350);
                dialogoExito.setLocationRelativeTo(this);

                dispose();
                dialogoExito.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        resultado.getError(),
                        "No se pudo crear la solicitud",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        buttonCancel.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { onCancel(); }
        });

        // Estilos visuales de fuentes
        configurarFuentes();
    }

    /**
     * Método auxiliar que busca los datos del modelo seleccionado en la base de datos
     * y rellena automáticamente los campos de texto correspondientes.
     */
    private void actualizarCamposPorModelo(String nombreModelo) {
        ModelosImpresion modeloReal = modelosController.obtenerModelosImpresion(nombreModelo);
        if (modeloReal != null) {
            titularSolicitud.setText(modeloReal.getNombreModelo());
            nombreArchivo.setText(modeloReal.getNombreModelo().toLowerCase() + "_proyecto.gcode");
        } else {
            titularSolicitud.setText("");
            nombreArchivo.setText("");
        }
    }

    private void configurarFuentes() {
        try {
            Font fuenteBase = headerSolicitud.getFont();
            Font fuentecuerpo = new Font(fuenteBase.getName(), Font.PLAIN, 16);
            Font fuenteTitulos = new Font(fuenteBase.getName(), Font.BOLD, 18);

            titularSolicitud.setFont(fuentecuerpo);
            nombreArchivo.setFont(fuentecuerpo);
            modelosDeImpresion.setFont(fuentecuerpo); // Aplicamos la fuente al JComboBox
            headerSolicitud.setFont(fuenteTitulos);
            btnSolicitar.setFont(fuentecuerpo);
            buttonCancel.setFont(fuentecuerpo);
        } catch (Exception e) {
            // Manejo silencioso de excepciones estéticas sin souts
        }
    }

    private void onCancel() { dispose(); }

    public JPanel getPanelNuevoArchivo() { return PanelNuevoArchivo; }
}