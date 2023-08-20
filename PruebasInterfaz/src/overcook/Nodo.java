/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package overcook;

/**
 *
 * @author AMD
 */
public class Nodo {
    Orden orden;
    Nodo siguiente;

    public Nodo(Orden orden) {
        this.orden = orden;
        this.siguiente = null;
    }    

    public Orden getOrden() {
        return this.orden;
    }

    public Nodo getSiguiente() {
        return this.siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}
