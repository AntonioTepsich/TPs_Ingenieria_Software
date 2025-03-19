factorial 0 = 1
factorial n = n * factorial (n-1)

divisores n = [x | x <- [1..n],mod n x == 0]

esNumPrimo n = length (divisores n) == 2
             
cantDivisoresPrimos n = length([x| x <- [1..n], esNumPrimo x])

aEntero (Left num) = num
aEntero (Right True) = 1
aEntero (Right False) = 0





---4---
promedio :: [Float] -> Float
promedio a = sum a / fromIntegral(length a)


-- diffPromedio :: [Float] -> [Float]
-- diffPromedio a [x|x]


---5---
data AB a = Nill | Bin (AB a) a (AB a)

nullTree :: AB a -> Bool
nullTree Nill = True
nullTree _ = False

negTree :: AB Bool -> AB Bool
negTree Nill = Nill
negTree (Bin izq val der) = Bin (negTree izq) (not val) (negTree der)

prodTree :: AB Int -> Int
prodTree Nill = 1
prodTree (Bin izq val der) = val * prodTree izq * prodTree izq



---9---
pitagoricas :: [(Integer,Integer,Integer)]
pitagoricas = [(a,b,c) | c <- [1..], a <- [1..c], b <-[1..c], a^2 + b^2 == c^2]


--11--
-- partir::[a] -> [([a],[a])]
-- partir s = [take x xs, dropt x xs | x:xs]

