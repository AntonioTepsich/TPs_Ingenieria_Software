package anillo;

public class NodoSolo extends Nodo {
    private Object cargo;

    public NodoSolo(Object cargo) {
        this.cargo = cargo;
    }

    public Nodo add(Object cargo) {
        // cambio de NodoSolo a NodoCargado
        NodoCargado NodoActualizado = new NodoCargado(this.cargo);
        NodoActualizado.setNext(NodoActualizado);
        return NodoActualizado.add(cargo);
    }

    public Nodo remove() {
        return new NodoVacio();
    }

    public Nodo next() {
        return this;
    }

    public Object current() {
        return cargo;
    }
}
