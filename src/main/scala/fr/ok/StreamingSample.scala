package fr.ok


import java.sql.{Date, Timestamp}
import java.text.SimpleDateFormat

import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.catalyst.encoders.RowEncoder
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

//Hello cava
object StreamingSample extends App {

  //val spark = SparkSession.builder.appName("Spark-Kafka-Integration").master("local").getOrCreate()
  //spark.sparkContext.setLogLevel("ERROR")

  val df = spark

    .readStream

    .format("kafka")

    .option("kafka.bootstrap.servers", "localhost:9092")

    .option("subscribe", "topicDataRaw")

    .load()

  import spark.implicits._


  val df1 = df.selectExpr("CAST(value AS STRING)").as[String]

    .withColumn("date",split(col("value"),"\t")(0))
    .withColumn("ouv",split(col("value"),"\t")(1))
    .withColumn("haut",split(col("value"),"\t")(2))
    .withColumn("bas",split(col("value"),"\t")(3))
    .withColumn("clot",split(col("value"),"\t")(4))
    .withColumn("vol",split(col("value"),"\t")(5))
    .withColumn("devise",split(col("value"),"\t")(6))
    .drop("value")


  val nouveauSchema = StructType(Seq(
    StructField("date", TimestampType),
    StructField("ouv", DoubleType),//1
    StructField("haut", DoubleType),//2
    StructField("bas", DoubleType),//3
    StructField("clot", DoubleType),//4
    StructField("vol", DoubleType),//5
    StructField("devise", StringType)
  ))



  val encoder = RowEncoder(nouveauSchema)
  def transform(v:String):Double={

    try {
      v.toDouble
    } catch {
      case e: Exception => 0
    }
  }
  val DATE_FORMAT = "dd/MM/yyyy hh:mm"

  def getDateAsString(d: Date): String = {
    val dateFormat = new SimpleDateFormat(DATE_FORMAT)
    dateFormat.format(d)
  }

  def convertStringToDate(s: String): Timestamp = {
    val dateFormat = new SimpleDateFormat(DATE_FORMAT)
   return new java.sql.Timestamp(System.currentTimeMillis())

  }


  df1.printSchema()
  val nouveauDF =df1.map {
    (x:Row)=>{
      Row(convertStringToDate(x.getString(0)),transform(x.getString(1)),transform(x.getString(2)),transform(x.getString(3)),transform(x.getString(4)),transform(x.getString(5)),x(6))
    }
  } (encoder).toDF()





  //val avg_ouv = nouveauDF.groupBy("date").avg("ouv").withWatermark( "times", delayThreshold = "30 seconds")
 /*
  val avg_haut = nouveauDF.groupBy("haut").agg($"avg")
  val avg_bas = nouveauDF.groupBy("bas").agg($"avg")
  val avg_clos = nouveauDF.groupBy("clos").agg($"avg")
  val avg_vol = nouveauDF.groupBy("vol").agg($"avg")
*/

  val finalDF =nouveauDF.filter(nouveauDF.col("haut")>7)
  //val sp :SimpleProducer=new SimpleProducer()
  //sp.send("topicDataRaw2","toto")

 finalDF
    .writeStream
    //.format("console")
    //.option("truncate","true")
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("checkpointLocation", "/devs/deploy/checkpointlocation/dir")
    .option("topic", "topicDataRaw2")
    //.start()
    //.awaitTermination()
    .start()
   .awaitTermination()


/*    val myNewDF =finalDF
      .writeStream
      .format("console")
      .option("truncate","true")
      .start()
      .awaitTermination()*/




  //  df1.take(10).foreach(println)
}
