import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.log4j._
import org.apache.spark.ml.Pipeline
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

// Convertir todas las columnas a una sola con vectorassembler escogemos solo las numericas
val assembler = (new VectorAssembler()
                  .setInputCols(Array("age","balance","day","duration","campaign","pdays","previous"))
                  .setOutputCol("features"))
//
val Array(training, test) = df.randomSplit(Array(0.5, 0.5), seed=1234L)

// capas de neuronas las primeras son las features y las ultimas 3 son las claes de salida
val layers = Array[Int](7, 5, 5, 3)
val layers = Array[Int](7, 5, 5, 2)

//val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setFeaturesCol("features").setBlockSize(128).setSeed(1234L).setMaxIter(100)
val mlp = new MultilayerPerceptronClassifier().setLayers(layers).setLabelCol("label").setFeaturesCol("features").setPredictionCol("prediction").setBlockSize(128).setSeed(1234L).setMaxIter(100)
//Utilizamos el metodo Pipeline para 
val pipe = new Pipeline().setStages(Array(labelIndexer,assembler,mlp))

val model = pipe.fit(training)

val result = modelo.transform(test)

//En base a los resultados obtenidos
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")

println("Test set accuracy = " + evaluator.evaluate(predictionAndLabels))