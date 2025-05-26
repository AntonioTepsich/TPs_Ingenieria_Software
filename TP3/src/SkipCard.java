package src;

import java.util.Objects;

public class SkipCard extends Card {
    private SkipCard(String color) { this.color = color; }

    public static SkipCard with(String color) { return new SkipCard(color); }

    @Override
    public boolean canPlayOn(Card top) { return top.accepts(this); }

    @Override
    protected boolean accepts(Card other) { return other.acceptsColor(this.color) || other.acceptsType("skip"); }

    @Override
    protected boolean acceptsColor(String c) { return this.color.equals(c); }

    @Override
    protected boolean acceptsType(String tipo) { return "skip".equals(tipo); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkipCard)) return false;
        SkipCard that = (SkipCard) o;
        return Objects.equals(color, that.color);
    }
}
