
package fr.ok

import java.util.Properties
import org.apache.kafka.clients.producer._

import scala.collection.JavaConverters._

class SimpleProducer {


  def send (topic:String, _message : String): Unit ={
    println("Hello, world!")

    val  props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")

    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    val TOPIC= topic




    val record = new ProducerRecord(TOPIC, "key", _message)
    producer.send(record)



    producer.close()
  }


}
