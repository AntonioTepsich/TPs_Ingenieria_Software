import Control.Exception
import System.IO.Unsafe

import Palet
import Route
import Stack
-- import Truck

testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()


main :: IO ()
main = do
  let 
    testPalet = [ 
        ("-- No acepta peso negativo", testF (newP "roma" (-1))), 
        ("-- No acepta ciudad vacía", testF (newP "" 1))
      ]

    longRoute = newR ["roma", "paris", "mdq", "berna"]
    palet1 = newP "roma" 1
    palet2 = newP "paris" 1
    palet3 = newP "mdq" 1
    arrayPalets = [palet1, palet2, palet3]
    
    testRoute =[ 
        ("-- No acepta lista vacía",       testF (newR [])),
        ("-- No acepta lista de una ciudad", testF (newR ["roma"])),
        ("-- No acepta ciudad no en la ruta", testF (inRouteR longRoute "berna") == False),
        ("-- Ciudad no en la ruta", testF (inOrderR longRoute "md" "mdq"))
      ]

    testStack = [
        ("-- No acepta pila de 0 celdas", testF (newS 0)),
        ("-- No acepta palet en pila llena", testF (stackS (newS 1) palet1))
      ]
    
  putStrLn "---Test Palet---"
  mapM_ printTest testPalet
  putStrLn "---Test Route---"
  mapM_ printTest testRoute
  putStrLn "---Test Stack---"
  mapM_ printTest testStack


    
printTest :: (String, Bool) -> IO ()
printTest (desc, ok) = putStrLn $ desc ++ ": " ++ if ok then "PASS" else "FAIL"


