-- Main.hs
module Main where

import Palet
import Route
import Stack
import Truck

main :: IO ()
main = do
  -- Crear una ruta:
  let ruta = newR ["CiudadA", "CiudadB", "CiudadC"]
  
  -- Crear un camión con 3 bahías y altura 2:
  let camion = newT 3 2 ruta
  
  -- Crear un palet para CiudadB:
  let palet1 = newP "CiudadB" 5
  
  -- Cargar el palet:
  let camion1 = loadT camion palet1
  
  -- Imprimir el estado del camión:
  print camion1
  
  -- Simular la descarga en CiudadB:
  let camion2 = unloadT camion1 "CiudadB"
  
  print camion2
