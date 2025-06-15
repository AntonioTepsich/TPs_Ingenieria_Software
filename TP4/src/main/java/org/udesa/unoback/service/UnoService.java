package org.udesa.unoback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udesa.unoback.model.Card;
import org.udesa.unoback.model.Match;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UnoService {
    @Autowired
    private Dealer dealer;
    private Map<UUID, Match> sessions = new HashMap<UUID, Match>();

    public UUID newMatch(List<String> players) {
        UUID newKey = UUID.randomUUID();
        sessions.put(newKey, Match.fullMatch(dealer.fullDeck(), players));
        return newKey;
    }

    public List<Card> playerHand(UUID matchKey) {
        return sessions.get(matchKey).playerHand();
    }

    public void play(UUID matchKey, String playerName, Card aCard) {
        sessions.get(matchKey).play(playerName, aCard);
    }

    public void drawCard(UUID matchId, String player) {
        Match match = sessions.get(matchId);
        match.drawCard(player);
    }

    public Card activeCard(UUID matchId) {
        Match match = sessions.get(matchId);
        return match.activeCard();
    }
}
