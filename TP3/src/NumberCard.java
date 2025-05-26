package src;

import java.util.Objects;

public class NumberCard extends Card {
    private final int number;

    private NumberCard(String color, int number) {
        this.color = color;
        this.number = number;
    }

    public static NumberCard with(String color, int number) {
        return new NumberCard(color, number);
    }

    @Override
    public boolean canPlayOn(Card top) { return top.accepts(this); }

    @Override
    protected boolean accepts(Card other) { return other.acceptsColor(this.color) || other.acceptsNumber(this.number); }

    @Override
    protected boolean acceptsColor(String c) { return this.color.equals(c); }

    @Override
    protected boolean acceptsNumber(int n) { return this.number == n; }

    public int number() { return number; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberCard)) return false;
        NumberCard that = (NumberCard) o;
        return number == that.number && Objects.equals(color, that.color);
    }
}
