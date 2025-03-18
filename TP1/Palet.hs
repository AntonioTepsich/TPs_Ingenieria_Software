module Palet ( Palet, newP, destinationP, netP ) -- C: declaramos el módulo
  where -- C: la lista dice a qué funciones pueden acceder otros módulos

data Palet = Pal String Int deriving (Eq, Show)
-- C: define el tipo de dato Palet 
-- C: el String es la ciudad destino, el Int el peso en toneladas
-- C: comparar palets (Eq) y mostrarlos como texto (Show)

-- C: comienzan las funciones:
newP :: String -> Int -> Palet   -- construye un Palet dada una ciudad de destino y un peso en toneladas
-- C: recibe el string y el int, y devuelve el palet creado
-- C: newP city load = Pal city load me lo corrige vs code
newP = Pal

destinationP :: Palet -> String  -- responde la ciudad destino del palet
-- C: dado un palet, devuelve la ciudad
destinationP (Pal city _) = city

netP :: Palet -> Int             -- responde el peso en toneladas del palet
-- C: dado un palet, devuelve el peso
netP (Pal _ load) = load
