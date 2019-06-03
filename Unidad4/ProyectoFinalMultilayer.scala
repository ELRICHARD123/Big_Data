import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
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
// Convertir todas las columnas a una sola con vectorassembler escogemos solo las numericas
val assembler = (new VectorAssembler()
                  .setInputCols(Array("age","balance","day","duration","campaign","pdays","previous"))
                  .setOutputCol("features"))

val Array(training, test) = df.randomSplit(Array(0.7, 0.3), seed=1234L)
import org.apache.spark.ml.Pipeline
// capas de neuronas las primeras son las features y las ultimas 3 son las claes de salida
val layers = Array[Int](4, 5, 5, 3)

//val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setFeaturesCol("features").setBlockSize(128).setSeed(1234L).setMaxIter(100)
val multiPerc = new MultilayerPerceptronClassifier().setLayers(capas).setLabelCol("label").setFeaturesCol("features").setPredictionCol("prediction").setBlockSize(128).setSeed(1234L).setMaxIter(100)
//Utilizamos el metodo Pipeline para 
val pipe = new Pipeline().setStages(Array(labelIndexer,assembler,multiPerc))

val model = pipe.fit(trainingData)

val result = model.transform(testData)

//En base a los resultados obtenidos
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")

println("Test set accuracy = " + evaluator.evaluate(predictionAndLabels))

