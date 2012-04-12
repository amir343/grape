

name := "grape"

version := "1.0"

scalaVersion := "2.9.1"

resolvers ++= Seq(
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
	"OpenNLP" at "http://opennlp.sourceforge.net/maven2"
)

libraryDependencies ++= Seq(
    "com.typesafe.akka" % "akka-actor" % "2.0",
    "org.scalaz" %% "scalaz-core" % "6.0.4",
	"org.apache.opennlp" % "opennlp-tools" % "1.5.2-incubating",
    "org.apache.opennlp" % "opennlp-maxent" % "3.0.2-incubating",
    "commons-io" % "commons-io" % "2.2",
    "org.slf4j" % "slf4j-api" % "1.6.4",
    "org.slf4j" % "slf4j-simple" % "1.6.4",
    "com.weiglewilczek.slf4s" %% "slf4s" % "1.0.7",
    "org.specs2" %% "specs2" % "1.9" % "test"
)
