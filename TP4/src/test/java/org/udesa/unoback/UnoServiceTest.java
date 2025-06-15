package org.udesa.unoback;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.udesa.unoback.model.*;
import org.udesa.unoback.service.UnoService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UnoServiceTest {

    @Autowired
    private UnoService unoService;

    private UUID matchId;

    @BeforeEach
    public void setup() {
        matchId = unoService.newMatch(List.of("Martina", "Alex"));
    }

    public static List<Card> fullDeck() {
        return List.of(
                new NumberCard("Red", 1),
                new NumberCard("Red", 2),
                new NumberCard("Blue", 2),
                new WildCard(),
                new ReverseCard("Yellow"),
                new NumberCard("Yellow", 3),
                new NumberCard("Red", 4),
                new Draw2Card("Blue"),
                new NumberCard("Yellow", 3),
                new NumberCard("Blue", 3),
                new WildCard(),
                new NumberCard("Yellow", 5),
                new SkipCard("Red"),
                new NumberCard("Blue", 4),
                new NumberCard("Red", 3),
                new NumberCard("Yellow", 2),
                new NumberCard("Green", 1),
                new NumberCard("Red", 5)
        );
    }

    @Test
    public void newMatchTest() {
        assertNotNull(unoService.newMatch(List.of("Martina", "Alex")));
    }

    @Test
    public void testActiveCardNotNull() {
        assertNotNull(unoService.activeCard(matchId));
    }

    @Test
    public void testNewMatchWithMultiplePlayers() {
        assertNotNull(unoService.newMatch(List.of("A", "B", "C", "D", "E")));
    }

    @Test
    public void testNewMatchWithEmptyPlayerList() {
        assertThrowsException(Exception.class, () -> unoService.newMatch(List.of()));
    }

    @Test
    public void testNewMatchWithDuplicatePlayers() {
        assertNotNull(unoService.newMatch(List.of("A", "A")));
    }

    @Test
    public void testNewMatchWithLongPlayerNames() {
        String longName = "x".repeat(500);
        assertNotNull(unoService.newMatch(List.of(longName, "B")));
    }

    @Test
    public void testNewMatchWithNullPlayerList() {
        assertThrowsException(NullPointerException.class, () -> unoService.newMatch(null));
    }

    @Test
    public void testFullDeckContainsMultipleColors() throws Exception {
        Field dealerField = unoService.getClass().getDeclaredField("dealer");
        dealerField.setAccessible(true);
        Object dealer = dealerField.get(unoService);
        List<Card> deck = ((org.udesa.unoback.service.Dealer) dealer).fullDeck();

        assertTrue(deck.stream().anyMatch(c -> "Red".equals(c.color())));
        assertTrue(deck.stream().anyMatch(c -> "Blue".equals(c.color())));
        assertTrue(deck.stream().anyMatch(c -> "Green".equals(c.color())));
        assertTrue(deck.stream().anyMatch(c -> "Yellow".equals(c.color())));
    }

    @Test
    public void testPlayerHandNotEmptyAfterMatchCreation() {
        List<Card> hand = unoService.playerHand(matchId);
        assertNotNull(hand);
        assertFalse(hand.isEmpty());
    }

    @Test
    public void testPlayerHandWithInvalidMatchUUID() {
        UUID fakeId = UUID.randomUUID();
        assertThrowsException(Exception.class, () -> unoService.playerHand(fakeId));
    }

    @Test
    public void testPlayerHandWithNullMatchUUID() {
        assertThrowsException(NullPointerException.class, () -> unoService.playerHand(null));
    }

    @Test
    public void testPlayWithInvalidPlayerName() {
        Card card = unoService.playerHand(matchId).get(0);
        assertThrowsException(Exception.class, () -> unoService.play(matchId, "UnknownPlayer", card));
    }

    @Test
    public void testPlayCardNotInHand() {
        Card fakeCard = new NumberCard("Red", 99);
        assertThrowsException(Exception.class, () -> unoService.play(matchId, "Martina", fakeCard));
    }

    @Test
    public void testDrawCardIncreasesHandSize() {
        List<Card> handBefore = unoService.playerHand(matchId);
        int sizeBefore = handBefore.size();

        unoService.drawCard(matchId, "Martina");

        List<Card> handAfter = unoService.playerHand(matchId);
        assertEquals(sizeBefore + 1, handAfter.size());
    }

    @Test
    public void testDrawCardWithInvalidMatchId() {
        UUID fakeId = UUID.randomUUID();
        assertThrowsException(Exception.class, () -> unoService.drawCard(fakeId, "Martina"));
    }

    @Test
    public void testActiveCardWithInvalidMatchId() {
        UUID fakeId = UUID.randomUUID();
        assertThrowsException(Exception.class, () -> unoService.activeCard(fakeId));
    }

    @Test
    public void testSessionsMapStoresMultipleMatches() {
        UUID id1 = unoService.newMatch(List.of("A", "B"));
        UUID id2 = unoService.newMatch(List.of("C", "D"));
        assertNotEquals(id1, id2);
        assertNotNull(unoService.playerHand(id1));
        assertNotNull(unoService.playerHand(id2));
    }

    private void assertThrowsException(Class<? extends Throwable> exClass, Runnable runnable) {
        Throwable thrown = assertThrows(exClass, runnable::run);
        assertNotNull(thrown);
    }
}
