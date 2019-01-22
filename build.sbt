/*
name := "Kafka_For_Kafka"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  // The exclude of slf4j-simple is because it overlaps with our use of logback with slf4j facade;  without the exclude
  // we get slf4j warnings and logback's configuration is not picked up.
  "org.apache.kafka" % "kafka-clients" % "0.10.1.0"
    exclude("javax.jms", "jms")
    exclude("com.sun.jdmk", "jmxtools")
    exclude("com.sun.jmx", "jmxri")
    exclude("org.slf4j", "slf4j-simple"),
  // Logback with slf4j facade
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "ch.qos.logback" % "logback-core" % "1.0.13",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "com.typesafe.play" %% "play-json" % "2.7.0",
  "org.apache.spark" %% "spark-sql" % "2.4.0",
"org.apache.spark" % "spark-sql-kafka-0-10_2.11" % "2.2.0"



)

mainClass in (Compile, run) := Some("fr.ok.KafkaProducer")
mainClass in (Compile, packageBin) := Some("fr.ok.KafkaProducer")*/
name := "kafkaproject"

version := "0.1"

scalaVersion := "2.11.0"

/*
//kafka-clients

libraryDependencies ++= Seq(
  // The excludes of jms, jmxtools and jmxri are required as per https://issues.apache.org/jira/browse/KAFKA-974.
  // The exclude of slf4j-simple is because it overlaps with our use of logback with slf4j facade;  without the exclude
  // we get slf4j warnings and logback's configuration is not picked up.
  "org.apache.kafka" % "kafka-clients" % "0.10.1.0"
    exclude("javax.jms", "jms")
    exclude("com.sun.jdmk", "jmxtools")
    exclude("com.sun.jmx", "jmxri")
    exclude("org.slf4j", "slf4j-simple"),
 //  Logback with slf4j facade
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "ch.qos.logback" % "logback-core" % "1.0.13",
  "org.slf4j" % "slf4j-api" % "1.7.5"
)
*/


libraryDependencies ++= Seq("org.apache.spark" %% "spark-sql" % "2.2.0",

  "org.apache.spark" % "spark-sql-kafka-0-10_2.11" % "2.2.0",

  "org.apache.kafka" % "kafka-clients" % "0.11.0.1")

