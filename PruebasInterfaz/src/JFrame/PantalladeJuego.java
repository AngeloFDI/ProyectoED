/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package JFrame;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;
import java.awt.event.*;
import overcook.*;

/**
 *
 * @author AMD
 */
public class PantalladeJuego extends javax.swing.JFrame {
    
    private int tiempoRestante = 300; // 5 minutos convertidos a segundos

    /**
     * Creates new form PantalladeJuego
     */
    public PantalladeJuego() {
        initComponents();
        
        ImageIcon imagenBasurero = new ImageIcon(getClass().getResource("/Imagenes/basurero.png"));
        ImageIcon imagenPlato = new ImageIcon(getClass().getResource("/Imagenes/plato.png"));
        ImageIcon imagenCampana = new ImageIcon(getClass().getResource("/Imagenes/campana.png"));
        
        ImageIcon scaledImageBasura = new ImageIcon(ajusteImagenes(imagenBasurero));
        ImageIcon scaledImagePlato = new ImageIcon(ajusteImagenes(imagenPlato));
        ImageIcon scaledImageCampana = new ImageIcon(ajusteImagenes(imagenCampana));
        

        Basurero.setIcon(scaledImageBasura);
        Plato.setIcon(scaledImagePlato);
        CampanaEntrega.setIcon(scaledImageCampana);
        timer.start();
        timerOrden.start();
        
    }
    
    public Image ajusteImagenes(ImageIcon imagen){
    
       int width = 75;  // Ancho deseado
       int height = 75; // Alto deseado
       Image originalImage = imagen.getImage();
       Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH); 
       return scaledImage;
    
    }
    
    // Timer para actualizar la interfaz
        int delay = 1000; // 1 segundo
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                tiempoRestante--;
                int minutos = tiempoRestante / 60;
                int segundos = tiempoRestante % 60;
                timeLabel.setText(String.format("Tiempo: %02d:%02d", minutos, segundos));
                if (tiempoRestante <= 0) {
                    ((Timer) evt.getSource()).stop(); // Detener el Timer cuando el tiempo llegue a cero
                    JOptionPane.showMessageDialog(null, "¡Tiempo terminado! Puntaje: " + 0);
                }
                /*if (cinta.contarIngredientes() <= 3) {
                    cinta.agregarIngredientesAleatorios();
                    actualizarCinta();
                }  */  
            }
        });
        
    private Orden generarOrdenAleatoria() {
        int random = new Random().nextInt(3);
        switch(random) {
            case 0:
                return new Orden("Hamburguesadecarne", new String[]{"pan", "carne"}, 5);
            case 1:
                return new Orden("Hamburguesaconqueso", new String[]{"pan", "carne", "queso"}, 10);
            default:
                return new Orden("Hamburguesaclasica", new String[]{"pan", "carne", "lechuga", "queso"}, 15);
        }
    }
    
    int delayOrden = 20000; // 20 segundos
        Timer timerOrden = new Timer(delayOrden, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (Cola.obtenerInstancia().size() < 3) {
                    Cola.obtenerInstancia().encolar(generarOrdenAleatoria());
                    actualizarOrdenes();
                }
                timerOrden.start();
            }
        });
    
    private void actualizarOrdenes() {
        Orden[] ordenesActuales = Cola.obtenerInstancia().mostrarOrdenes();
        if (Orden1.getIcon() == null) {
            if (ordenesActuales[0] != null) {
                //Orden1.setText(ordenesActuales[0].getNombre());
                cargarImagenLabel(ordenesActuales[0].getNombre(), Orden1);
            }
        } else if (Orden2.getIcon() == null) {
            if (ordenesActuales[1] != null) {
                //Orden2.setText(ordenesActuales[1].getNombre());
                cargarImagenLabel(ordenesActuales[1].getNombre(), Orden2);
            }
        } else if (Orden3.getIcon() == null) {
            if (ordenesActuales[2] != null) {
                //Orden3.setText(ordenesActuales[2].getNombre());
                cargarImagenLabel(ordenesActuales[2].getNombre(), Orden3);
            }
        }
    }
    
    private void cargarImagenLabel(String nombreOrden, JLabel label) {
    ImageIcon imagen = new ImageIcon(getClass().getResource("/Imagenes/" + nombreOrden.toLowerCase() + ".png"));
    int width = 61;  // Ancho deseado
    int height = 61; // Alto deseado
    Image originalImage = imagen.getImage();
    Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
    label.setIcon(scaledImageIcon);
}
        
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        EspacioCinta1 = new javax.swing.JLabel();
        EspacioCinta5 = new javax.swing.JLabel();
        EspacioCinta2 = new javax.swing.JLabel();
        EspacioCinta3 = new javax.swing.JLabel();
        EspacioCinta4 = new javax.swing.JLabel();
        Basurero = new javax.swing.JLabel();
        Plato = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Orden1 = new javax.swing.JLabel();
        Orden2 = new javax.swing.JLabel();
        Orden3 = new javax.swing.JLabel();
        CampanaEntrega = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        EspacioCinta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/carne.png"))); // NOI18N

        EspacioCinta5.setText("jLabel1");

        EspacioCinta2.setText("jLabel1");

        EspacioCinta3.setText("jLabel1");

        EspacioCinta4.setText("jLabel1");

        Basurero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/basurero.png"))); // NOI18N

        timeLabel.setText("Tiempo: 5:00");

        jLabel3.setText("Puntaje: 0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EspacioCinta5, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EspacioCinta1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(EspacioCinta4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EspacioCinta3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EspacioCinta2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(CampanaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(Plato, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addComponent(Basurero, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(Orden1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(Orden2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(Orden3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeLabel)
                    .addComponent(jLabel3))
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Orden1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Orden2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Orden3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EspacioCinta4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EspacioCinta3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EspacioCinta2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EspacioCinta5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EspacioCinta1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Plato, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Basurero, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(CampanaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
   

// Asignar el icono de la imagen al JLabel EspacioCinta1
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantalladeJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantalladeJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantalladeJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantalladeJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantalladeJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Basurero;
    private javax.swing.JLabel CampanaEntrega;
    private javax.swing.JLabel EspacioCinta1;
    private javax.swing.JLabel EspacioCinta2;
    private javax.swing.JLabel EspacioCinta3;
    private javax.swing.JLabel EspacioCinta4;
    private javax.swing.JLabel EspacioCinta5;
    private javax.swing.JLabel Orden1;
    private javax.swing.JLabel Orden2;
    private javax.swing.JLabel Orden3;
    private javax.swing.JLabel Plato;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
