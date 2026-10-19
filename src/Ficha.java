public class Ficha {

    private int ladoA;
    private int ladoB;
    public Ficha(int ladoA, int ladoB)   {
        if((ladoA>=0 && ladoA<=6) && (ladoB>=0 && ladoB<=6)){
            this.ladoA = ladoA;
            this.ladoB = ladoB;
        }
    }

    public int getLadoA() { return ladoA;}
    public int getLadoB() { return ladoB;}

    public int getPuntosTotales() {
        return (ladoA + ladoB);
    }

    // Invierte la ficha para acomodarla a la cadena si es necesario
    public Ficha invertir() {
        Ficha invertida = new Ficha(ladoB, ladoA);
        return invertida;
    }

    @Override
    public String toString() {
        String cadenanueva = "["+ ladoA + "|" + ladoB + "]";
        return cadenanueva;
    }
}
