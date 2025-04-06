package anillo;

public class Ring {
    private Nodo nodo;

    public Ring() {
        nodo = new NodoVacio();
    }

    public Ring next() {
        nodo = nodo.next();
        return this;
    }

    public Object current() {
        return nodo.current();
    }

    public Ring add(Object cargo) {
        nodo = nodo.add(cargo);
        return this;
    }

    public Ring remove() {
        nodo = nodo.remove();
        return this;
    }
}


