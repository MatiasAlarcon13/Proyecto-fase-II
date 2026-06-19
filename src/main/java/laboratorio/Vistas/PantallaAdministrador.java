package laboratorio.Vistas;

import laboratorio.Modelos.Usuario;
import laboratorio.Modelos.ModelosImpresion;
import laboratorio.Controladores.ModelosImpresionController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PantallaAdministrador {

    // Contenedores del árbol principal
    private JPanel PanelPrincipalAdm;
    private JPanel MenuLateral;
    private JPanel ContenedorVistas;

    // Botones laterales
    private JButton btnVistaGeneral;
    private JButton btnBobinas;
    private JButton btnImpresoras;
    private JButton btnModelos3D;
    private JButton btnback;
    private JLabel datosAdm;

    // Sub-vistas
    private JPanel PanelGeneral;
    private JPanel PanelBobinas;
    private JPanel PanelImpresoras;
    private JPanel PanelModelos3D;

    // Componentes del ABM de Modelos 3D (Mapeados desde el Form)
    private JTable tablaModelos;
    private JLabel nombre;
    private JLabel gramos;
    private JLabel totalCapas;
    private JTextField txtNombre;
    private JTextField txtGramos;
    private JTextField txtCapas;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private final ModelosImpresionController modelosController = new ModelosImpresionController();
    private ModelosImpresion modeloSeleccionado = null;

    public PantallaAdministrador(Usuario usuarioLogueado) {
        if (usuarioLogueado != null) {
            this.datosAdm.setText("Admin: " + usuarioLogueado.getNombre());
        }

        CardLayout cardLayout = (CardLayout) ContenedorVistas.getLayout();

        btnVistaGeneral.addActionListener(e -> cardLayout.show(ContenedorVistas, "VistaGeneral"));
        btnBobinas.addActionListener(e -> cardLayout.show(ContenedorVistas, "Bobinas"));
        btnImpresoras.addActionListener(e -> cardLayout.show(ContenedorVistas, "Impresoras"));

        // Al hacer clic en el botón, cambia la pestaña y refresca los datos desde MySQL
        btnModelos3D.addActionListener(e -> {
            cardLayout.show(ContenedorVistas, "Modelos3D");
            actualizarTablaModelos();
        });

        // ─── ACCIÓN: GUARDAR / EDITAR ───
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() || txtGramos.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(PanelPrincipalAdm, "Por favor complete los campos obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (modeloSeleccionado == null) {
                modeloSeleccionado = new ModelosImpresion();
            }

            try {
                modeloSeleccionado.setNombreModelo(txtNombre.getText().trim());
                modeloSeleccionado.setGramosRequeridos(Integer.parseInt(txtGramos.getText().trim()));
                modeloSeleccionado.setTotalCapas(Integer.parseInt(txtCapas.getText().trim()));

                ModelosImpresion resultado = modelosController.guardarModelo(modeloSeleccionado);
                if (resultado != null) {
                    limpiarFormularioModelos();
                    actualizarTablaModelos();
                    JOptionPane.showMessageDialog(PanelPrincipalAdm, "Modelo guardado correctamente.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(PanelPrincipalAdm, "Gramos y capas deben ser valores numéricos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ─── ACCIÓN: SELECCIONAR FILA DE LA JTABLE ───
        tablaModelos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaModelos.getSelectedRow();
                if (fila >= 0) {
                    int id = (int) tablaModelos.getValueAt(fila, 0);
                    String nombre = (String) tablaModelos.getValueAt(fila, 1);
                    int gramos = (int) tablaModelos.getValueAt(fila, 2);
                    int capas = (int) tablaModelos.getValueAt(fila, 3);

                    modeloSeleccionado = new ModelosImpresion();
                    modeloSeleccionado.setIdModelo(id);

                    txtNombre.setText(nombre);
                    txtGramos.setText(String.valueOf(gramos));
                    txtCapas.setText(String.valueOf(capas));
                }
            }
        });

        // ─── ACCIÓN: ELIMINAR REGISTRO ───
        btnEliminar.addActionListener(e -> {
            if (modeloSeleccionado != null && modeloSeleccionado.getIdModelo() != 0) {
                int conf = JOptionPane.showConfirmDialog(PanelPrincipalAdm, "¿Seguro que desea eliminar este modelo?", "Confirmar baja", JOptionPane.YES_NO_OPTION);
                if (conf == JOptionPane.YES_OPTION) {
                    modelosController.eliminarModelo(modeloSeleccionado.getIdModelo());
                    limpiarFormularioModelos();
                    actualizarTablaModelos();
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarFormularioModelos());

        btnback.addActionListener(e -> {
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(PanelPrincipalAdm);
            if (frameActual != null) {
                VentanaSeleccion pantallaInicio = new VentanaSeleccion();
                frameActual.setContentPane(pantallaInicio.getPanelPrincipal());
                frameActual.revalidate();
                frameActual.repaint();
            }
        });

        configurarFuentesDefensivas();
    }

    private void actualizarTablaModelos() {
        String[] columnas = {"ID", "Nombre Modelo", "Gramos Requeridos", "Total Capas"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        try {
            List<ModelosImpresion> lista = modelosController.listarTodos();
            for (ModelosImpresion m : lista) {
                Object[] fila = { m.getIdModelo(), m.getNombreModelo(), m.getGramosRequeridos(), m.getTotalCapas() };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            // Manejo defensivo silencioso
        }
        tablaModelos.setModel(modeloTabla);
    }

    private void limpiarFormularioModelos() {
        modeloSeleccionado = null;
        txtNombre.setText("");
        txtGramos.setText("");
        txtCapas.setText("");
        tablaModelos.clearSelection();
    }

    private void configurarFuentesDefensivas() {
        try {
            Font fuenteBase = datosAdm.getFont();
            Font fuenteBotones = new Font(fuenteBase.getName(), Font.PLAIN, 15);
            datosAdm.setFont(new Font(fuenteBase.getName(), Font.BOLD, 16));
            btnVistaGeneral.setFont(fuenteBotones);
            btnBobinas.setFont(fuenteBotones);
            btnImpresoras.setFont(fuenteBotones);
            btnModelos3D.setFont(fuenteBotones);
            btnback.setFont(fuenteBotones);
        } catch (Exception e) {
            // Silencioso
        }
    }

    public JPanel getPanelPrincipalAdm() {
        return PanelPrincipalAdm;
    }
}