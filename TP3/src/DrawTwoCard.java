package src;

import java.util.Objects;

public class DrawTwoCard extends Card {
    private DrawTwoCard(String color) { this.color = color; }

    public static DrawTwoCard with(String color) { return new DrawTwoCard(color); }

    @Override
    public boolean canPlayOn(Card top) { return top.accepts(this); }

    @Override
    protected boolean accepts(Card other) { return other.acceptsColor(this.color) || other.acceptsType("drawtwo"); }

    @Override
    protected boolean acceptsColor(String c) { return this.color.equals(c); }

    @Override
    protected boolean acceptsType(String tipo) { return "drawtwo".equals(tipo); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DrawTwoCard)) return false;
        DrawTwoCard that = (DrawTwoCard) o;
        return Objects.equals(color, that.color);
    }
}
