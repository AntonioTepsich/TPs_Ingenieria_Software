package org.udesa.unoback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udesa.unoback.model.JsonCard;
import org.udesa.unoback.service.UnoService;

import java.util.List;
import java.util.UUID;

@Controller
public class UnoController {
    @Autowired
    UnoService unoService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleIllegalArgument(Exception ex){
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @PostMapping ( "newmatch" )
    public ResponseEntity newMatch(@RequestParam List<String> players ) {
        return ResponseEntity.ok(unoService.newMatch( players ));
    }

    @GetMapping("playerhand/{matchId}")
    public ResponseEntity playerHand( @PathVariable UUID matchId ) {
        return ResponseEntity.ok( unoService.playerHand( matchId).stream().map(each -> each.asJson()  )  );
    }

    @PostMapping("play/{matchId}/{player}")
    public ResponseEntity play(@PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card) {
        unoService.play(matchId, player, card.asCard());
        return ResponseEntity.ok().build();
    }

    @PostMapping("draw/{matchId}/{player}")
    public ResponseEntity drawCard( @PathVariable UUID matchId, @PathVariable String player ) {
        unoService.drawCard(matchId, player);
        return ResponseEntity.ok().build();
    }

    @GetMapping("activecard/{matchId}")
    public ResponseEntity activeCard(@PathVariable UUID matchId) {
        return ResponseEntity.ok( unoService.activeCard(matchId).asJson() );
    }
}