import org.apache.spark.mllib.classification.{SVMModel, SVMWithSGD}
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.util.MLUtils
// $example off$
import org.apache.log4j._
//Quita muchos warnings
// pa que no salgan los warning
Logger.getLogger("org").setLevel(Level.ERROR)
// cargamos el csv 
val spark = SparkSession.builder().getOrCreate()
val df = spark.read.option("header","true").option("inferSchema","true").csv("bank-full.csv")
df.show()

import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}

// Convertir strings a valores numericos
val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("label").fit(df)
// Convertir todas las columnas a una sola con vectorassembler escogemos solo las numericas
val assembler = (new VectorAssembler()
                  .setInputCols(Array("age","balance","day","duration","campaign","pdays","previous"))
                  .setOutputCol("features"))

val Array(training, test) = df.randomSplit(Array(0.5, 0.5), seed = 11L)

import org.apache.spark.ml.classification.LinearSVC

import org.apache.spark.ml.Pipeline
val lsvc = new LinearSVC().setLabelCol("label").setFeaturesCol("features").setPredictionCol("prediction").setMaxIter(10).setRegParam(0.1)
val pipe = new Pipeline().setStages(Array(labelIndexer,assembler,lsvc))

val model = pipe.fit(training)

val result = model.transform(test)
//val predictionAndLabels = result.select("prediction", "label")

//Probar el modelo solo se puede con la libreria vieja
import org.apache.spark.mllib.evaluation.MulticlassMetrics
val predictionAndLabels = result.select($"prediction",$"label").as[(Double, Double)].rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

// Matriz de confusion
println("Confusion matrix:")
println(metrics.confusionMatrix)

metrics.accuracy

