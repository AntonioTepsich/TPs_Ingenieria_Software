package anillo;

public class Ring {
    private Link current;

    public Ring() {
        current = NullLink.getInstance();
    }

    public Ring add(Object cargo) {
        Link nuevo = new RealLink(cargo);
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


