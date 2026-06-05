package laboratorio.Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaConfirmacion extends JDialog {
    private JPanel PanelConfirmacion;
    private JButton buttonOK;
    private JPanel PanelDatos;
    private JLabel headerConfirmacion;
    private JLabel mensajeEspera;
    private JLabel tiempoImpresion;
    private JLabel tiempo;
    private JLabel materialRequerido;
    private JLabel material;
    private JLabel totalCapas;
    private JLabel Capas;
    private JButton buttonCancel;

    public VentanaConfirmacion() {
        setContentPane(PanelConfirmacion);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        try {
            // 1. Tomamos la fuente por defecto de FlatLaf para mantener la armonía
            Font fuenteBase = headerConfirmacion.getFont();

            // 2. Creamos un estilo más grande (ej: tamaño 18) y en negrita (Font.BOLD)
            Font fuentecuerpo = new Font(fuenteBase.getName(), Font.PLAIN, 16);
            Font fuenteTitulos = new Font(fuenteBase.getName(), Font.BOLD, 18);
            Font fuenteSub = new Font(fuenteBase.getName(), Font.PLAIN, 12);


            // 3. Se lo aplicamos a las dos etiquetas de texto
            mensajeEspera.setFont(fuenteSub);
            headerConfirmacion.setFont(fuenteTitulos);
            buttonOK.setFont(fuentecuerpo);


        } catch (Exception e) {
            System.err.println("Error al agrandar los textos del login: " + e.getMessage());
        }
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    public static void main(String[] args) {
        VentanaConfirmacion dialog = new VentanaConfirmacion();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public JPanel getPanelConfirmacion() {
        return PanelConfirmacion;
    }
}
