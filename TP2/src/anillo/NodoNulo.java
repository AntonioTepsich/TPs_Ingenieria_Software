package anillo;

public class NodoNulo extends Nodo {
    private static final NodoNulo INSTANCE = new NodoNulo();

    public static NodoNulo getInstance() {
        return INSTANCE;
    }

    public Nodo add(Nodo nuevoNodo) {
        return nuevoNodo;
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
