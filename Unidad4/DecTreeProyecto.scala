import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
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

// Automatically identify categorical features, and index them.
//val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("features").setMaxCategories(4).fit(df)

// Split the data into training and test sets (70% held out for testing).
val Array(trainingData, testData) = df.randomSplit(Array(0.5, 0.5))

// entrenamos el moddelo.
val dt = new DecisionTreeClassifier().setLabelCol("label").setFeaturesCol("features")

  dt.setLabelCol("label")
  dt.setFeaturesCol("features")

// Convert indexed labels back to original labels.
val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)

// Chain indexers and tree in a Pipeline.
val pipeline = new Pipeline().setStages(Array(labelIndexer, assembler, dt, labelConverter))

// Train model. This also runs the indexers.
val model = pipeline.fit(trainingData)

// Make predictions.
val predictions = model.transform(testData)

// Select example rows to display.
predictions.select("predictedLabel", "label", "features").show(5)

// Select (prediction, true label) and compute test error.
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println("Test Error = " + (1.0 - accuracy))

val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
println("Learned classification tree model:\n" + treeModel.toDebugString)