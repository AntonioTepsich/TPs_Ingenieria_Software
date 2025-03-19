module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS )
  where

import Palet
import Route

data Stack = Sta [ Palet ] Int deriving (Eq, Show)


newS :: Int -> Stack                      -- construye una Pila con la capacidad indicada 
newS cap  | cap < 0 = error "La capacidad de la pila no puede ser negativa!!"
          | otherwise = Sta [] cap

freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
freeCellsS (Sta palets cap) = cap - length palets 


-- Condiciones verificadas en holdsS
stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
stackS (Sta palets cap) pal = Sta (pal : palets) cap
    

netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
netS (Sta [] _) = 0
netS (Sta (x:xs) cap) = netP x + netS (Sta xs cap)

holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS (Sta palets cap) pal route = 
  (freeCellsS (Sta palets cap) > 0)           -- Que haya lugar en la pila 
  && (netS (Sta palets cap) + netP pal <= 10) -- Que el peso neto de la pila mas el peso del palet no supere los 10
  && (palets == [] || not (inOrderR route (destinationP (head palets)) (destinationP pal))) -- Que el palet a apilar no tenga destino antes que el tope de la pila
  


popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada
popS (Sta [] cap) _ = Sta [] cap
popS (Sta (x:xs) cap) ciudad | destinationP x == ciudad = popS (Sta xs cap) ciudad  -- Si el destino del palet es la ciudad, lo saco
                             --TODO: VERIFICAR SI NO VA UN ERROR
                             | otherwise = Sta (x:xs) cap                          -- Si no, lo dejo 
