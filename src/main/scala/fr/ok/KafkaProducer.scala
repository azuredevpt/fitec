package fr.ok

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.collection.JavaConverters._

object KafkaProducer {


  def main (args:Array[String]): Unit ={
    println("Hello, world!")

    val  props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")

    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    val TOPIC="jems"
    var i:Int=0;


    while(true){
      i+=1
      val record = new ProducerRecord(TOPIC, "key", s"toto $i")
      producer.send(record)
      Thread.sleep(1000) // wait for 1000 millisecond
      println("Envoi de message kafka")
    }


    producer.close()
  }


}
