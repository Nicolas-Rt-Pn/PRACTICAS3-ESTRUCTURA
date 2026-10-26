
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class JuegoDomino {
    private final Stack<Ficha> pozo;
    private final Deque<Ficha> cadena;
    private final Jugador[] jugadores;
    private final int NJUGADORES;
    private final int NFICHAS;
    private int turno;

    public JuegoDomino (String nombresJugadores[], int nFichas) {
        this.NFICHAS = nFichas;
        this.NJUGADORES = nombresJugadores.length;
        Jugador[] jugadores = new Jugador[nombresJugadores.length];
        int pos = 0;
        for (String n : nombresJugadores){
            Jugador jugador = new Jugador(n);
            jugadores[pos] =jugador;
            pos++;
        }
        this.jugadores = jugadores;

    }
        
    public JuegoDomino (String nombresJugadores[]) {
        this(nombresJugadores, 7);
    }

    public void prepararPartida () {
        crearYMezclarFichas();
        repartirFichasIniciales();
        turno=0;
    }
    public void crearYMezclarFichas() {
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                pozo.add(new Ficha(i, j));
            }
        }
        Collections.shuffle(pozo);
    }

    private void repartirFichasIniciales() {
        crearYMezclarFichas();
        for(Jugador jugador : jugadores) {
            for (int i = 0; i < this.NFICHAS; i++) {
                Ficha entregada = pozo.peek();
                pozo.pop();
                jugador.recibirFicha(entregada);
            }
        }
    }

    public void jugar() {
        System.out.println("--- ESTADO INICIAL ---");
        for (int j=0; j<NJUGADORES; j++)
            System.out.println(jugadores[j]);

        System.out.println("Fichas en pozo (durmientes): " + pozo.size());
        System.out.println("----------------------\n");

        // Jugador 1 empieza colocando su primera ficha boca arriba
        Ficha primeraFicha = jugadores[turno].getFichas().poll();
        cadena.add(primeraFicha);
        System.out.println("\n--- TURNO DE " + jugadores[turno].getNombre().toUpperCase() + " ---");
        System.out.println(jugadores[turno].getNombre() + " inicia la cadena con la ficha " + primeraFicha);
        mostrarCadena();

        this.turno = (this.turno+1)%this.NJUGADORES; //pasa al siguiente jugador
        Jugador actual = this.jugadores[turno];
        int pasesConsecutivos = 0;

        boolean finJuego = false;
        while (!finJuego) {
            System.out.println("\n--- TURNO DE " + actual.getNombre().toUpperCase() + " ---");
            boolean colocoFicha = jugarTurno(actual);

            if (!actual.tieneFichas()) {
                System.out.println("\n¡" + actual.getNombre() + " se ha quedado sin fichas!");
                finJuego = true;
            }
            else {
               if (colocoFicha) {
                  pasesConsecutivos = 0;
               } else {
                   pasesConsecutivos++;
                   System.out.println(actual.getNombre() + " NO ha podido colocar ninguna ficha y pasa.");
                   if (pasesConsecutivos == 2) {
                       System.out.println("\n¡Ambos jugadores han pasado consecutivamente! El juego está cerrado.");
                       finJuego = true;
                   }
               }
            }

            mostrarCadena();

            if (!finJuego) {
                // Cambiar turno
                this.turno= (this.turno+1)%NJUGADORES;
                actual = this.jugadores[turno];
            }
        }
        determinarGanador();
    }

    public Jugador getJugadorActual(){
        return jugadores[turno];
    }

   
    public boolean jugarTurno(Jugador jugador) {
        return true;
    }

    public boolean intentarColocar(Ficha f) {

        int izq = f.getLadoA();
        int derch = f.getLadoB();
        int valor1 = cadena.getFirst().getLadoA();
        int valor2 = cadena.getLast().getLadoB();
        if(valor2!=derch && valor1!=izq) return false;
        else if (valor2 == derch) {
            if(valor1 == izq){
                if(valor1<valor2){
                    colocarEnIzquierda(f, valor1);
                }

            }
            colocarEnDerecha(f, valor2);
        }
        colocarEnIzquierda(f, valor1);
        return true;
    }

    public void colocarEnIzquierda(Ficha f, int valorExtremo) {

        
    }

    public void colocarEnDerecha(Ficha f, int valorExtremo) {
      
    }

    public void mostrarCadena() {
        StringBuilder sb = new StringBuilder("CADENA ACTUAL: ");
        for (Ficha f : cadena) {
            sb.append(f.toString()).append(" ");
        }
        System.out.println(sb.toString());
    }

    public Jugador determinarGanador() {
       
        return null;
    }

    public Jugador[] getJugadores() {
        return this.jugadores;
    }
    public int fichasEnPozo() {
        return this.pozo.size();
    }
    public Object[] getCadena() {
        return this.cadena.toArray();
    }
}
