# TP 1

## Delivery

Modelamos el camión de una compañía de transporte de materiales de construcción en palets.
El camión tiene una ruta establecida y una cantidad de bahías para estiba.
Todas las bahías de un camión toleran la misma cantidad de palets apilados.

Al momento de llegar a uno de los destinos de su ruta tiene que poder descargar los palets con ese destino simplemente desapilando.
Con lo cual al momento de cargar no puede apilarse un palet con destino posterior al que le queda de bajo.
La carga de un palet se hace solo si hay una bahía que lo acepte

Los palets tienen destino y peso en toneladas.
Una bahía no tolera apilar más de 10 toneladas

Al llegar a un destino el camión descarga todos los palets con ese destino 
luego puede cargar mas palets, 
y luego queda listo para partir al siguiente destino.

Para sostener este modelo se cuenta con las siguientes entidades:
(nada de esto se puede modificar)

``` haskell

module Palet ( Palet, newP, destinationP, netP )
  where

data Palet = Pal String Int deriving (Eq, Show)

newP :: String -> Int -> Palet   -- construye un Palet dada una ciudad de destino y un peso en toneladas
destinationP :: Palet -> String  -- responde la ciudad destino del palet
netP :: Palet -> Int             -- responde el peso en toneladas del palet

```

------------------------

``` haskell 
module Route ( Route, newR, inOrderR, inRouteR )
   where

data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route -- construye una ruta según una lista de ciudades
inOrderR :: Route -> String -> String -> Bool -- indica si la primer ciudad consultada está antes que la segunda ciudad en la ruta
inRouteR :: Route -> String -> Bool -- indica si la ciudad consultada está en la ruta


```

------------------------
``` haskell
module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS )
  where

import Palet
import Route

data Stack = Sta [ Palet ] Int deriving (Eq, Show)

newS :: Int -> Stack                      -- construye una Pila con la capacidad indicada 
freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada

```

------------------------
``` haskell
module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )
  where

import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camión según una cantidad de bahías, la altura de las mismas y una ruta
freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
loadT :: Truck -> Palet -> Truck      -- carga un palet en el camion
unloadT :: Truck -> String -> Truck   -- responde un camión al que se le han descargado los paletes que podían descargarse en la ciudad
netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion

```