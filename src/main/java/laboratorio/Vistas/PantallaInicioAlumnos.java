package laboratorio.Vistas;

import laboratorio.Modelos.Usuario;
import javax.swing.*;
import java.awt.*;

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

    public PantallaInicioAlumnos(Usuario usuarioLogueado) {

        if (usuarioLogueado != null) {
            this.usuarioNombre.setText(usuarioLogueado.getNombre());
            this.cuotaDisponible.setText("Cuota: "+usuarioLogueado.getCuota()+"g");
        }
        try {
            // 1. Cargamos las imágenes como Recursos usando el ClassLoader
            java.net.URL urlCasa = getClass().getResource("/imagenes/casa.png");
            java.net.URL urlPuente = getClass().getResource("/imagenes/puente.png");
            java.net.URL urlPelota = getClass().getResource("/imagenes/pelota.png");

            // Escalado de la Imagen de la Casa
            if (urlCasa != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlCasa);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imgCasa.setIcon(new ImageIcon(imgEscalada));
                imgCasa.setText("");
            } else {
                System.err.println("No se encontró el recurso: /imagenes/casa.png");
            }

            // Escalado de la Imagen del Puente
            if (urlPuente != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlPuente);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imgPuente.setIcon(new ImageIcon(imgEscalada));
                imgPuente.setText("");
            } else {
                System.err.println("No se encontró el recurso: /imagenes/puente.png");
            }

            // Escalado de la Imagen de la Pelota
            if (urlPelota != null) {
                ImageIcon iconoOriginal = new ImageIcon(urlPelota);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imgPelota.setIcon(new ImageIcon(imgEscalada));
                imgPelota.setText("");
            } else {
                System.err.println("No se encontró el recurso: /imagenes/pelota.png");
            }

        } catch (Exception e) {
            System.err.println("Error al cargar los íconos de los modelos: " + e.getMessage());
        }

        try {
            // Tomamos la fuente por defecto de FlatLaf para mantener la armonía
            Font fuenteBase = usuarioNombre.getFont();

            // Creamos un estilo más grande (ej: tamaño 18) y en negrita (Font.BOLD)
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

        // accion del boton 'nuevo archivo'
        btnNuevoArchivo.addActionListener(e -> {
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(PanelAlumno);

            if (frameActual != null) {
                //Ventana emergente
                JDialog dialogo = new JDialog(frameActual, "Subir Nuevo Archivo", true); // 'true' la hace modal

                // diseño del formulario flotante
                VentanaNuevoArchivo formFlotante = new VentanaNuevoArchivo();

                // panel de tu diseño adentro del diálogo emergente
                dialogo.setContentPane(formFlotante.getPanelNuevoArchivo());

                // config para que se vea bien
                dialogo.setResizable(false); // Evita que el alumno la deforme estirándola
                dialogo.setSize(350, 400); // Ancho y Alto fijos
                dialogo.setLocationRelativeTo(frameActual); // La clava perfectamente en el centro de la app


                // 6. La hacemos visible
                dialogo.setVisible(true);
            }
        });
    }

    public JPanel getPanelAlumno () {
        return PanelAlumno;
    }

}

