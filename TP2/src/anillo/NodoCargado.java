package anillo;

public class NodoCargado extends Nodo {
    private Object cargo;
    private Nodo next;

    public NodoCargado(Object cargo) {
        this.cargo = cargo;
    }

    public void setNext(Nodo next) {
        this.next = next;
    }

    public Nodo add(Object nuevoCargo) {
        NodoCargado nuevoNodo = new NodoCargado(nuevoCargo);
        nuevoNodo.setNext(this.next);
        this.next = nuevoNodo;
        // Intercambiamos los cargos
        Object temp = this.cargo;
        this.cargo = nuevoCargo;
        nuevoNodo.cargo = temp;
        return this;
    }

    public Nodo remove() {
        NodoCargado anterior = this;
        while (anterior.next != this) {
            anterior = (NodoCargado) anterior.next;
        }
        anterior.next = this.next;

        return this.next;
    }

    public Nodo next() {
        return next;
    }

    public Object current() {
        return cargo;
    }
}
