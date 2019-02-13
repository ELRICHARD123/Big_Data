//1 numeros primos
scala> def Primo(num:Int): Boolean={
     | if(num%2==0){
     | return true }
     | else{
     | return false}
     | }
Primo: (num: Int)Boolean

scala> Primo(20)
res26: Boolean = true

scala> Primo(4)
res27: Boolean = true

scala> Primo(3)
res28: Boolean = false

///2 lista pares
///checar50/50
scala> def checar(num:List[Int]): Boolean={
     | return(num(1)%2==0)}
checar: (num: List[Int])Boolean

scala> println(checar(num))
false

scala> val num=List(2,5,7,8)
num: List[Int] = List(2, 5, 7, 8)

scala> checar(num)
res1: Boolean = false

//3
scala> def afo(list:List[Int]): Int={
     | var r=0
     | for(n<- list){
     | if(n==7){
     | r=r+14
     | }else{
     | r=r+n
     | }
     | }
     | return r
     | }
afo: (list: List[Int])Int

scala> val x=List(7,1)
x: List[Int] = List(7, 1)

scala> afo(x)
res5: Int = 15


////4
scala> def eqi(l:List[Int]): Boolean={
     | var p=0
     | var s=0
     | s=l.sum
     | for(i<-Range(0,l.length)){
     | p=p+l(i)
     | s=s-l(i)
     | if(p==s){
     | return true 
     | }
     | }
     | return false
     | }
eqi: (l: List[Int])Boolean

scala> val q=List(1,5,3,3)
q: List[Int] = List(1, 5, 3, 3)

scala> eqi(q)
res5: Boolean = true



////5
scala> def backw(e:String):Boolean={
     | return(e==e.reverse)
     | }
backw: (e: String)Boolean

scala> val v="anitalavalatina"
v: String = anitalavalatina

scala> println(backw(v))
true