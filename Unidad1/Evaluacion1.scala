//1 numeros primos

scala> def Primo(num:Int): Boolean={
      if(num%2==0){
      return true }
      else{
      return false}
      }
Primo: (num: Int)Boolean

scala> Primo(20)
res26: Boolean = true

scala> Primo(4)
res27: Boolean = true

scala> Primo(3)
res28: Boolean = false

///2 
def lista(list:List[Int]): String ={
    for(n <- list){
        if(n%2==0){
            println(s"$n es par")
        }else{
            println(s"$n es inpar")
        }
    }
    return "Done"
}
val x = List(1,2,3,4,5,6,7,8)
val y = List(4,3,22,55,7,8)





//3
 def afo(list:List[Int]): Int={
      var r=0
      for(n<- list){
      if(n==7){
      r=r+14
      }else{
      r=r+n
      }
      }
      return r
      }
afo: (list: List[Int])Int

scala> val x=List(7,1)
x: List[Int] = List(7, 1)

scala> afo(x)
res5: Int = 15


////4
scala> def eqi(l:List[Int]): Boolean={
      var p=0
      var s=0
      s=l.sum
      for(i<-Range(0,l.length)){
      p=p+l(i)
      s=s-l(i)
      if(p==s){
      return true 
      }
      }
      return false
      }
eqi: (l: List[Int])Boolean

scala> val q=List(1,5,3,3)
q: List[Int] = List(1, 5, 3, 3)

scala> eqi(q)
res5: Boolean = true

////5
scala> def backw(e:String):Boolean={
      return(e==e.reverse)
      }
backw: (e: String)Boolean

scala> val v="anitalavalatina"
v: String = anitalavalatina

scala> println(backw(v))
true