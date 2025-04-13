package anillo;

public class NodoCargado extends Nodo {
    private Object cargo;
    private Nodo next;
    private Nodo prev;

    public NodoCargado(Object cargo) {
        this.cargo = cargo;

        next = this;
        prev = this;
    }

    public Nodo add(Nodo nuevoNodo) {
        NodoCargado nuevo = (NodoCargado) nuevoNodo;
        nuevo.next = this;
        nuevo.prev = prev;
        ((NodoCargado) prev).next = nuevo;
        prev = nuevo;
        return nuevo;
    }

    public Nodo remove() {

        // este if no lo pude sacar pero hablando con Julio nos dio a entender que este if podia ir.
        if (next == this) {
            return NodoNulo.getInstance();
        }

        ((NodoCargado) prev).next = next;
        ((NodoCargado) next).prev = prev;
        return next;
    }

    public Nodo next() {
        return next;
    }

    public Object current() {
        return cargo;
    }
}


