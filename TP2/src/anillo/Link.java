package anillo;

public abstract class Link {
    public abstract Link add(Link newLink);
    public abstract Link remove();
    public abstract Link next();
    public abstract Object current();
}

