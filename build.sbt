

name := "text-cluster"

version := "1.0"

scalaVersion := "2.9.1"

resolvers ++= Seq(
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
	"OpenNLP" at "http://opennlp.sourceforge.net/maven2"
)

libraryDependencies ++= Seq(
    "com.typesafe.akka" % "akka-actor" % "2.0",
	"org.apache.opennlp" % "opennlp-tools" % "1.5.2-incubating",
    "org.apache.opennlp" % "opennlp-maxent" % "3.0.2-incubating"
)
