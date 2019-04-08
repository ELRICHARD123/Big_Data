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
// 
val struct =
   StructType(
     StructField("5.1", DoubleType, true) ::
     StructField("3.5", DoubleType, true) ::
     StructField("1.4", DoubleType, true) ::
     StructField("0.2",DoubleType, true) ::
     StructField("Iris-setosa", StringType, true) :: Nil)


val df1 = spark.read.option("header","false").schema(struct).load("Iris.csv")
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer}

// Convertir strings a valores numericos
val labelIndexer = new StringIndexer().setInputCol("Iris-setosa").setOutputCol("labels")
// Convertir todas las columnas a una sola con vectorassembler escogemos solo las numericas
val VectAs = (new VectorAssembler().setInputCols(Array("5.1","3.5", "1.4","0.2")).setOutputCol("features"))
//sepawidth,petallength,petalwidth
//val splits = df.randomSplit(Array(0.6, 0.4), seed = 1234L)
//val train = splits(0)
//val test = splits(1)

val Array(training, test) = df1.randomSplit(Array(0.7, 0.3), seed = 12345)
import org.apache.spark.ml.Pipeline
// capas de neuronas 
val layers = Array[Int](3, 2, 3, 3)

//val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setFeaturesCol("features").setBlockSize(128).setSeed(1234L).setMaxIter(100)
val multiPerc= new MultilayerPerceptronClassifier().setLayers(layers).setLabelCol("labels").setFeaturesCol("features").setBlockSize(128).setSeed(12345).setMaxIter(100)


val pipe = new Pipeline().setStages(Array(labelIndexer,VectAs,multiPerc))

// entrenamos el modelo
val model = multiPerc.fit(training)

val results = model.transform(test)



//creamos el entrenador y le damos los datos entrenar

// entrenamos el modelo
//val model = trainer.fit(train)