package anillo;

public class NullLink extends Link {
    private static final NullLink INSTANCE = new NullLink();

    public static NullLink getInstance() {
        return INSTANCE;
    }

    public Link add(Link newLink) { return newLink; }

    public Link remove() {
        throw new RuntimeException("No se puede eliminar un eslabón de un anillo vacío");
    }

    public Link next() {
        throw new RuntimeException("No se puede obtener el siguiente eslabón de un anillo vacío");
    }

    public Object current() {
        throw new RuntimeException("No se puede obtener un eslabón de un anillo vacío");
    }
}
