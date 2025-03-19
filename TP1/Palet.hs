module Palet ( Palet, newP, destinationP, netP )
  where 

data Palet = Pal String Int deriving (Eq, Show)

newP :: String -> Int -> Palet   -- construye un Palet dada una ciudad de destino y un peso en toneladas
newP city load | load < 0 = error "El peso no puede ser negativo"
                 | city == "" = error "La ciudad no puede ser vacía"
                 | otherwise = Pal city load

destinationP :: Palet -> String  -- responde la ciudad destino del palet
destinationP (Pal city _) = city

netP :: Palet -> Int             -- responde el peso en toneladas del palet
netP (Pal _ load) = load
