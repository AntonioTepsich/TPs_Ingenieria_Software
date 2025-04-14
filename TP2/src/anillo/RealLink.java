package anillo;

public class RealLink extends Link {
    private Object cargo;
    private Link next;
    private Link prev;

    public RealLink(Object cargo) {
        this.cargo = cargo;

        next = this;
        prev = this;
    }

    public Link add(Link newLink) {
        RealLink nuevo = (RealLink) newLink;
        nuevo.next = this;
        nuevo.prev = prev;
        ((RealLink) prev).next = nuevo;
        prev = nuevo;
        return nuevo;
    }

    public Link remove() {

        // este if no lo pude sacar pero hablando con Julio nos dio a entender que este if podía ir.
        if (next == this) {
            return NullLink.getInstance();
        }

        ((RealLink) prev).next = next;
        ((RealLink) next).prev = prev;
        return next;
    }

    public Link next() {
        return next;
    }

    public Object current() {
        return cargo;
    }
}


