module Route ( Route, newR, inOrderR )
  where

data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route                    -- construye una ruta segun una lista de ciudades
inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta

newR nombre_rutas = Rou nombre_rutas

-- Función auxiliar que busca el índice de un elemento en una lista.
-- Retorna Just índice si lo encuentra, o Nothing si no.
elemIndex :: Eq a => a -> [a] -> Maybe Int
elemIndex _ [] = Nothing
elemIndex x (y:ys)
  | x == y    = Just 0
  | otherwise = case elemIndex x ys of
                  Nothing -> Nothing
                  Just n  -> Just (n + 1)

-- Obtiene el índice de una ciudad dentro de la ruta.
cityIndex :: Route -> String -> Maybe Int
cityIndex (Rou cities) city = elemIndex city cities

-- Comprueba si 'ciudad1' aparece antes que 'ciudad2' en la ruta.
inOrderR route city1 city2
  | Just i1 <- cityIndex route city1, Just i2 <- cityIndex route city2 = i1 < i2
  | otherwise = False