import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema","true")csv("Sales.csv")
----------------------------------------------------------


//df.select(approx_count_distinct("Sales")).show()
//df.select(avg("Sales")).show()
//df.select(collect_list("Sales")).show()
//df.select(collect_set("sales")).show()
//df.select(corr($"Sales",$"Company")).show()
     ///df.select(corr($"Sales",$"Sales")).show()
//df.select(count("Sales")).show()
//df.select(countDistinct($"Sales",$"Company")).show()
    ///df.select(countDistinct($"Sales",$"Sales")).show()
//df.select(covar_pop($"Sales",$"Sales")).show()
//df.select(covar_samp($"Sales",$"Sales")).show()
//df.select(first("Sales")).show()
//df.select(kurtosis("Sales")).show()
//df.select(last("Sales")).show()
//df.select(skewness("Sales")).show()
//df.select(var_pop("Sales")).show()
//df.select(variance("Sales")).show()
//df.select(stddev("Sales")).show()