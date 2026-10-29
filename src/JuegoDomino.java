import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class JuegoDomino {
    private final Stack<Ficha> pozo;
    private final Deque<Ficha> cadena;
    private final Jugador[] jugadores;
    private final int NFICHAS;
    private final int NJUGADORES;
    private int turno;

    public JuegoDomino(String nombresJugadores[], int nFichas) {
        if (nombresJugadores == null || nombresJugadores.length < 2 || nombresJugadores.length > 4) {
            throw new IllegalArgumentException("El número de jugadores debe estar entre 2 y 4.");
        }
        this.NFICHAS = nFichas;
        this.NJUGADORES = nombresJugadores.length;
        this.pozo = new Stack<>();
        this.cadena = new LinkedList<>();

        Jugador[] jugadoresArray = new Jugador[this.NJUGADORES];
        for (int i = 0; i < this.NJUGADORES; i++) {
            jugadoresArray[i] = new Jugador(nombresJugadores[i]);
        }
        this.jugadores = jugadoresArray;
        this.turno = 0;
    }

    public JuegoDomino(String nombresJugadores[]) {
        this(nombresJugadores, 7);
    }

    public void prepararPartida() {
        pozo.clear();
        cadena.clear();
        crearYMezclarFichas();
        repartirFichasIniciales();
        turno = 0;
    }

    public void crearYMezclarFichas() {
        pozo.clear();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                pozo.add(new Ficha(i, j));
            }
        }
        Collections.shuffle(pozo);
    }

    private void repartirFichasIniciales() {
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < this.NFICHAS; i++) {
                if (!pozo.isEmpty()) {
                    jugador.recibirFicha(pozo.pop());
                }
            }
        }
    }

    public void jugar() {
        System.out.println("--- ESTADO INICIAL ---");
        for (int j = 0; j < NJUGADORES; j++)
            System.out.println(jugadores[j]);

        System.out.println("Fichas en pozo: " + pozo.size());
        System.out.println("----------------------\n");

        Ficha primeraFicha = jugadores[turno].getFichas().poll();
        cadena.add(primeraFicha);
        System.out.println("\n--- TURNO DE " + jugadores[turno].getNombre().toUpperCase() + " ---");
        System.out.println(jugadores[turno].getNombre() + " inicia la cadena con " + primeraFicha);
        mostrarCadena();

        this.turno = (this.turno + 1) % this.NJUGADORES;
        int pasesConsecutivos = 0;
        boolean finJuego = false;

        while (!finJuego) {
            Jugador actual = jugadores[turno];
            System.out.println("\n--- TURNO DE " + actual.getNombre().toUpperCase() + " ---");
            boolean colocoFicha = jugarTurno(actual);

            if (!actual.tieneFichas()) {
                System.out.println("\n¡" + actual.getNombre() + " se ha quedado sin fichas!");
                finJuego = true;
            } else {
                if (colocoFicha) {
                    pasesConsecutivos = 0;
                } else {
                    pasesConsecutivos++;
                    System.out.println(actual.getNombre() + " pasa.");
                    if (pasesConsecutivos >= NJUGADORES) {
                        System.out.println("\n¡Juego bloqueado!");
                        finJuego = true;
                    }
                }
            }

            mostrarCadena();

            if (!finJuego) {
                this.turno = (this.turno + 1) % NJUGADORES;
            }
        }
        determinarGanador();
    }

    public Jugador getJugadorActual() {
        return jugadores[turno];
    }

    public boolean jugarTurno(Jugador jugador) {
        int izq = cadena.getFirst().getLadoA();
        int der = cadena.getLast().getLadoB();

        Ficha fichaMano = jugador.buscarFicha(izq, der);
        if (fichaMano != null) {
            return intentarColocar(fichaMano);
        }

        while (!pozo.isEmpty()) {
            Ficha robada = pozo.pop();
            if (robada.getLadoA() == izq || robada.getLadoA() == der ||
                    robada.getLadoB() == izq || robada.getLadoB() == der) {
                return intentarColocar(robada);
            } else {
                jugador.recibirFicha(robada);
            }
        }
        return false;
    }

    public boolean intentarColocar(Ficha f) {
        int izq = cadena.getFirst().getLadoA();
        int der = cadena.getLast().getLadoB();

        boolean encajaIzq = (f.getLadoA() == izq || f.getLadoB() == izq);
        boolean encajaDer = (f.getLadoA() == der || f.getLadoB() == der);

        if (!encajaIzq && !encajaDer) return false;

        if (encajaIzq && encajaDer) {
            if (f.getPuntosTotales() <= 6) {
                colocarEnIzquierda(f, izq);
            } else {
                colocarEnDerecha(f, der);
            }
        } else if (encajaIzq) {
            colocarEnIzquierda(f, izq);
        } else {
            colocarEnDerecha(f, der);
        }
        return true;
    }

    public void colocarEnIzquierda(Ficha f, int valor) {
        if (f.getLadoB() != valor) {
            f = f.invertir();
        }
        cadena.addFirst(f);
    }

    public void colocarEnDerecha(Ficha f, int valor) {
        if (f.getLadoA() != valor) {
            f = f.invertir();
        }
        cadena.addLast(f);
    }

    public void mostrarCadena() {
        StringBuilder sb = new StringBuilder("CADENA ACTUAL: ");
        for (Ficha f : cadena) {
            sb.append(f.toString()).append(" ");
        }
        System.out.println(sb.toString());
    }

    public Jugador determinarGanador() {
        Jugador ganador = null;
        int minPuntos = Integer.MAX_VALUE;
        int minFichas = Integer.MAX_VALUE;
        boolean empate = false;

        for (Jugador j : jugadores) {
            int puntos = j.calcularPuntosRestantes();
            int numFichas = j.getFichas().size();

            if (puntos < minPuntos) {
                minPuntos = puntos;
                minFichas = numFichas;
                ganador = j;
                empate = false;
            } else if (puntos == minPuntos) {
                if (numFichas < minFichas) {
                    minFichas = numFichas;
                    ganador = j;
                    empate = false;
                } else if (numFichas == minFichas) {
                    empate = true;
                }
            }
        }

        if (empate) return null;
        return ganador;
    }

    public Jugador[] getJugadores() {
        return this.jugadores;
    }

    public int fichasEnPozo() {
        return this.pozo.size();
    }

    public Stack<Ficha> getPozo() {
        return this.pozo;
    }

    public Object[] getCadena() {
        return this.cadena.toArray();
    }
}