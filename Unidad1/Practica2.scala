



//1)Desarrollar un algoritmo en scala que calcule el radio de un círculo
var diametro=15
//diametro: Int = 15

diametro/2
//res0: Int = 7
//2)Desarrollar un algoritmo en scala que me diga si un número es primo

def Primo(Num1:Int):Int={
     |     var sobra = Num1%2
     |     if(sobra == 0){
     |         println(s"El numero $Num1 no es primo")
     |     }
     |     else{
     |        println(s"El numero $Num1 es primo") 
     |     }
     |     return Num1
     | }
Primo: (Num1: Int)Int

Primo(13)
//El numero 13 es primo
//res53: Int = 15



///3)Dada la variable bird = "tweet", utiliza interpolación de string para // imprimir "Estoy escribiendo un tweet"
if(bird=="tweet"){
println("Estoy escribiendo un tweet")
}
Estoy escribiendo un tweet



4) Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slice para extraer la // secuencia "Luke"
val st="Hola Luke yo soy tu padre!"
st: String = Hola Luke yo soy tu padre!

scala> st slice(5,9)
res12: String = Luke

5) Cual es la diferencia en value y una variable en scala?
Las variables mutables se describen con la palabra clave "var". A diferencia de val, "var" se puede reasignar a valores diferentes o apuntar a objetos diferentes. Pero tienen que ser inicializados en el momento de la declaración.
scala> val x=1
x: Int = 1

scala> x=2
<console>:25: error: reassignment to val
       x=2
        ^

scala> var x=2
x: Int = 2

scala> x=3
x: Int = 3

6) Dada la tupla ((2,4,5),(1,2,3),(3.1416,23))) regresa el número 3.1416 
scala> val Tupla=((2,4,5),(1,2,3),(3.1416,23))
Tupla: ((Int, Int, Int), (Int, Int, Int), (Double, Int)) = ((2,4,5),(1,2,3),(3.1416,23))

scala> Tupla._3._1
res25: Double = 3.1416

