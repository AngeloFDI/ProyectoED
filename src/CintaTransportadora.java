
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author angel
 */
public class CintaTransportadora {
    private String[] ingredientes = {"pan", "carne", "queso", "lechuga"};
    private String[] cinta = new String[5];
    private int inicio = 0;
    private Random random = new Random();

    public CintaTransportadora() {
        for(int i = 0; i < 5; i++) {
            cinta[i] = ingredientes[random.nextInt(ingredientes.length)];
        }
    }

    public String getIngrediente() {
        String ingrediente = cinta[inicio];
        cinta[inicio] = null;
        moverCinta();
        return ingrediente;
    }

    private void moverCinta() {
        inicio = (inicio + 1) % 5;
        if(contarIngredientes() <= 3) {
            rellenarCinta();
        }
    }

    int contarIngredientes() {
        int count = 0;
        for(int i = 0; i < 5; i++) {
            if(cinta[i] != null) {
                count++;
            }
        }
        return count;
    }

    private void rellenarCinta() {
        for(int i = 0; i < 5; i++) {
            if(cinta[i] == null) {
                cinta[i] = ingredientes[random.nextInt(ingredientes.length)];
            }
        }
    }

    public void agregarIngredientesAleatorios() {
        while(contarIngredientes() < 5) {
            int posicion = random.nextInt(5);
            if(cinta[posicion] == null) {
                cinta[posicion] = ingredientes[random.nextInt(ingredientes.length)];
            }
        }
    }

    public String[] mostrarCinta() {
        return cinta;
    }
}
