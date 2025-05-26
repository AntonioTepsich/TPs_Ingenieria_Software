package src;

import java.util.*;
import java.util.stream.IntStream;

public class Player {
    private final String playerName;
    private final List<Card> hand = new ArrayList<>();
    private final Game game;
    private Player next;
    private Player previous;

    Player(String name, Game game) {
        this.playerName = name;
        this.game = game;
    }

    public String name() { return playerName; }
    public List<Card> hand() { return Collections.unmodifiableList(hand); }

    public void draw(int n) {
        if (game.deckSize() < n) throw new EmptyDeckException();
        IntStream.range(0, n)
                .forEach(i -> hand.add(game.getDeck().poll()));
    }


    public void play(Card c) {
        if (!hand.contains(c)) throw new RuntimeException("Card not in hand");

        boolean isSecondToLast = hand.size() == 2;
        boolean isCallingUNO = c.hasCalledUNO();

        hand.remove(c);

        if (isSecondToLast && !isCallingUNO) {
            draw(2);
        } else if (!isSecondToLast && isCallingUNO) {
            draw(2);
        }

        c.resetUNO();
    }

    public boolean has(Card c) { return hand.contains(c); }

    public void setNext(Player p) { this.next = p; }

    public void setPrevious(Player p) { this.previous = p; }

    public Player next() { return next; }

    public Player previous() { return previous; }
}
