/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package overcook;

public class Lista {
    private NodoC cabeza;
    private NodoC ultimo;
    
    public void inserta(Cinta p){
        if(cabeza==null){
            cabeza = new NodoC(p);
        }else if(p.getId()<cabeza.getDato().getId()){
            NodoC aux = new NodoC(p);
            aux.setNext(cabeza);
            cabeza=aux;
        }else if(cabeza.getNext()==null){
            cabeza.setNext(new NodoC(p));
        }else{
           NodoC aux = cabeza;
           while (aux.getNext() != null &&
                   aux.getNext().getDato().getId()
                   <p.getId()){
               aux=aux.getNext();
           }
           NodoC temp = new NodoC(p);
           temp.setNext(aux.getNext());
           aux.setNext(temp);
        } 
    }
    
    public void insertaCircular(Cinta p){
        if(cabeza==null){
            cabeza = new NodoC(p);
            ultimo = cabeza;
        }else if(p.getId()<cabeza.getDato().getId()){
            NodoC aux = new NodoC(p);
            aux.setNext(cabeza);
            cabeza=aux;
        }else if(ultimo.getDato().getId() <= p.getId()){
            ultimo.setNext(new NodoC(p));
            ultimo = ultimo.getNext();
        }else{
           NodoC aux = cabeza;
           while (aux.getNext() != null &&
                   aux.getNext().getDato().getId()
                   <p.getId()){
               aux=aux.getNext();
           }
           NodoC temp = new NodoC(p);
           temp.setNext(aux.getNext());
           aux.setNext(temp);
        } 
        ultimo.setNext(cabeza);
    }
    
    public boolean existe (int id){
        boolean esta = false;
        //Busca en lista, y retorna true si una persona tiene el id
        //en la lista
        if (cabeza != null){
            //Si hay algo en la lista buscaré
            NodoC aux = cabeza;
            //utilizo aux como indice

            //Mientras no se acabe la lista y el elemento
            //de la lista sea menor que el buscado
            while (aux != null && aux.getDato().getId() < id){
                aux = aux.getNext () ;//avanzo en la lista
            }

            //verdadero si lo encontró
            esta = (aux != null && aux.getDato().getId()== id);
        }

        return esta;
    }
    
    public void modifica (Cinta p) {
        //busca a si existe alguien con el id, y le actualiza el nombre
        if (cabeza != null) {
            //Si hay algo en la lista buscaré
            NodoC aux = cabeza; //utilizo aux como indice
            //Mientras no se acabe la lista y el elemento
            //de la lista sea menor que el buscado
            while (aux != null && aux.getDato().getId() < p.getId()) {
                aux = aux. getNext (); //avanzo en la lista
            }
            // Si lo encuentra hago el cambio
            if (aux != null && aux. getDato () .getId () == p.getId ()){
                //Solo básta cambiar nombre
                aux.getDato().setIngrediente(p.getIngrediente()) ;
            }
        }
    } 
    public void elimina (int id) {
        //Si una persona tiene el id, lo elimina
        if (cabeza != null) { //Si hay algo en la lista buscaré
            if (cabeza.getDato().getId() == id) 
            {
                cabeza = cabeza.getNext();
            } 
            else {
                NodoC aux = cabeza; //utilizo aux como indice
                //Mientras no se acabe la lista y el elemento
                //de la lista sea menor que el buscado
                while (aux. getNext () != null &&
                    aux.getNext () .getDato () .getId() < id) {
                    aux = aux.getNext () ;
                }
                //avanzo en la lista
            
                // si es el de adelante lo borro
                if (aux.getNext () != null &&
                aux.getNext () .getDato () .getId () == id) {
                    aux. setNext (aux.getNext () .getNext ()); //cambio las referencias
                }
            }
        }
    }
    
    public Cinta extrae (int id) {
        Cinta ingredienteExtraido = null;

        // Si la cabeza de la lista no es nula
        if (cabeza != null) {
            // Si el ingrediente a extraer es el primero en la lista
            if (cabeza.getDato().getId() == id) {
                ingredienteExtraido = cabeza.getDato();  // Guardar el ingrediente a extraer
                cabeza = cabeza.getNext();  // Avanzar la cabeza de la lista
            } else {
                NodoC aux = cabeza;

                // Buscar el ingrediente en la lista mientras avanzamos
                while (aux.getNext() != null && aux.getNext().getDato().getId() < id) {
                    aux = aux.getNext();
                }

                // Si encontramos el ingrediente en la lista, extraerlo
                if (aux.getNext() != null && aux.getNext().getDato().getId() == id) {
                    ingredienteExtraido = aux.getNext().getDato();  // Guardar el ingrediente a extraer
                    aux.setNext(aux.getNext().getNext());  // Eliminar el nodo de la lista
                }
            }
        }

        return ingredienteExtraido;
    }
    
    public Cinta[] mostrarCintas() {
    Cinta[] cintas = new Cinta[5]; // Cambiar el tamaño según el número de elementos en la lista
    NodoC temp = cabeza ;
    int index = 0;
    while (temp != null && index < 5) {
        cintas[index] = temp.getDato();
        temp = temp.getNext();
        index++;
    }
    return cintas;
}
    
    @Override
    public String toString(){
        NodoC aux = cabeza;
        String s="Lista: ";
        while(aux!=null){
            s+=aux+", ";
            aux=aux.getNext();
        }
        return s;
    }

    public int size() {
        int count = 0;
        NodoC temp = cabeza;
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }
}
