package laboratorio.Vistas;

import javax.swing.*;
import java.awt.*;

public class ItemSolicitud {
    private JPanel SolicitudAlumno;
    private JPanel PanelImagen;
    private JPanel PanelDatos;
    private JPanel PanelOk;
    private JPanel PanelCancel;
    private JLabel imgUser;
    private JLabel nombreUser;
    private JLabel modelo;
    private JLabel totalCapas;
    private JLabel filamentoRequerido;
    private JLabel tiempoImpresion;
    private JButton btnOk;
    private JButton btnCancel;


    public ItemSolicitud(String titular, String infomodelo, int capas, double filamento, String tiempo) {
        nombreUser.setText(titular);
        modelo.setText("Modelo: "+infomodelo);
        totalCapas.setText("Total de Capas: "+capas);
        filamentoRequerido.setText("Filamento Requerido: "+ filamento +"g");
        tiempoImpresion.setText("Tiempo de Impresion: "+ tiempo);

        Font fuenteDatos = new Font(modelo.getFont().getName(), Font.PLAIN, 12);
        Font fuenteTitular = new Font (modelo.getFont().getName(), Font.BOLD, 14);

        nombreUser.setFont(fuenteTitular);
        modelo.setFont(fuenteDatos);
        totalCapas.setFont(fuenteDatos);
        filamentoRequerido.setFont(fuenteDatos);
        tiempoImpresion.setFont(fuenteDatos);


        try{
            java.net.URL urlPerfil = getClass().getResource("/imagenes/logusser.png");

            if (urlPerfil != null){
                ImageIcon iconoOriginal = new ImageIcon(urlPerfil);
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imgUser.setIcon(new ImageIcon(imgEscalada));
                imgUser.setText("");
            }

        } catch (Exception e) {
            System.out.println("Error al obtener imagenes de usuario");
        }
    }

    public JButton getBtnOk() {return btnOk;}
    public JButton getBtnCancel() {return btnCancel;}
    public JPanel getSolicitudAlumno() {return SolicitudAlumno;}
}
