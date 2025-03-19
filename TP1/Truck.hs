module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where

import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT bahias cap route   | bahias < 0 = error "La cantidad de bahias no puede ser negativa!!"
                        | otherwise = Tru [newS cap | _ <- [1..bahias]] route -- cap verificado en newS TODO: VERIFICAR SI NO ES INCONSISTENTE HACER ESTO

sumarArray :: [Int] -> Int
sumarArray [] = 0
sumarArray (x:xs) = x + sumarArray xs

freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru stacks _) = sumarArray [freeCellsS stack | stack <- stacks]

cargarPrimerStack :: [Stack] -> Palet -> Route -> [Stack]
cargarPrimerStack [] _ _ = error "No se pudo cargar el palet"
cargarPrimerStack (stack:stacks) pal route | holdsS stack pal route = stackS stack pal : stacks     -- Si la pila puede aceptar el palet, lo apilo
                                           | otherwise = stack : cargarPrimerStack stacks pal route -- Si no, sigo buscando

loadT :: Truck -> Palet -> Truck      -- carga un palet en el camion
loadT (Tru stacks route) pal = Tru newStacks route
  where
    newStacks = cargarPrimerStack stacks pal route

unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargarse en la ciudad
unloadT (Tru stacks route) ciudad = Tru [popS stack ciudad | stack <- stacks] route

netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
netT (Tru stacks _) = sumarArray [netS stack | stack <- stacks]