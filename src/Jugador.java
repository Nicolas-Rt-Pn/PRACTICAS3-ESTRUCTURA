import java.util.LinkedList;
import java.util.Queue;

public class Jugador {
    private Queue<Ficha> coleccionf;
    private String nombre;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.coleccionf = new LinkedList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Queue<Ficha> getFichas() {
        return coleccionf;
    }

    public void recibirFicha(Ficha f) {
        coleccionf.add(f);
    }

    public boolean tieneFichas() {
        return !coleccionf.isEmpty();
    }

    public int calcularPuntosRestantes() {
        int suma = 0;
        for (Ficha f : coleccionf) {
            suma += f.getPuntosTotales();
        }
        return suma;
    }

    public Ficha buscarFicha(int valor1, int valor2) {
        int tam = coleccionf.size();
        for (int i = 0; i < tam; i++) {
            Ficha evaluada = coleccionf.poll();
            if (evaluada.getLadoA() == valor1 || evaluada.getLadoA() == valor2 ||
                    evaluada.getLadoB() == valor1 || evaluada.getLadoB() == valor2) {
                return evaluada;
            } else {
                coleccionf.add(evaluada);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if (coleccionf.isEmpty()) {
            return nombre + " []";
        }
        return nombre + " : " + coleccionf.size();
    }
}