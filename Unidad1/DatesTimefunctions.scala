import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("CitiGroup2006_2008")
-------------------------------------------------------------
///df.select(add_months($"Date", 7)).show()
///df.select(date_add($"Date",1)).show()
//df.select(datediff($"Date",$"Date")).show()
//df.select(last_day($"Date")).show()
//df.select(unix_timestamp($"Date")).show()
//df.select(to_date($"Date")).show()
//df.select(second($"Date")).show()
//df.select(quarter($"Date")).show()
//df.select(next_day($"Date","2006-01-27")).show()
//df.select(months_between($"Date",$"Date")).show()
//df.select(hour($"Date")).show()
df.select(minute($"Date")).show()
df.select(dayofmonth($"Date")).show()
df.select(dayofyear($"Date")).show()
df.select(dayofweek($"Date")).show()