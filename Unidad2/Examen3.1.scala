import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
//cargamos el csv
val spark = SparkSession.builder().getOrCreate()
val df = spark.read.option("header","true").option("inferSchema", "true").format("csv").load("Iris.csv")
df.show()

//checamos los tipos de datos que contienel csv
df.printSchema()
import org.apache.spark.sql.types._
// le damos una estructura alos datos que usaremos 
val struct =
   StructType(
     StructField("SL", DoubleType, true) ::
     StructField("SW", DoubleType, true) ::
     StructField("PL", DoubleType, true) ::
     StructField("PW",DoubleType, true) ::
     StructField("Iris-setosa", StringType, true) :: Nil)


val df1 = spark.read.option("header", "false").schema(struct)csv("Iris.csv")

//val df1 = spark.read.option("header","false").format("csv").schema(struct).load("Iris.csv")
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer}

// Convertir strings a valores numericos
val labelIndexer = new StringIndexer().setInputCol("Iris-setosa").setOutputCol("labels")
// Convertir todas las columnas a una sola con vectorassembler escogemos solo las numericas
val VectAs = (new VectorAssembler().setInputCols(Array("SL","SW", "PL","PW")).setOutputCol("features"))
//sepawidth,petallength,petalwidth
//val splits = df.randomSplit(Array(0.6, 0.4), seed = 1234L)
//val train = splits(0)
//val test = splits(1)

val Array(training, test) = df1.randomSplit(Array(0.6, 0.4), seed = 1234L)
import org.apache.spark.ml.Pipeline
// capas de neuronas las primeras son las features y las ultimas 3 son las claes de salida
val layers = Array[Int](4, 5, 5, 3)

//val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setFeaturesCol("features").setBlockSize(128).setSeed(1234L).setMaxIter(100)
val multiPerc= new MultilayerPerceptronClassifier().setLayers(layers).setLabelCol("labels").setFeaturesCol("features").setPredictionCol("pred").setBlockSize(128).setSeed(1234L).setMaxIter(100)
//.setPredictionCol("pred")

val pipe = new Pipeline().setStages(Array(labelIndexer,VectAs,multiPerc))

// entrenamos el modelo
val model = pipe.fit(training)

//predic
val reslt = model.transform(test)

//
reslt.select("SL","SW", "PL","PW","Iris-setosa").show(10)
reslt.select("pred", "labels")
//
val predic = reslt.select("pred", "labels")
val evaluator = new MulticlassClassificationEvaluator().setLabelCol("labels").setPredictionCol("pred").setMetricName("accuracy")
val accuracy = evaluator.evaluate(predic)
//imprimimos la precicion y error  
println("Test set accuracy = " + accuracy)
println("Test Error = " + (1.0 - accuracy))





//creamos el entrenador y le damos los datos entrenar

// entrenamos el modelo
//val model = trainer.fit(train)