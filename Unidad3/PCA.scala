//Libreria spark

import org.apache.spark.sql.SparkSession
// cargamos el csv 
val spark = SparkSession.builder().appName("PCA_Example").getOrCreate()
val data = spark.read.option("header","true").option("inferSchema","true").format("csv").load("Cancer_Data")
//Imprimimos el schema para ver los tipos de datos
data.printSchema()

//Libreria spark para usar en mi assembler
import org.apache.spark.ml.feature.{PCA,StandardScaler,VectorAssembler}
import org.apache.spark.ml.linalg.Vectors
//colocamos en un areglo las columnas 
val colnames = (Array("mean radius", "mean texture", "mean perimeter", "mean area", "mean smoothness",
"mean compactness", "mean concavity", "mean concave points", "mean symmetry", "mean fractal dimension",
"radius error", "texture error", "perimeter error", "area error", "smoothness error", "compactness error",
"concavity error", "concave points error", "symmetry error", "fractal dimension error", "worst radius",
"worst texture", "worst perimeter", "worst area", "worst smoothness", "worst compactness", "worst concavity",
"worst concave points", "worst symmetry", "worst fractal dimension"))
//le damos las columnas que se llama colnames que vamos a entrena 
val assembler = new VectorAssembler().setInputCols(colnames).setOutputCol("features")

val output = assembler.transform(data).select($"features")

// colocamos los datos de entrada para luego fitearlos dentro del modelo 
val scaler = (new StandardScaler()
  .setInputCol("features")
  .setOutputCol("scaledFeatures")
  .setWithStd(true)
  .setWithMean(false))
val scalerModel = scaler.fit(output)

//transformamos la data del model para comvertir een data para fitearlo en el PCA
val scaledData = scalerModel.transform(output)
//ponemos como los parametros los scaledfeature  y los fiteamos 
val pca = (new PCA()
  .setInputCol("scaledFeatures")
  .setOutputCol("pcaFeatures")
  .setK(4)
  .fit(scaledData))

val pcaDF = pca.transform(scaledData)
//imprimimos en resultado
val result = pcaDF.select("pcaFeatures")
result.show()

result.head(1)
