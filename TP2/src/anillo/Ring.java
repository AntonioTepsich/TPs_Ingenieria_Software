package anillo;

public class Ring {
    private Nodo current;

    public Ring() {
        current = NodoNulo.getInstance();
    }

    public Ring add(Object cargo) {
        Nodo nuevo = new NodoCargado(cargo);
        current = current.add(nuevo);
        return this;
    }

    public Ring next() {
        current = current.next();
        return this;
    }

    public Ring remove() {
        current = current.remove();
        return this;
    }

    public Object current() {
        return current.current();
    }
}


