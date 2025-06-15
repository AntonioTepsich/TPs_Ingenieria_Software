package org.udesa.unoback.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.udesa.unoback.UnoServiceTest;
import org.udesa.unoback.service.Dealer;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UnoControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private Dealer dealer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        when(dealer.fullDeck()).thenReturn(UnoServiceTest.fullDeck());
    }

    // NEWMATCH

    @Test
    void newMatchSuccess() throws Exception {
        String uuid = startNewGame();
        assertNotNull(UUID.fromString(uuid));
    }

    @Test
    void newMatchFailsWithoutPlayers() throws Exception {
        mockMvc.perform(post("/newmatch"))
                .andExpect(status().isInternalServerError());
    }

    // PLAYERHAND

    @Test
    void getPlayerHandSuccess() throws Exception {
        List<JsonCard> cards = getActivePlayerHand(startNewGame());
        assertFalse(cards.isEmpty());
    }

    @Test
    void getPlayerHandFailsWithInvalidUUID() throws Exception {
        mockMvc.perform(get("/playerhand/invalid-uuid"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getPlayerHandFailsWithEmptyUUID() throws Exception {
        mockMvc.perform(get("/playerhand/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPlayerHandFailsWithNonexistentUUID() throws Exception {
        mockMvc.perform(get("/playerhand/" + UUID.randomUUID()))
                .andExpect(status().isInternalServerError());
    }

    // ACTIVECARD

    @Test
    void getActiveCardSuccess() throws Exception {
        String uuid = startNewGame();
        String response = mockMvc.perform(get("/activecard/" + uuid))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains("color") && response.contains("number"));
    }

    @Test
    void getActiveCardFailsWithInvalidUUID() throws Exception {
        mockMvc.perform(get("/activecard/" + UUID.randomUUID()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getActiveCardFailsWithEmptyUUID() throws Exception {
        mockMvc.perform(get("/activecard/"))
                .andExpect(status().isNotFound());
    }

    // PLAY

    @Test
    void playCardSuccessOnCorrectTurn() throws Exception {
        String uuid = startNewGame();
        List<JsonCard> cards = getActivePlayerHand(uuid);
        String cardJson = objectMapper.writeValueAsString(cards.get(0));

        mockMvc.perform(post("/play/" + uuid + "/Emilio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andExpect(status().isOk());
    }

    @Test
    void playCardFailsOnWrongTurn() throws Exception {
        String uuid = startNewGame();
        List<JsonCard> cards = getActivePlayerHand(uuid);
        String cardJson = objectMapper.writeValueAsString(cards.get(0));

        String response = mockMvc.perform(post("/play/" + uuid + "/Julio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andExpect(status().isInternalServerError())
                .andReturn().getResponse().getContentAsString();

        assertEquals(Player.NotPlayersTurn + "Julio", response);
    }

    @Test
    void playCardFailsWithMalformedJson() throws Exception {
        String uuid = startNewGame();
        mockMvc.perform(post("/play/" + uuid + "/Emilio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"color\": \"red\""))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void playCardFailsWithMissingParameters() throws Exception {
        mockMvc.perform(post("/play/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void playCardFailsWithEmptyJson() throws Exception {
        String uuid = startNewGame();
        mockMvc.perform(post("/play/" + uuid + "/Emilio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void playCardFailsWithInvalidPlayer() throws Exception {
        String uuid = startNewGame();
        List<JsonCard> cards = getActivePlayerHand(uuid);
        String cardJson = objectMapper.writeValueAsString(cards.get(0));

        mockMvc.perform(post("/play/" + uuid + "/NoExiste")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cardJson))
                .andExpect(status().isInternalServerError());
    }

    // DRAWCARD

    @Test
    void drawCardSuccessOnTurn() throws Exception {
        String uuid = startNewGame();
        mockMvc.perform(post("/draw/" + uuid + "/Emilio"))
                .andExpect(status().isOk());
    }

    @Test
    void drawCardFailsOffTurn() throws Exception {
        String uuid = startNewGame();
        mockMvc.perform(post("/draw/" + uuid + "/Julio"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void drawCardFailsWithInvalidPlayer() throws Exception {
        String uuid = startNewGame();
        mockMvc.perform(post("/draw/" + uuid + "/NoExiste"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void drawCardFailsWithInvalidUUID() throws Exception {
        mockMvc.perform(post("/draw/" + UUID.randomUUID() + "/Emilio"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void drawCardFailsWithEmptyUUIDAndPlayer() throws Exception {
        mockMvc.perform(post("/draw/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void drawCardFailsWithEmptyPlayer() throws Exception {
        String uuid = startNewGame();
        mockMvc.perform(post("/draw/" + uuid))
                .andExpect(status().isNotFound());
    }

    @Test
    void drawCardFailsWithShortInvalidPlayer() throws Exception {
        String uuid = startNewGame();
        mockMvc.perform(post("/draw/" + uuid + "/A"))
                .andExpect(status().isInternalServerError());
    }

    // AUXILIARES

    private List<JsonCard> getActivePlayerHand(String uuid) throws Exception {
        String response = mockMvc.perform(get("/playerhand/" + uuid))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(response, new TypeReference<List<JsonCard>>() {});
    }

    private String startNewGame() throws Exception {
        String response = mockMvc.perform(post("/newmatch?players=Emilio&players=Julio"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readTree(response).asText();
    }
}
