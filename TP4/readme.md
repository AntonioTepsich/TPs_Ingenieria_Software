# Enunciado

La fecha de entrega es Domingo 15 de junio, 23:59 hs

Se nos pide implementar un servicio para que permita jugar el Juego Uno a través de una interfaz Api Rest.

Se definen los siguientes endpoints o recursos.

- POST /newmatch con los nombres de los jugadores como parametros

Ej: curl -X POST "http://localhost:8080/newmatch?players=A&players=B" \

-H "Accept: application/json"r

- POST /play/{matchId}/{player} y la representación json de la carta a jugar

Ej: curl -X POST "http://localhost:8080/play/6951e08e-3594-49ec-95a3-056382cea112/A" \

-H "Content-Type: application/json" \

-d '{"color":"Blue","number":6,"type":"NumberCard","shout":false}'

- POST draw/{matchId}/{player}GET /activecard/{matchId}Ej: curl -X GET "http://localhost:8080/activecard/6951e08e-3594-49ec-95a3-056382cea112" \

-H "Accept: application/json"

- GET /playerhand/{matchId}

Ej: curl -X GET "http://localhost:8080/playerhand/6951e08e-3594-49ec-95a3-056382cea112" \

-H "Accept: application/json"

Los endpoints correspondientes Java Spring son:

```java
@PostMapping("newmatch") public ResponseEntity newMatch( @RequestParam List<String> players ) {
@PostMapping("play/{matchId}/{player}") public ResponseEntity play( @PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
@PostMapping("draw/{matchId}/{player}") public ResponseEntity drawCard( @PathVariable UUID matchId, @RequestParam String player ) {
@GetMapping("activecard/{matchId}") public ResponseEntity activeCard( @PathVariable UUID matchId ) {
@GetMapping("playerhand/{matchId}") public ResponseEntity playerHand( @PathVariable UUID matchId ) {
```
