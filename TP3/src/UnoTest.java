package src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UnoTest {
    private Card redTwo;
    private Card blueFour;
    private Card blueSkip;
    private Card reverseRed;
    private Card redThree;
    private Card redFive;
    private Card plusTwoRed;
    private Card yellowFive;
    private Card greenSix;
    private Card blueEight;
    private Card redThreeNum;
    private Card redFourNum;
    private Card blueSix;
    private Card redFiveNum;
    private Card blueOne;
    private Card greenTwo;
    private WildCard wildYellow;
    private WildCard wildBlue;

    private List<Card> singleDeck;
    private List<Card> oneCardDeck;
    private List<Card> invalidPlayDeck;
    private List<Card> reverseDeck;
    private List<Card> drawTwoNoAccumDeck;
    private List<Card> penaltyNoUNODek;
    private List<Card> unoCalledCorrectDeck;
    private List<Card> unoCalledIncorrectDeck;
    private List<Card> drawTwoForceDrawDeck;
    private List<Card> wildFollowedBySameColorDeck;
    private List<Card> wildWithoutColorDeck;
    private List<Card> reversePlayDeck;
    private List<Card> deckWithRedThree;

    @BeforeEach
    public void setup() {
        redTwo = NumberCard.with("Red", 2);
        blueFour = NumberCard.with("Blue", 4);
        blueSkip = SkipCard.with("Blue");
        reverseRed = ReverseCard.with("Red");
        redThree = NumberCard.with("Red", 3);
        redFive = NumberCard.with("Red", 5);
        plusTwoRed = DrawTwoCard.with("Red");
        yellowFive = NumberCard.with("Yellow", 5);
        greenSix = NumberCard.with("Green", 6);
        blueEight = NumberCard.with("Blue", 8);
        redThreeNum = NumberCard.with("Red", 3);
        redFourNum = NumberCard.with("Red", 4);
        blueSix = NumberCard.with("Blue", 6);
        redFiveNum = NumberCard.with("Red", 5);
        blueOne = NumberCard.with("Blue", 1);
        greenTwo = NumberCard.with("Green", 2);
        wildYellow = WildCard.create().chooseColor("Yellow");
        wildBlue = WildCard.create().chooseColor("Blue");

        singleDeck = List.of(redTwo);
        oneCardDeck = List.of(blueFour, blueSkip);
        invalidPlayDeck = List.of(redTwo, blueFour);
        reverseDeck = List.of(redTwo, reverseRed, redThree, redFive);
        drawTwoNoAccumDeck = List.of(redTwo, plusTwoRed, yellowFive, greenSix, blueEight);
        penaltyNoUNODek = List.of(redTwo, redThreeNum, redFourNum, blueSix, greenSix);
        unoCalledCorrectDeck = List.of(redTwo, redThreeNum, redFourNum);
        unoCalledIncorrectDeck = List.of(redTwo, redThreeNum, redFourNum, redFiveNum, blueOne, greenTwo);
        drawTwoForceDrawDeck = List.of(redTwo, plusTwoRed, blueSix, blueSix, blueSix);
        wildFollowedBySameColorDeck = List.of(redTwo, wildBlue, NumberCard.with("Blue", 2));
        wildWithoutColorDeck = List.of(redTwo, WildCard.create());
        reversePlayDeck = List.of(redTwo, reverseRed, redThree, redFive);
        deckWithRedThree = List.of(redTwo, redThree);
    }

    private Game createGameWithDeckAndPlayers(List<Card> deck, int handSize, String... playerNames) {
        return Game.withDeckPlayersAndHandSize(deck, handSize, playerNames);
    }

    private void assertPlayerHandSize(Game game, int playerIndex, int expectedSize) {
        assertEquals(expectedSize, game.players.get(playerIndex).hand().size());
    }

    private void assertTopCard(Game game, Card expectedCard) {
        assertEquals(expectedCard, game.getTop());
    }

    private void assertTopCardColor(Game game, String expectedColor) {
        assertEquals(expectedColor, game.getTop().color());
    }

    private void assertTopCardClass(Game game, Class<?> expectedClass) {
        assertEquals(expectedClass, game.getTop().getClass());
    }

    @Test
    void testZeroHandSize() {
        Game game = createGameWithDeckAndPlayers(singleDeck, 0, "A");
        assertTopCard(game, redTwo);
        assertEquals(0, game.deckSize());
    }

    @Test
    void testPlaySingleCard() {
        Game game = createGameWithDeckAndPlayers(oneCardDeck, 1, "J");
        game.playCard("J", game.players.get(0).hand().get(0));
        assertTopCardColor(game, "Blue");
        assertTopCardClass(game, SkipCard.class);
        assertPlayerHandSize(game, 0, 0);
    }

    @Test
    void testPlaySingleCardInvalid() {
        Game game = createGameWithDeckAndPlayers(invalidPlayDeck, 1, "Z");
        assertThrows(InvalidPlayException.class, () -> game.playCard("Z", game.players.get(0).hand().get(0)));
        assertTopCard(game, redTwo);
        assertPlayerHandSize(game, 0, 1);
    }

    @Test
    void testGameWithFullDeck() {
        Game game = Game.withFullDeck("A", "B", "C");
        assertEquals(108 - 7 * game.players.size() - 1, game.deckSize());
        assertEquals(3, game.players.size());
        assertTrue(game.players.stream().allMatch(p -> p.hand().size() == 7));
        assertNotNull(game.getTop());
    }

    @Test
    void testWildCardColor() {
        Game game = createGameWithDeckAndPlayers(List.of(redTwo, wildYellow), 1, "A");
        game.playCard("A", game.players.get(0).hand().get(0));
        assertEquals("Yellow", game.colorOnTop());
    }

    @Test
    void testReverseTurnOrder() {
        Game game = createGameWithDeckAndPlayers(reverseDeck, 1, "A", "B", "C");
        game.playCard("A", game.players.get(0).hand().get(0));
        game.playCard("C", game.players.get(2).hand().get(0));
        assertTopCardColor(game, redFive.color());
        assertTopCardClass(game, redFive.getClass());
    }

    @Test
    void testDrawTwoNoAccumulation() {
        Game game = createGameWithDeckAndPlayers(drawTwoNoAccumDeck, 1, "A", "B");
        game.playCard("A", game.players.get(0).hand().get(0));
        assertTopCardColor(game, plusTwoRed.color());
        assertTopCardClass(game, plusTwoRed.getClass());
    }

    @Test
    void testInvalidPlayOrOutOfTurn() {
        Game game = createGameWithDeckAndPlayers(singleDeck, 0, "A", "B");
        assertThrows(InvalidPlayException.class, () -> game.playCard("B", NumberCard.with("Red", 5)));
    }

    @Test
    void testPenaltyNoUNO() {
        Game game = createGameWithDeckAndPlayers(penaltyNoUNODek, 2, "A");
        game.playCard("A", redThreeNum);
        assertPlayerHandSize(game, 0, 3);
    }

    @Test
    void testUNOcalledCorrectly() {
        Game game = createGameWithDeckAndPlayers(unoCalledCorrectDeck, 2, "B");
        game.playCard("B", redThreeNum.uno());
        assertPlayerHandSize(game, 0, 1);
    }

    @Test
    void testUNOcalledIncorrectly() {
        Game game = createGameWithDeckAndPlayers(unoCalledIncorrectDeck, 3, "C");
        game.playCard("C", redThreeNum.uno());
        assertPlayerHandSize(game, 0, 4);
    }

    @Test
    void testDrawTwoForcesDraw() {
        Game game = createGameWithDeckAndPlayers(drawTwoForceDrawDeck, 1, "A", "B");
        game.playCard("A", plusTwoRed);
        assertPlayerHandSize(game, 1, 3);
    }

    @Test
    void testWildCardFollowedBySameColorCard() {
        Game game = createGameWithDeckAndPlayers(wildFollowedBySameColorDeck, 1, "A", "B");
        game.playCard("A", wildBlue);
        game.playCard("B", NumberCard.with("Blue", 2));
        assertTopCard(game, NumberCard.with("Blue", 2));
    }

    @Test
    void testWildCardWithoutColorThrows() {
        Game game = createGameWithDeckAndPlayers(wildWithoutColorDeck, 1, "A");
        assertThrows(InvalidPlayException.class, () -> game.playCard("A", game.players.get(0).hand().get(0)));
    }

    @Test
    void testReverseChangesDirectionAndAffectsNextTurn() {
        Game game = createGameWithDeckAndPlayers(reversePlayDeck, 1, "A", "B", "C");
        game.playCard("A", game.players.get(0).hand().get(0));
        assertThrows(InvalidPlayException.class, () -> game.playCard("B", game.players.get(1).hand().get(0)));
        game.playCard("C", game.players.get(2).hand().get(0));
    }

    @Test
    void testDeckEmptyDoesNotThrowOnDraw() {
        Game game = createGameWithDeckAndPlayers(singleDeck, 0, "A");
        game.players.get(0).draw(0);
        assertEquals(0, game.deckSize());
    }

    @Test
    void testPlayCardNotInHandThrows() {
        Game game = createGameWithDeckAndPlayers(deckWithRedThree, 1, "A");
        assertThrows(InvalidPlayException.class, () -> game.playCard("A", redFiveNum));
    }
}