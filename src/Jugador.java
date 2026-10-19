

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Jugador {
    //parte privada
    private Queue<Ficha> coleccionf;
    private String nombre;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public Queue<Ficha> getFichas() {
       return coleccionf;
    }

    public void recibirFicha(Ficha f) {
        coleccionf.add(f);
    }

    public Ficha buscarFicha(int valor1, int valor2) {
        int evaluadas = 0;
        while (evaluadas < coleccionf.size()){
            Ficha evaluada = coleccionf.element();
            coleccionf.remove();
            if(evaluada.getLadoA() == valor1 || evaluada.getLadoB() == valor2){
                return evaluada;
            }
            evaluadas++;
        }
        return null;
    }


    public boolean tieneFichas() {
        if (coleccionf.isEmpty()) return false;
        return true;
    }

    public int calcularPuntosRestantes() {
        int suma = 0;
        if(tieneFichas()){
            for(Ficha f : coleccionf){
                suma+= f.getPuntosTotales();
            }
        }
        return suma;
    }

    @Override
    public String toString() {
        String jugadorinfo = nombre + " : " + coleccionf.size();
        return jugadorinfo;
    }
}
