
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author angel
 */
public class Main {
    // Variables globales
    private JFrame frame;
    private JLabel timeLabel;
    private JLabel scoreLabel;
    private JLabel[] conveyorLabels;
    private JLabel[] orderLabels;
    private CintaTransportadora cinta = new CintaTransportadora();
    private ColaOrdenes colaOrdenes = new ColaOrdenes();
    private int puntaje = 0;
    private ArrayList<String> ingredientesSeleccionados = new ArrayList<String>();
    private int tiempoRestante = 300; // 5 minutos convertidos a segundos


    public Main() {
        // Inicializar la interfaz y configurar el juego
        frame = new JFrame("Juego de Hamburguesa");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel de Información
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        timeLabel = new JLabel("Tiempo: 5:00");
        scoreLabel = new JLabel("Puntaje: 0");
        infoPanel.add(timeLabel);
        infoPanel.add(scoreLabel);
        frame.add(infoPanel, BorderLayout.NORTH);

        // Panel de Órdenes
        JPanel ordersPanel = new JPanel(new GridLayout(1, 3));
        orderLabels = new JLabel[] {new JLabel(), new JLabel(), new JLabel()};
        for (JLabel label : orderLabels) {
            ordersPanel.add(label);
        }
        frame.add(ordersPanel, BorderLayout.CENTER);

        // Cinta Transportadora
        JPanel conveyorPanel = new JPanel(new GridLayout(1, 5));
        conveyorLabels = new JLabel[] {new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()};
        for (JLabel label : conveyorLabels) {
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String ingrediente = cinta.getIngrediente();
                    ingredientesSeleccionados.add(ingrediente);
                    verificarOrden();
                    actualizarCinta();
                }
            });
            conveyorPanel.add(label);
        }
        frame.add(conveyorPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        
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
                    JOptionPane.showMessageDialog(frame, "¡Tiempo terminado! Puntaje: " + puntaje);
                }
                if (cinta.contarIngredientes() <= 3) {
                    cinta.agregarIngredientesAleatorios();
                    actualizarCinta();
                }    
            }
        });
        timer.start();
        
        int delayOrden = 20000; // 20 segundos
        Timer timerOrden = new Timer(delayOrden, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (colaOrdenes.size() < 3) {
                    colaOrdenes.encolar(generarOrdenAleatoria());
                    actualizarOrdenes();
                }
            }
        });
        timerOrden.start();    
    }
    
    private Orden generarOrdenAleatoria() {
        int random = new Random().nextInt(3);
        switch(random) {
            case 0:
                return new Orden("Hamburguesa de carne", new String[]{"pan", "carne"}, 5);
            case 1:
                return new Orden("Hamburguesa con queso", new String[]{"pan", "carne", "queso"}, 10);
            default:
                return new Orden("Hamburguesa clásica", new String[]{"pan", "carne", "lechuga", "queso"}, 15);
        }
    }
    
    private void verificarOrden() {
        Orden[] ordenesActuales = colaOrdenes.mostrarOrdenes();
        for (Orden orden : ordenesActuales) {
            if (orden != null && Arrays.equals(orden.ingredientes, ingredientesSeleccionados.toArray())) {
                puntaje += orden.puntos;
                scoreLabel.setText("Puntaje: " + puntaje);
                colaOrdenes.desencolar();
                ingredientesSeleccionados.clear();
                actualizarOrdenes();
                break;
            }
        }
    }

    private void actualizarOrdenes() {
        Orden[] ordenesActuales = colaOrdenes.mostrarOrdenes();
        for (int i = 0; i < orderLabels.length; i++) {
            if (ordenesActuales[i] != null) {
                orderLabels[i].setText(ordenesActuales[i].nombre);
            } else {
                orderLabels[i].setText("");
            }
        }
    }

    private void actualizarCinta() {
        String[] ingredientes = cinta.mostrarCinta();
        for (int i = 0; i < conveyorLabels.length; i++) {
            conveyorLabels[i].setText(ingredientes[i]);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }
}