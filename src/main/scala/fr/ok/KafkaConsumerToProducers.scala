package fr.ok

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.collection.JavaConverters._

object KafkaConsumerToProducers extends App {
  val TOPIC="macron"

  //  LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).asInstanceOf[Logger]
  //  rootLogger.setLevel(Level.OFF)
  //setLevel(Level.ERROR)
  val  props = new Properties()
  props.put("bootstrap.servers", "10.9.254.20:9092")

  props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "something")

  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val consumer = new KafkaConsumer[String, String](props)

  consumer.subscribe(util.Collections.singletonList(TOPIC))

  val producer = new KafkaProducer[String, String](props)

  val TOPIC2="twitter_flux"
  var i:Int=0;



  while(true){
    val records=consumer.poll(100)
    Thread.sleep(1)
    for (record<-records.asScala){
      println(record.value)
      val record2 = new ProducerRecord(TOPIC2, "key", record.value)
      producer.send(record2)
    }
  }

}
