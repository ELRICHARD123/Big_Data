import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.log4j._
//Quita muchos warnings
// pa que no salgan los warning
Logger.getLogger("org").setLevel(Level.ERROR)
// cargamos el csv 
val spark = SparkSession.builder().getOrCreate()
val df = spark.read.option("header","true").option("inferSchema","true").csv("bank-full.csv")
df.show()

//checamos los tipos de datos que contienel csv
df.printSchema()
//importamos mas cosa
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
// Convertir strings a valores numericos
val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("label").fit(df)

val labelEncoder = new OneHotEncoder().setInputCol("label").setOutputCol("labelVec")
val assembler = (new VectorAssembler()
                  .setInputCols(Array("age","balance","day","duration","campaign","pdays","previous"))
                  .setOutputCol("features"))


val Array(training, test) = df.randomSplit(Array(0.5, 0.5), seed = 12345)

import org.apache.spark.ml.Pipeline

val lr = new LogisticRegression()

// val pipeline = new Pipeline().setStages(Array(genderIndexer,embarkIndexer,embarkEncoder,assembler,lr))
val pipeline = new Pipeline().setStages(Array(labelIndexer,labelEncoder,assembler,lr))

val model = pipeline.fit(training)

val results = model.transform(test)


//Probar el modelo solo se puede con la libreria vieja
import org.apache.spark.mllib.evaluation.MulticlassMetrics

val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

// Matriz de confusion
println("Confusion matrix:")
println(metrics.confusionMatrix)

metrics.accuracy
