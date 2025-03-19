module Route ( Route, newR, inOrderR, inRouteR )
   where

data Route = Rou [ String ] deriving (Eq, Show)



newR :: [ String ] -> Route -- construye una ruta según una lista de ciudades
newR ciudades   | ciudades == [] = error "La ruta no puede ser vacía"
                | otherwise = Rou ciudades


position :: [String] -> String -> Int
position [] _ = -1
position (x:xs) c 
    | x == c                 = 0
    | position xs c == -1    = -1
    | otherwise              = 1 + position xs c

inRouteR :: Route -> String -> Bool -- indica si la ciudad consultada está en la ruta
inRouteR (Rou cs) c | position cs c == -1 = False
                    | otherwise = True

inOrderR :: Route -> String -> String -> Bool -- indica si la primer ciudad consultada está antes que la segunda ciudad en la ruta
inOrderR (Rou cs) c1 c2 | position cs c1 == -1 || position cs c2 == -1 = error "La ciudad no está en la ruta"
                        | otherwise = position cs c1 < position cs c2