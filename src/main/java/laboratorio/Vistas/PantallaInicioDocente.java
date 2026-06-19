package laboratorio.Vistas;

import laboratorio.Modelos.SolicitudImpresion;
import laboratorio.Modelos.Usuario;

import javax.swing.*;
import java.util.List;


public class PantallaInicioDocente {
    private JPanel PanelDocente;
    private JPanel PanelDatosDocente;
    private JPanel PanelListaImpresiones;
    private JLabel modeloCasa;
    private JLabel modeloPuente;
    private JLabel modeloPelota;
    private JLabel nombreDocente;
    private JLabel cuota;
    private JPanel PanelTarjetasImpresion;
    private JButton btnback;


    public PantallaInicioDocente(Usuario usuarioLogueado) {
        if  (usuarioLogueado != null) {
            this.nombreDocente.setText("Prof. "+usuarioLogueado.getNombre());

            if (usuarioLogueado.getRol().equals("Docente")) {
                this.cuota.setText("Cuota: Ilimitada");
            }
        }

        PanelTarjetasImpresion.setLayout(new BoxLayout(PanelTarjetasImpresion, BoxLayout.Y_AXIS));
        cargarIconosModelos();
    }

    public void printSolicitudes(List<SolicitudImpresion> listaSolicitudes) {
        PanelTarjetasImpresion.removeAll();

        for  (SolicitudImpresion sol : listaSolicitudes) {
            ItemSolicitud tarjeta = new ItemSolicitud(
                    sol.getTitularSolicitud(),
                    sol.getModelo(),
                    sol.getCapas(),
                    sol.getGramosRequeridos(),
                    sol.getTiempoEstimado() + ""
            );

            tarjeta.getBtnOk().addActionListener(e -> {
                System.out.println("Solicitud Aprobada: "+sol.getIdSolicitud());
            });

            tarjeta.getBtnCancel().addActionListener(e -> {
                System.out.println("Solicitud Cancelada");
            });
        }
        PanelTarjetasImpresion.revalidate();
        PanelTarjetasImpresion.repaint();
    }

    private void cargarIconosModelos() {
        try {
            java.net.URL urlCasa = getClass().getResource("/imagenes/casa.png");
            if (urlCasa != null) {
                modeloCasa.setIcon(new ImageIcon(urlCasa));
                modeloCasa.setText("");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar iconos en panel docente: "+e.getMessage());
        }
        try {
            java.net.URL urlPelota = getClass().getResource("/imagenes/pelota.png");
            if (urlPelota != null) {
                modeloPelota.setIcon(new ImageIcon(urlPelota));
                modeloPelota.setText("");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar iconos en panel docente: "+e.getMessage());
        }
        try {
            java.net.URL urlPuente = getClass().getResource("/imagenes/puente.png");
            if (urlPuente != null) {
                modeloPuente.setIcon(new ImageIcon(urlPuente));
                modeloPuente.setText("");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar iconos en panel docente: "+e.getMessage());
        }
        btnback.addActionListener(e -> {
            JFrame actual = (JFrame)SwingUtilities.getWindowAncestor(PanelDocente);
            if(actual != null){
                VentanaSeleccion pantallaInicio = new VentanaSeleccion();
                actual.setContentPane(pantallaInicio.getPanelPrincipal());
                actual.revalidate();
                actual.repaint();
            }
        });
    }

    public JPanel getPanelDocente() {
        return PanelDocente;
    }
}
