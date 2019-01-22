package fr.ok

import java.util
import java.util.Properties
import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.io._

object FileConsumer {

  def main(args : Array[String]): Unit ={

    val TOPIC="topicDataRaw"

    //  LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).asInstanceOf[Logger]
    //  rootLogger.setLevel(Level.OFF)
    //setLevel(Level.ERROR)
    val  props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")

    props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "something")

    val consumer = new KafkaConsumer[String, String](props)

    consumer.subscribe(util.Collections.singletonList(TOPIC))

    while(true){
      val records=consumer.poll(100)
      Thread.sleep(1)
      for (record<-records.asScala){
        println("reception de fichier")
        val file = new File(args(0)+"/" + record.key())
        val bw = new BufferedWriter(new FileWriter(file))
        bw.write(record.value())
        bw.close()
      }
    }


  }
}
