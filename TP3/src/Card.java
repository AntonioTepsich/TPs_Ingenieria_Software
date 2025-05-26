package src;

public abstract class Card {
    protected String color;
    private boolean calledUNO = false;

    public abstract boolean canPlayOn(Card top);

    protected abstract boolean accepts(Card other);

    protected boolean acceptsColor(String c) { return color != null && color.equals(c); }

    protected boolean acceptsNumber(int n) { return false; }

    protected boolean acceptsType(String tipo) { return false; }

    public Card uno() {
        this.calledUNO = true;
        return this;
    }

    public boolean hasCalledUNO() { return calledUNO; }

    public void resetUNO() { this.calledUNO = false; }

    public String color() { return color; }
}
