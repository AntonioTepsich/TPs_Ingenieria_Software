package src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UnoTest {
    private Card redTwo;
    private Card blueFour;
    private Card blueSkip;
    private List<Card> singleDeck;
    private List<Card> oneCardDeck;
    private List<Card> invalidPlayDeck;

    @BeforeEach
    public void setup() {
        redTwo = NumberCard.with("Red", 2);
        blueFour = NumberCard.with("Blue", 4);
        blueSkip = SkipCard.with("Blue");
        singleDeck = List.of(redTwo);
        oneCardDeck = List.of(blueFour, blueSkip);
        invalidPlayDeck = List.of(redTwo, blueFour);
    }

    @Test
    void testZeroHandSize() {
        Game game = Game.withDeckPlayersAndHandSize(singleDeck, 0, "A");
        assertEquals(redTwo, game.getTop());
        assertEquals(0, game.deckSize());
    }

    @Test
    void testPlaySingleCard() {
        Game game = Game.withDeckPlayersAndHandSize(oneCardDeck, 1, "J");
        game.playCard("J", game.players.get(0).hand().get(0));
        assertEquals("Blue", game.getTop().color());
        assertEquals(SkipCard.class, game.getTop().getClass());
        assertEquals(0, game.players.get(0).hand().size());
    }

    @Test
    void testPlaySingleCardInvalid() {
        Game game = Game.withDeckPlayersAndHandSize(invalidPlayDeck, 1, "Z");
        assertThrows(InvalidPlayException.class, () -> game.playCard("Z", game.players.get(0).hand().get(0)));
        assertEquals(redTwo, game.getTop());
        assertEquals(1, game.players.get(0).hand().size());
    }

    @Test
    void testGameWithFullDeck() {
        Game game = Game.withFullDeck("Alice", "Bob", "Charlie");

        assertEquals(108 - 7 * game.players.size() - 1, game.deckSize());
        assertEquals(3, game.players.size());
        assertEquals(true, game.players.stream().allMatch(p -> p.hand().size() == 7));
        assertEquals(false, game.getTop() == null);
    }


    @Test
    void testWildCardColor() {
        WildCard wc = WildCard.create().chooseColor("Yellow");
        List<Card> deck = List.of(redTwo, wc);
        Game game = Game.withDeckPlayersAndHandSize(deck, 1, "P");
        Card toPlay = game.players.get(0).hand().get(0);
        game.playCard("P", toPlay);
        assertEquals("Yellow", game.colorOnTop());
    }

    @Test
    void testReverseTurnOrder() {
        Card reverse = ReverseCard.with("Red");
        Card redThree = NumberCard.with("Red", 3);
        Card redFive = NumberCard.with("Red", 5);
        List<Card> deck = List.of(redTwo, reverse, redThree, redFive);
        Game game = Game.withDeckPlayersAndHandSize(deck, 1, "A", "B", "C");
        Card toPlayA = game.players.get(0).hand().get(0);
        game.playCard("A", toPlayA);
        Card toPlayC = game.players.get(2).hand().get(0);
        game.playCard("C", toPlayC);
        assertEquals(redFive.color(), game.getTop().color());
        assertEquals(redFive.getClass(), game.getTop().getClass());
    }

    @Test
    void testDrawTwoNoAccumulation() {
        Card plusTwo = DrawTwoCard.with("Red");
        Card next = NumberCard.with("Yellow", 5);
        Card extra1 = NumberCard.with("Green", 6);
        Card extra2 = NumberCard.with("Blue", 8);
        List<Card> deck = List.of(redTwo, plusTwo, next, extra1, extra2);
        Game game = Game.withDeckPlayersAndHandSize(deck, 1, "X", "Y");
        Card toPlay = game.players.get(0).hand().get(0);
        game.playCard("X", toPlay);
        assertEquals(plusTwo.color(), game.getTop().color());
        assertEquals(plusTwo.getClass(), game.getTop().getClass());
    }

    @Test
    void testInvalidPlayOrOutOfTurn() {
        Game game = Game.withDeckPlayersAndHandSize(singleDeck, 0, "A", "B");
        assertThrows(InvalidPlayException.class, () -> game.playCard("B", NumberCard.with("Red", 5)));
    }

    @Test
    void testPenaltyNoUNO() {
        Card red3 = NumberCard.with("Red", 3);
        Card red4 = NumberCard.with("Red", 4);
        Card extra1 = NumberCard.with("Blue", 6);
        Card extra2 = NumberCard.with("Green", 8);
        List<Card> deck = List.of(redTwo, red3, red4, extra1, extra2);
        Game game = Game.withDeckPlayersAndHandSize(deck, 2, "A");

        Player p = game.players.get(0);
        game.playCard("A", red3);

        assertEquals(3, p.hand().size());
    }

    @Test
    void testUNOcalledCorrectly() {
        Card red3 = NumberCard.with("Red", 3);
        Card red4 = NumberCard.with("Red", 4);
        List<Card> deck = List.of(redTwo, red3, red4);
        Game game = Game.withDeckPlayersAndHandSize(deck, 2, "B");
        Player p = game.players.get(0);
        game.playCard("B", red3.uno());
        assertEquals(1, p.hand().size());
    }

    @Test
    void testUNOcalledIncorrectly() {
        Card red3 = NumberCard.with("Red", 3);
        Card red4 = NumberCard.with("Red", 4);
        Card red5 = NumberCard.with("Red", 5);
        Card extra1 = NumberCard.with("Blue", 1);
        Card extra2 = NumberCard.with("Green", 2);
        List<Card> deck = List.of(redTwo, red3, red4, red5, extra1, extra2);
        Game game = Game.withDeckPlayersAndHandSize(deck, 3, "C");
        Player p = game.players.get(0);
        game.playCard("C", red3.uno());
        assertEquals(4, p.hand().size());
    }

    @Test
    void testDrawTwoForcesDraw() {
        Card drawTwo = DrawTwoCard.with("Red");
        Card filler = NumberCard.with("Blue", 6);
        List<Card> deck = List.of(redTwo, drawTwo, filler, filler, filler);
        Game game = Game.withDeckPlayersAndHandSize(deck, 1, "A", "B");

        game.playCard("A", drawTwo);
        Player b = game.players.get(1);
        assertEquals(3, b.hand().size());
    }

    @Test
    void testWildCardFollowedBySameColorCard() {
        WildCard wc = WildCard.create().chooseColor("Blue");
        Card blueTwo = NumberCard.with("Blue", 2);
        List<Card> deck = List.of(redTwo, wc, blueTwo);
        Game game = Game.withDeckPlayersAndHandSize(deck, 1, "A", "B");

        game.playCard("A", wc);
        game.playCard("B", blueTwo);
        assertEquals(blueTwo, game.getTop());
    }
}
