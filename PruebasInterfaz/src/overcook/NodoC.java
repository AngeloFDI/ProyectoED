/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package overcook;

/**
 *
 * @author angel
 */
public class NodoC {
    
    private Cinta dato;
    private NodoC next;
    
    @Override
    public String toString() {
        return "Nodo{" + "dato=" + dato + '}';
    }
    
    public NodoC(Cinta dato) {
        this.dato = dato;
    }

    public Cinta getDato() {
        return dato;
    }

    public void setDato(Cinta dato) {
        this.dato = dato;
    }

    public NodoC getNext() {
        return next;
    }

    public void setNext(NodoC next) {
        this.next = next;
    }
}