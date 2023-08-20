package overcook;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author angel
 */
public class Cola {
    
    private Nodo frente;
    private Nodo fin;
    private static Cola instancia;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public void encolar(Orden orden) {
        Nodo nuevoNodo = new Nodo(orden);
        if(frente == null) {
            frente = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.siguiente = nuevoNodo;
            fin = nuevoNodo;
        }
    }

    public Orden desencolar() {
        if(frente == null) {
            return null;
        }
        Orden orden = frente.orden;
        frente = frente.siguiente;
        return orden;
    }

    public int size() {
        int count = 0;
        Nodo temp = frente;
        while(temp != null) {
            count++;
            temp = temp.siguiente;
        }
        return count;
    }

    public Orden[] mostrarOrdenes() {
        Orden[] ordenes = new Orden[3];
        Nodo temp = frente;
        int index = 0;
        while(temp != null && index < 3) {
            ordenes[index] = temp.orden;
            temp = temp.siguiente;
            index++;
        }
        return ordenes;
    }
    
    public static Cola obtenerInstancia() {
        if (instancia == null) {
            instancia = new Cola();
        }
        return instancia;
    }
}
