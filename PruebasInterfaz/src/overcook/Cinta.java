/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package overcook;

/**
 *
 * @author angel
 */
public class Cinta{
    private int idCinta;
    private String ingrediente;

    public Cinta(int idCinta, String ingrediente) {
        this.idCinta = idCinta;
        this.ingrediente = ingrediente;
    }

    public int getId() {
        return idCinta;
    }

    public void setIdCinta(int idCinta) {
        this.idCinta = idCinta;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    @Override
    public String toString() {
        return "Cinta{" + "idCinta=" + idCinta +
               ", ingrediente=" + ingrediente + '}';
    }
}