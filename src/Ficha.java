public class Ficha {
    private int ladoA;
    private int ladoB;

    public Ficha(int ladoA, int ladoB) {
        if ((ladoA >= 0 && ladoA <= 6) && (ladoB >= 0 && ladoB <= 6)) {
            this.ladoA = ladoA;
            this.ladoB = ladoB;
        } else {
            throw new IllegalArgumentException("Valores fuera de rango [0, 6].");
        }
    }

    public int getLadoA() {
        return ladoA;
    }

    public int getLadoB() {
        return ladoB;
    }

    public int getPuntosTotales() {
        return (ladoA + ladoB);
    }

    public Ficha invertir() {
        return new Ficha(ladoB, ladoA);
    }

    @Override
    public String toString() {
        return "[" + ladoA + "|" + ladoB + "]";
    }
}