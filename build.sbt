name := "GlassTeaPot"

version := "0.00001"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.3.2",
  "org.apache.derby" % "derby" % "10.4.1.3",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)