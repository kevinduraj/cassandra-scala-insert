name := "cassandra-scala-insert"

version := "0.0.1"

scalaVersion := "2.11.8"

val cassandraDriver = "3.3.0"

libraryDependencies ++= Seq(
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.3.0" //,
)


/*
 * References:
 * https://mvnrepository.com/artifact/com.datastax.cassandra/cassandra-driver-core
 */
