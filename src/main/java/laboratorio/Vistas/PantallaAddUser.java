package laboratorio.Vistas;

import javax.swing.*;
import java.awt.*;
import laboratorio.Modelos.Alumno;
import laboratorio.Modelos.Profesor;
import laboratorio.Modelos.Usuario;
import laboratorio.Persistencia.UsuarioDAO;

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
    private JTextField IngresarCorreo;
    private JLabel Correo;

    public PantallaAddUser() {

        try {
            // 1. Cargamos las imágenes como Recursos usando el ClassLoader
            java.net.URL urlAddUser = getClass().getResource("/imagenes/addUsser.png");

            if (urlAddUser != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlAddUser);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                AddIlustracion.setIcon(new ImageIcon(imgEscalada));
                AddIlustracion.setText("");
            } else {
                System.err.println("No se encontró el recurso: /imagenes/addUsser.png");
            }

        } catch (Exception e) {
            System.err.println("Error al cargar los íconos: " + e.getMessage());
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
            Correo.setFont(fuenteCamposText);


            //feunte en las cajas de texto
            Font fuenteCajasBlancas = new Font(fuenteBase.getName(), Font.PLAIN, 16);
            addNombre.setFont(fuenteCajasBlancas);
            AddDNI.setFont(fuenteCajasBlancas);
            IngresarCorreo.setFont(fuenteCajasBlancas);

        } catch (Exception e) {
            System.err.println("Error al agrandar los textos del login: " + e.getMessage());
        }

        ButtonGroup grupoRoles = new ButtonGroup();
        grupoRoles.add(RadioDocente);
        grupoRoles.add(RadioAlumno);

        btnback.addActionListener(e -> {
            // 1. Buscamos el marco (JFrame) actual donde está metido este panel
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(PanelAdd);

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

        registrarseButton.addActionListener(e -> {
            String dniAdd = AddDNI.getText().trim();
            String nombreAdd = addNombre.getText().trim();
            String addCorreo = IngresarCorreo.getText().trim();

            if (dniAdd.isEmpty() || nombreAdd.isEmpty() || addCorreo.isEmpty()) {
                JOptionPane.showMessageDialog(PanelAdd, "Por favor, complete todos los campos obligatorios.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int dni = Integer.parseInt(dniAdd);

                Usuario nuevoUsuario;
                if(RadioDocente.isSelected()) {
                    nuevoUsuario = new Profesor(nombreAdd,dni, addCorreo, 500);
                } else {
                    nuevoUsuario = new Alumno(nombreAdd, dni, addCorreo, 500);
                }
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.guardar(nuevoUsuario);

                JOptionPane.showMessageDialog(PanelAdd, "Usuario registrado correctamente en el sistema.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            } catch (NumberFormatException ex) {
            // Captura controlada del error si escriben letras en el campo DNI
            JOptionPane.showMessageDialog(PanelAdd, "Ingresar unicamente numeros sin punto.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Captura ante cualquier problema del motor de persistencia de Hibernate
            JOptionPane.showMessageDialog(PanelAdd, "Ocurrió un error inesperado al almacenar el perfil: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
        });
    }

    private void limpiarFormulario() {
        addNombre.setText("");
        AddDNI.setText("");
        IngresarCorreo.setText("");
        RadioAlumno.setSelected(true);
    }

    public JPanel getPanelAdd() {
        return PanelAdd;
    }
}
