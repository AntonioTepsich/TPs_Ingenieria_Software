package src;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    final Deque<Card> deck;
    final List<Player> players;
    private Card top;
    private TurnManager turnManager;
    private Player currentPlayer;

    private Game(List<Card> deckOrder, int handSize, List<String> names) {
        this.deck = new ArrayDeque<>(deckOrder);
        this.players = names.stream()
                .map(name -> new Player(name, this))
                .collect(Collectors.toCollection(ArrayList::new));

        if (deck.isEmpty()) throw new EmptyDeckException("Empty deck");
        top = deck.poll();

        setupCircle(handSize);
    }

    private void setupCircle(int handSize) {
        Iterator<Player> iterator = players.iterator();
        if (!iterator.hasNext()) return;

        Player first = iterator.next();
        Player prev = first;

        while (iterator.hasNext()) {
            Player current = iterator.next();
            prev.setNext(current);
            current.setPrevious(prev);
            prev = current;
        }

        prev.setNext(first);
        first.setPrevious(prev);

        players.forEach(p -> p.draw(handSize));

        turnManager = TurnManager.createPair(first);
        currentPlayer = turnManager.current();
    }

    public static Game withFullDeck(String... names) {
        List<Card> fullDeck = DeckFactory.buildFullDeck();
        Collections.shuffle(fullDeck);
        int handSize = 7;
        return new Game(fullDeck, handSize, Arrays.asList(names));
    }

    public static Game withDeckPlayersAndHandSize(List<Card> deckOrder, int handSize, String... names) {
        return new Game(deckOrder, handSize, Arrays.asList(names));
    }

    public Card getTop() { return top; }

    public String colorOnTop() { return top.color(); }

    public int deckSize() { return deck.size(); }

    public void playCard(String playerName, Card c) {
        if (!currentPlayer.name().equals(playerName)) throw new InvalidPlayException("Not your turn");

        Player player = getPlayerByName(playerName);
        if (!player.has(c)) throw new InvalidPlayException("Card not in hand");
        if (!c.canPlayOn(top)) throw new InvalidPlayException("Invalid play");
        if (c instanceof WildCard && c.color == null) throw new InvalidPlayException("WildCard without color");

        player.play(c);
        top = c;

        advanceTurn(c);
    }

    private Player getPlayerByName(String name) {
        return players.stream()
                .filter(p -> p.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new InvalidPlayException("Unknown player"));
    }

    private void advanceTurn(Card c) {
        if (c instanceof ReverseCard) {
            turnManager = turnManager.reversed();
            currentPlayer = turnManager.next();
        } else if (c instanceof SkipCard) {
            turnManager.next();
            currentPlayer = turnManager.next();
        } else if (c instanceof DrawTwoCard) {
            Player victim = turnManager.next();
            victim.draw(2);
            currentPlayer = turnManager.next();
        } else {
            currentPlayer = turnManager.next();
        }
    }

    Deque<Card> getDeck() { return deck; }
}