module Main where

import Palet
import Route
import Stack
import Truck

main :: IO ()
main = do
    putStrLn "========== TESTEANDO LA APLICACIÓN ==========\n"

    -- 1. Definir una ruta
    let r = newR ["Buenos Aires", "Rosario", "Cordoba"]
    putStrLn $ "Ruta de prueba: " ++ show r

    -- 2. Crear palets
    let p1 = newP "Buenos Aires" 2
    let p2 = newP "Cordoba" 3
    let p3 = newP "Rosario" 5
    putStrLn $ "\nPalets creados:\n  " ++ show p1
        ++ "\n  " ++ show p2
        ++ "\n  " ++ show p3

    -- 3. Construir un camión con 2 bahías, cada una de capacidad 2
    let t = newT 2 2 r
    putStrLn $ "\nCamion inicial:\n  " ++ show t
    putStrLn $ "Celdas libres en T: " ++ show (freeCellsT t)
    putStrLn $ "Peso total en T:    " ++ show (netT t)

    -- 4. Cargar el primer palet (p1)
    let t1 = loadT t p1
    putStrLn $ "\nDespués de cargar p1 ('Buenos Aires' 2):\n  " ++ show t1
    putStrLn $ "Celdas libres: " ++ show (freeCellsT t1)
    putStrLn $ "Peso total:    " ++ show (netT t1)

    -- 5. Cargar el segundo palet (p2)
    let t2 = loadT t1 p2
    putStrLn $ "\nDespués de cargar p2 ('Cordoba' 3):\n  " ++ show t2
    putStrLn $ "Celdas libres: " ++ show (freeCellsT t2)
    putStrLn $ "Peso total:    " ++ show (netT t2)

    -- 6. Cargar el tercer palet (p3)
    let t3 = loadT t2 p3
    putStrLn $ "\nDespués de cargar p3 ('Rosario' 5):\n  " ++ show t3
    putStrLn $ "Celdas libres: " ++ show (freeCellsT t3)
    putStrLn $ "Peso total:    " ++ show (netT t3)

    -- 7. Descargar en "Buenos Aires"
    let t4 = unloadT t3 "Buenos Aires"
    putStrLn $ "\nDespués de descargar en 'Buenos Aires':\n  " ++ show t4
    putStrLn $ "Celdas libres: " ++ show (freeCellsT t4)
    putStrLn $ "Peso total:    " ++ show (netT t4)

    -- 8. Descargar en "Cordoba"
    let t5 = unloadT t4 "Cordoba"
    putStrLn $ "\nDespués de descargar en 'Cordoba':\n  " ++ show t5
    putStrLn $ "Celdas libres: " ++ show (freeCellsT t5)
    putStrLn $ "Peso total:    " ++ show (netT t5)

    putStrLn "\n========== FIN DEL TEST =========="
