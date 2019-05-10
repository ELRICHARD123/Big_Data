
// Importe una  SparkSession con la libreria Logistic Regression

// Optional: Utilizar el codigo de  Error reporting

// Cree un sesion Spark 

// Utilice Spark para leer el archivo csv Advertising.

// Imprima el Schema del DataFrame


import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DateType
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

val spark = SparkSession.builder().getOrCreate()

val data  = spark.read.option("header","true").option("inferSchema", "true").format("csv").load("advertising.csv")

data.printSchema()
// Imprima un renglon de ejemplo 
data.head(1)
//pendiente

//   Hacer lo siguiente:
//    - Renombre la columna "Clicked on Ad" a "label"
//    - Tome la siguientes columnas como features "Daily Time Spent on Site","Age","Area Income","Daily Internet Usage","Timestamp","Male"
//    - Cree una nueva clolumna llamada "Hour" del Timestamp conteniendo la  "Hour of the click"
data.select("Clicked on Ad").show()
val timedata = data.withColumn("Hour",hour(data("Timestamp")))
val logregdataall = timedata.select(data("Clicked on Ad").as("label"),$"Daily Time Spent on Site",$"Age",$"Area Income",$"Daily Internet Usage",$"Hour",$"Male")
//val logregdataall = data.select(data("Clicked on Ad").as("label"),$"Daily Time Spent on Site",$"Age",$"Area Income",$"Daily Internet Usage",$"Male"$"Timestamp").cast(DateType).as("Hour")
val feature_data = data.select($"Daily Time Spent on Site",$"Age",$"Area Income",$"Daily Internet Usage",$"Timestamp",$"Male")
val logregdataal = (data.withColumn("Hour",hour(data("Timestamp")))
val logregdataal = logregdataall.na.drop()

////
// Importe VectorAssembler y Vectors
// Cree un nuevo objecto VectorAssembler llamado assembler para los feature
// Utilice randomSplit para crear datos de train y test divididos en 70/30
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors

val assembler = new VectorAssembler().setInputCols(Array("Daily Time Spent on Site","Age","Area Income","Daily Internet Usage","Hour","Male")).setOutputCol("features")
//val assembler = new VectorAssembler().setInputCols(Array("Area Income","Daily Internet Usage","Timestamp","Male")).setOutputCol("features")


val Array(training, test) = logregdataall.randomSplit(Array(0.7, 0.3), seed = 12345)

///////////////////////////////
// Configure un Pipeline ///////
/////////////////////////////

// Importe  Pipeline
// Cree un nuevo objeto de  LogisticRegression llamado lr

// Cree un nuevo  pipeline con los elementos: assembler, lr

// Ajuste (fit) el pipeline para el conjunto de training.


// Tome los Resultados en el conjuto Test con transform

////////////////////////////////////
//// Evaluacion del modelo /////////////
//////////////////////////////////

// Para Metrics y Evaluation importe MulticlassMetrics

// Convierta los resutalos de prueba (test) en RDD utilizando .as y .rdd

// Inicialice un objeto MulticlassMetrics 

// Imprima la  Confusion matrix
import org.apache.spark.ml.Pipeline

val lr = new LogisticRegression()

// val pipeline = new Pipeline().setStages(Array(genderIndexer,embarkIndexer,embarkEncoder,assembler,lr))
val pipeline = new Pipeline().setStages(Array(assembler,lr))

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
