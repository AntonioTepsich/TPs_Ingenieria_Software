package src;

import java.util.function.Function;

public class TurnManager {
    private Player current;
    private final Function<Player, Player> nextFunction;
    private TurnManager twin;

    private TurnManager(Player start, Function<Player, Player> nextFunction) {
        this.current = start;
        this.nextFunction = nextFunction;
    }

    public static TurnManager createPair(Player first) {
        TurnManager clockwise = new TurnManager(first, Player::next);
        TurnManager counterClockwise = new TurnManager(first, Player::previous);
        clockwise.twin = counterClockwise;
        counterClockwise.twin = clockwise;
        return clockwise;
    }

    public TurnManager reversed() {
        return twin;
    }

    public Player current() {
        return current;
    }

    public Player next() {
        current = nextFunction.apply(current);
        return current;
    }
}
