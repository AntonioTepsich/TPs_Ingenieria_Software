package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class DeckFactory {
    public static List<Card> buildFullDeck() {
        List<Card> deck = new ArrayList<>();
        String[] colors = {"Red", "Blue", "Yellow", "Green"};

        for (String color : colors) {
            deck.add(NumberCard.with(color, 0));

            IntStream.rangeClosed(1, 9).forEach(i ->
                    deck.addAll(Collections.nCopies(2, NumberCard.with(color, i)))
            );

            deck.addAll(Collections.nCopies(2, SkipCard.with(color)));
            deck.addAll(Collections.nCopies(2, ReverseCard.with(color)));
            deck.addAll(Collections.nCopies(2, DrawTwoCard.with(color)));
        }

        deck.addAll(Collections.nCopies(8, WildCard.create()));

        return deck;
    }
}
