package anillo;

public abstract class Nodo {
    public abstract Nodo add(Object cargo);
    public abstract Nodo remove();
    public abstract Nodo next();
    public abstract Object current();
}
