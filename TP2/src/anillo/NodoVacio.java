package anillo;

public class NodoVacio extends Nodo {
    public Nodo add(Object cargo) {
        return new NodoSolo(cargo);
    }

    public Nodo remove() {
        throw new RuntimeException();
    }

    public Nodo next() {
        throw new RuntimeException();
    }

    public Object current() {
        throw new RuntimeException();
    }
}
