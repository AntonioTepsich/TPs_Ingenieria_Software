package src;

public class WildCard extends Card {
    public WildCard() { this.color = null; }

    public static WildCard create() { return new WildCard(); }

    public WildCard chooseColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public boolean canPlayOn(Card top) { return true; }

    @Override
    protected boolean accepts(Card other) { return true; }

    @Override
    protected boolean acceptsColor(String c) { return true; }

    @Override
    protected boolean acceptsType(String tipo) { return true; }
}
