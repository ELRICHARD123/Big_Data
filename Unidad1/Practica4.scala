


algoritmo de fibonacci 1
scala> def fib(n:Int): Int={
     | if(n<2){
     | return n }
     | else { return (n-1)+(n-2)}
     | }
fib: (n: Int)Int

scala> fib(1)
res26: Int = 1

scala> fib(2)
res27: Int = 1

scala> fib(5)
res28: Int = 7

Algoritmo 2
scala> def fun(n:Double):Double={
     | if(n<2){
     | return n }
     | else {
     | var p =((1+math.sqrt(5))/2)
     | var j =((math.pow(p,n)-math.pow((1-p),n))/math.sqrt(5))
     | return j }
     | }
fun: (n: Double)Double

scala> fun(5)
res3: Double = 5.000000000000001

Algoritmo 3
scala> def fig(x:Int):Int={
     | var a =0
     | var b=1
     | var c=0
     | var k=0
     | for(k<-1 to x){
     | println(a)
     | c=b+a
     | a=b
     | b=c}
     | return(x)
     | }
fig: (x: Int)Int

scala> fig(15)
0
1
1
2
3
5
8
13
21
34
55
89
144
233
377
res1: Int = 15

scala>

Algoritmo 4

scala> def fig(x:Int):Int={
     | var a=0
     | var b=1
     | var k=0
     | for(k<-1 to x){
     | println(a)
     | b=b+a
     | a=b-a
     | }
     | return(x)
     | }
fig: (x: Int)Int

scala> fig(15)
0
1
1
2
3
5
8

