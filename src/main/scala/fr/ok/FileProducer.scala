package fr.ok
import java.util.Properties

import org.apache.kafka.clients.producer._
import java.io.File
import scala.io.Source
import java.nio.file.{Files, Paths, StandardCopyOption}
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.nio.charset.CodingErrorAction

object FileProducer{

def main(args:Array[String]): Unit ={
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")

  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val TOPIC = "topicDataRaw"

  val decoder = Charset.forName("UTF-8").newDecoder
  decoder.onMalformedInput(CodingErrorAction.IGNORE)
println("Kafka File Watcher is Running ...")
  while(true)
  {
    val  files = new File(args(0)).listFiles().filter(_.isFile)

    if(files.length> 0){
     // /devs/deploy/DATALAKE/DATA-RAW
      println("************************************************************")
      println("Nombre de fichiers trouvés "+ files.length)
      println("************************************************************")
      println("")
    }
    for( file <- files){

      //val producer = new KafkaProducer[String, String](props)
      println("------------------------------------------------------------")
      val nameFile = file.getName
      //for( line <- Source.fromFile(file).getLines()){
      println("Lecture du fichier "+ nameFile + " en cours")
          //val allText = Source.fromFile(file)(decoder).mkString
          //println("Envoi du fichier "+ nameFile )
          //val record = new ProducerRecord(TOPIC, nameFile, allText)
      println("Envoi du fichier Ligne par ligne"+ nameFile )
      for( line <- Source.fromFile(file).getLines()) {
        val record = new ProducerRecord(TOPIC, nameFile, line)
        println(line)
        producer.send(record)
        producer.flush()
      }
      //producer.close()
      //}

      println("Archivage du fichier "+ nameFile)
     val path = Files.move(
        Paths.get(file.getPath),
        Paths.get(args(1)+"/"+ nameFile),
        StandardCopyOption.REPLACE_EXISTING
      )
      println("------------------------------------------------------------")
      println("")
    }


    Thread.sleep(500)
  }
  producer.close()
}



}