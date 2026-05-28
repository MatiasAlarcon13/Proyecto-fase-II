package laboratorio.Vistas;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PantallaInicioAlumnos {

    private JPanel PanelAlumno;
    private JPanel PanelImpresion;
    private JTable table1;
    private JPanel infoModelos;
    private JLabel headerModelos;
    private JPanel modeloCasa;
    private JPanel modeloPuente;
    private JPanel modeloPelota;
    private JLabel usuarioNombre;
    private JLabel cuotaDisponible;
    private JLabel nombre;
    private JLabel cuota;
    private JLabel imgCasa;
    private JLabel imgPuente;
    private JLabel imgPelota;
    private JLabel tiempoImpresionC;
    private JLabel totalCapasC;
    private JLabel materialRequeridoC;
    private JLabel tiempoImpresionPu;
    private JLabel totalCapasPu;
    private JLabel materialRequeridoPu;
    private JLabel tiempoImpresionPe;
    private JLabel totalCapasPe;
    private JLabel materialRequeridosPe;
    private JLabel tituloCasa;
    private JPanel panelPuente;
    private JLabel tituloPelota;
    private JButton btnNuevoArchivo;
    private JButton btnBack;
    private JLabel tituloPuente;
    private JPanel panelCasa;
    private JPanel panelPelota;
    private JPanel PanelUsuario;
    private JScrollPane scrollModelos;
    private JPanel PanelBoton;
    private JPanel PanelModelos;
    private JPanel PanelIzq;
    private JPanel PanelDer;

    public PantallaInicioAlumnos() {
        try {
            File rutaCasa = new File("imagenes/casa.png");
            File rutaPuente = new File("imagenes/puente.png");
            File rutaPelota = new File("imagenes/pelota.png");

            //cargar imagen correspondiente
            if (rutaCasa.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(rutaCasa.getAbsolutePath());
                // Escalamos a 100*100 para que mantenga buena proporción
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imgCasa.setIcon(new ImageIcon(imgEscalada));
                imgCasa.setText(""); // Borra el texto "Button" genérico
            } else {
                System.err.println("No se encontró el archivo en: " + rutaCasa.getAbsolutePath());
            }

            // 2. Cargar 'add usser.png' en el botón de Alumno / Invitado
            if (rutaPuente.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(rutaPuente.getAbsolutePath());
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imgPuente.setIcon(new ImageIcon(imgEscalada));
                imgPuente.setText(""); // Borra el texto genérico
            } else {
                System.err.println("No se encontró el archivo en: " + rutaPuente.getAbsolutePath());
            }

            if (rutaPelota.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(rutaPelota.getAbsolutePath());
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imgPelota.setIcon(new ImageIcon(imgEscalada));
                imgPelota.setText(""); // Borra el texto genérico
            } else {
                System.err.println("No se encontró el archivo en: " + rutaPelota.getAbsolutePath());
            }

        } catch (Exception e) {
            System.err.println("Error al cargar los íconos de usuario: " + e.getMessage());
        }

        try {
            // 1. Tomamos la fuente por defecto de FlatLaf para mantener la armonía
            Font fuenteBase = usuarioNombre.getFont();

            // 2. Creamos un estilo más grande (ej: tamaño 18) y en negrita (Font.BOLD)
            Font fuentecuerpo = new Font(fuenteBase.getName(), Font.PLAIN, 16);
            Font fuenteUsuario = new Font(fuenteBase.getName(), Font.BOLD, 12);
            Font fuenteTitulos = new Font(fuenteBase.getName(), Font.BOLD, 18);


            // 3. Se lo aplicamos a las dos etiquetas de texto
            usuarioNombre.setFont(fuenteUsuario);
            cuotaDisponible.setFont(fuenteUsuario);
            cuota.setFont(fuenteUsuario);
            nombre.setFont(fuenteUsuario);
            btnNuevoArchivo.setFont(fuentecuerpo);
            btnBack.setFont(fuentecuerpo);
            headerModelos.setFont(fuenteTitulos);
            tituloCasa.setFont(fuenteTitulos);
            tituloPuente.setFont(fuenteTitulos);
            tituloPelota.setFont(fuenteTitulos);
            tiempoImpresionC.setFont(fuentecuerpo);
            tiempoImpresionPe.setFont(fuentecuerpo);
            tiempoImpresionPu.setFont(fuentecuerpo);
            materialRequeridoC.setFont(fuentecuerpo);
            materialRequeridoPu.setFont(fuentecuerpo);
            materialRequeridosPe.setFont(fuentecuerpo);
            totalCapasC.setFont(fuentecuerpo);
            totalCapasPe.setFont(fuentecuerpo);
            totalCapasPu.setFont(fuentecuerpo);

        } catch (Exception e) {
            System.err.println("Error al agrandar los textos del login: " + e.getMessage());
        }

        btnBack.addActionListener(e -> {
            // 1. Buscamos el marco (JFrame) actual donde está metido este panel
            // (Reemplazá "panelAddUser" por el nombre de tu Jpanel principal de esta pantalla)
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(PanelAlumno);

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

        // Acá vas a poder programar lo que pasa cuando el alumno
        // quiera subir un "Nuevo Archivo" STL para imprimir.
        btnNuevoArchivo.addActionListener(e -> {
            // Ejemplo: Abrir un selector de archivos de la compu
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(PanelAlumno);
        });
    }
    // El puente indispensable para que el JFrame lo pueda mostrar en pantalla
    public JPanel getPanelAlumno () {
        return PanelAlumno;
    }

}

