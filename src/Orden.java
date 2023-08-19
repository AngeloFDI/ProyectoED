/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author angel
 */
public class Orden {
    String nombre;
    String[] ingredientes;
    int puntos;

    public Orden(String nombre, String[] ingredientes, int puntos) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.puntos = puntos;
    }    

    public String getNombre() {
        return this.nombre;
    }

    public String[] getIngredientes() {
        return this.ingredientes;
    }

    public int getPuntos() {
        return this.puntos;
    }
}
