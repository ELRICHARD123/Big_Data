///1/2
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
val df = spark.read.option("header", "true").option("inferSchema","true")csv("Netflix_2011_2016.csv")
//3
df.columns
//4
df.printSchema()
//5
df.select("Date","Open","High","Low","Close").show()
for(row <- df.head(5)){
    println(row)
}
//6
df.describe().show()
//7
val df1=df.withColumn("HV Ratio",df("High")/df("Volume"))
//8
df.orderBy($"High".desc).show()
//9
son el valor de las acciones de netflix que cerraron en dicho periodo de tiempo 
//10
df.select(max($"Volume")).show()
df.select(min($"Volume")).show()
//11
    //a
      df.filter($"Close"<600).count()

    //b
       (df.filter($"High">500).count() * 100)/1259  
    //c
    df.select(corr($"High",$"Volume")).show()
    //d
   val df2=df.withColumn("Year",year(df("Date")))
   val dfmax=df2.groupBy("Year").max()
   dfmax.select($"Year",$"max(High)").show()
   dfmax.select($"Year",$"max(High)").show(1)
   //e
   val dfmonth=df.withColumn("Month",month(df("Date")))
   val dfmean=dfmonth.select($"Month",$"Close").groupBy("Month").mean()
   dfmean.orderBy($"Month".desc).show()
   dfmean.orderBy($"Month").show()
 
:load nombredelarchio.scala