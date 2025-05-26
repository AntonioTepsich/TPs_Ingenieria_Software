package src;

import java.util.Objects;

public class ReverseCard extends Card {
    private ReverseCard(String color) { this.color = color; }

    public static ReverseCard with(String color) { return new ReverseCard(color); }

    @Override
    public boolean canPlayOn(Card top) { return top.accepts(this); }

    @Override
    protected boolean accepts(Card other) { return other.acceptsColor(this.color) || other.acceptsType("reverse"); }

    @Override
    protected boolean acceptsColor(String c) { return this.color.equals(c); }

    @Override
    protected boolean acceptsType(String tipo) { return "reverse".equals(tipo); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReverseCard)) return false;
        ReverseCard that = (ReverseCard) o;
        return Objects.equals(color, that.color);
    }
}
