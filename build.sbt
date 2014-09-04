name := "GlassTeaPot"

version := "1.0"

scalaVersion := "2.11.0"

logLevel:=Level.Info

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
                  "Sonatype snapshots"  at "http://oss.sonatype.org/content/repositories/snapshots/")


libraryDependencies ++=Seq(
  "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.4",
  "net.liftweb" %% "lift-json" % "2.6-M4",
  "org.scalatest" %% "scalatest" % "2.2.0"// % "test"
)
