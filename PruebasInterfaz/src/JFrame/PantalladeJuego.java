
package JFrame;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import overcook.*;


public class PantalladeJuego extends javax.swing.JFrame {
    
    private int tiempoRestante = 300; // 5 minutos convertidos a segundos
    private Lista cintaTransportadora = new Lista();
    private Cola colaOrdenes = new Cola();
    private static int IDCinta = 0;
    private String ingredienteSeleccionado = null;
    public String[] pedido = new String[4];
    public int idpedido = 0;
    public static int puntaje = 0;

    /**
     * Creates new form PantalladeJuego
     */
    public PantalladeJuego() {
        initComponents();
        
        Arrays.fill(pedido, "");
        
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
        timerIngrediente.start();
        
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
                    JOptionPane.showMessageDialog(null, "¡Tiempo terminado! Puntaje: " + puntaje);
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
                return new Orden("Hamburguesadecarne", new String[]{"Pan", "Carne"}, 5);
            case 1:
                return new Orden("Hamburguesaconqueso", new String[]{"Pan", "Carne", "Queso"}, 10);
            default:
                return new Orden("Hamburguesaclasica", new String[]{"Pan", "Carne", "Lechuga", "Queso"}, 15);
        }
    }
    
    private Cinta generarIngredienteAleatorio() {
        String[] opcionesIngredientes = { "Pan", "Queso", "Carne", "Lechuga" };
        Random random = new Random();
        int indiceAleatorio = random.nextInt(opcionesIngredientes.length);
        String ingrediente = opcionesIngredientes[indiceAleatorio];
        int idCinta = aumentarId();
        return new Cinta(idCinta, ingrediente);
    }
    
    private int aumentarId(){
        IDCinta = (IDCinta + 1) % 5; 
        return IDCinta;
    }
    
    int delayOrden = 20000; // 20 segundos
        Timer timerOrden = new Timer(delayOrden, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (colaOrdenes.size() < 3) {
                    colaOrdenes.encolar(generarOrdenAleatoria());
                    actualizarOrdenes();
                }
                timerOrden.start();
            }
        });
        
    int delayIngrediente = 10000; // 20 segundos
        Timer timerIngrediente = new Timer(delayIngrediente, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (cintaTransportadora.size() < 5) {
                    cintaTransportadora.inserta(generarIngredienteAleatorio());
                    actualizarCinta();
                }
                timerIngrediente.start();
            }
        });
    
    private void actualizarCinta(){
    
        Cinta[] ingredientesEnCinta = cintaTransportadora.mostrarCintas();

        Cinta temp = ingredientesEnCinta[ingredientesEnCinta.length - 1];
        for (int i = ingredientesEnCinta.length - 1; i > 0; i--) {
            ingredientesEnCinta[i] = ingredientesEnCinta[i - 1];
        }
        ingredientesEnCinta[0] = temp;

        JLabel[] labelsCinta = { EspacioCinta0, EspacioCinta1, EspacioCinta2, EspacioCinta3, EspacioCinta4 };

        for (int i = 0; i < ingredientesEnCinta.length; i++) {
            final int currentIndex = i;  // Crear una variable final para almacenar el índice

            if (ingredientesEnCinta[i] != null) {
                String nombreIngrediente = ingredientesEnCinta[i].getIngrediente();
                cargarImagenLabel(nombreIngrediente, labelsCinta[i]);

                // Asignar el ID de la cinta como propiedad del Label
                labelsCinta[i].putClientProperty("idCinta", ingredientesEnCinta[i].getId());

                // Agregar el mismo MouseListener a cada Label de la cinta
                labelsCinta[i].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        labelCintaMouseClicked(evt, labelsCinta[currentIndex]); // Usar la variable final
                    }
                });
            } else {
                labelsCinta[i].setIcon(null);
                // Limpiar la propiedad del ID de la cinta
                labelsCinta[i].putClientProperty("idCinta", null);

                // Eliminar el MouseListener si no hay ingrediente en el Label
                for (MouseListener listener : labelsCinta[i].getMouseListeners()) {
                    labelsCinta[i].removeMouseListener(listener);
                }
            }
        }
    }
    
    private void actualizarOrdenes() {
        Orden[] ordenesActuales = colaOrdenes.mostrarOrdenes();
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
        EspacioCinta0 = new javax.swing.JLabel();
        EspacioCinta4 = new javax.swing.JLabel();
        EspacioCinta1 = new javax.swing.JLabel();
        EspacioCinta2 = new javax.swing.JLabel();
        EspacioCinta3 = new javax.swing.JLabel();
        Basurero = new javax.swing.JLabel();
        Plato = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        PuntajeLabel = new javax.swing.JLabel();
        Orden1 = new javax.swing.JLabel();
        Orden2 = new javax.swing.JLabel();
        Orden3 = new javax.swing.JLabel();
        CampanaEntrega = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        EspacioCinta0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EspacioCinta0MouseClicked(evt);
            }
        });

        EspacioCinta4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EspacioCinta4MouseClicked(evt);
            }
        });

        EspacioCinta1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EspacioCinta1MouseClicked(evt);
            }
        });

        EspacioCinta2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EspacioCinta2MouseClicked(evt);
            }
        });

        EspacioCinta3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EspacioCinta3MouseClicked(evt);
            }
        });

        Basurero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/basurero.png"))); // NOI18N
        Basurero.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BasureroMouseClicked(evt);
            }
        });

        Plato.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PlatoMouseClicked(evt);
            }
        });

        timeLabel.setText("Tiempo: 5:00");

        PuntajeLabel.setText("Puntaje: 0");

        CampanaEntrega.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CampanaEntregaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PuntajeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EspacioCinta4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EspacioCinta0, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(EspacioCinta3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EspacioCinta2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EspacioCinta1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(PuntajeLabel))
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Orden1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Orden2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Orden3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EspacioCinta3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EspacioCinta2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EspacioCinta1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EspacioCinta4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EspacioCinta0, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void EspacioCinta0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EspacioCinta0MouseClicked
        // TODO add your handling code here:
        labelCintaMouseClicked(evt, EspacioCinta0);
    }//GEN-LAST:event_EspacioCinta0MouseClicked

    private void EspacioCinta1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EspacioCinta1MouseClicked
        // TODO add your handling code here:
        labelCintaMouseClicked(evt, EspacioCinta1);
    }//GEN-LAST:event_EspacioCinta1MouseClicked

    private void EspacioCinta2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EspacioCinta2MouseClicked
        // TODO add your handling code here:
        labelCintaMouseClicked(evt, EspacioCinta2);
    }//GEN-LAST:event_EspacioCinta2MouseClicked

    private void EspacioCinta3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EspacioCinta3MouseClicked
        // TODO add your handling code here:
        labelCintaMouseClicked(evt, EspacioCinta3);
    }//GEN-LAST:event_EspacioCinta3MouseClicked

    private void EspacioCinta4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EspacioCinta4MouseClicked
        // TODO add your handling code here:
        labelCintaMouseClicked(evt, EspacioCinta4);
    }//GEN-LAST:event_EspacioCinta4MouseClicked

    private void labelCintaMouseClicked(java.awt.event.MouseEvent evt, javax.swing.JLabel label) {                                           
        int idCinta = (int) label.getClientProperty("idCinta");
    
        if (ingredienteSeleccionado == null) {
            Cinta ingrediente = cintaTransportadora.extrae(idCinta);

            if (ingrediente != null) {
                ingredienteSeleccionado = ingrediente.getIngrediente();
                actualizarCinta();
            }
        }
    }
    
    private void PlatoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlatoMouseClicked
        // TODO add your handling code here:
        if (idpedido < 5){
            if (ingredienteSeleccionado != null){
                pedido[idpedido] = ingredienteSeleccionado;
                JOptionPane.showMessageDialog(rootPane, "Ingrediente añadido con éxito");
                ingredienteSeleccionado = null;
                idpedido++;
            }else{
            JOptionPane.showMessageDialog(rootPane, "Número de ingrdientes máximo alcanzado, limpiando ingredientes...");
            idpedido = 0;
            pedido[0] = "";
            pedido[1] = "";
            pedido[2] = "";
            pedido[3] = "";
            }
        } 
        
    }//GEN-LAST:event_PlatoMouseClicked

    private void BasureroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BasureroMouseClicked
        // TODO add your handling code here:
        ingredienteSeleccionado = null;
        JOptionPane.showMessageDialog(rootPane, "Ingrediente Botado con éxito");
    }//GEN-LAST:event_BasureroMouseClicked

    private void CampanaEntregaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CampanaEntregaMouseClicked
        // TODO add your handling code here:
        int ingredientesSeleccionados = 0;
        for (String ingrediente : pedido) {
            if (ingrediente != null) {
                ingredientesSeleccionados++;
            }
        }

        if (ingredientesSeleccionados < 2) {
            JOptionPane.showMessageDialog(rootPane, "Selecciona al menos 2 ingredientes en el pedido.");
            return;
        }

        // Resto del código para manejar la entrega de la orden
        Orden[] ordenesActuales = colaOrdenes.mostrarOrdenes();
        for (Orden orden : ordenesActuales) {
            if (compararIngredientes(orden.getIngredientes(), pedido)){
                puntaje = puntaje + orden.getPuntos();
                PuntajeLabel.setText("Puntaje: " + puntaje);
                
                actualizarOrdenes();
                return;
            }
        }
        JOptionPane.showMessageDialog(rootPane, "Orden no válida");
    }//GEN-LAST:event_CampanaEntregaMouseClicked

    public static boolean compararIngredientes(String[] ingredientesOrden, String[] ingredientesPlato) {
        Set<String> setIngredientesOrden = new HashSet<>(Arrays.asList(ingredientesOrden));
        Set<String> setIngredientesPlato = new HashSet<>(Arrays.asList(ingredientesPlato));
        return setIngredientesOrden.equals(setIngredientesPlato);
    }
    
    
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
    private javax.swing.JLabel EspacioCinta0;
    private javax.swing.JLabel EspacioCinta1;
    private javax.swing.JLabel EspacioCinta2;
    private javax.swing.JLabel EspacioCinta3;
    private javax.swing.JLabel EspacioCinta4;
    private javax.swing.JLabel Orden1;
    private javax.swing.JLabel Orden2;
    private javax.swing.JLabel Orden3;
    private javax.swing.JLabel Plato;
    private javax.swing.JLabel PuntajeLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
