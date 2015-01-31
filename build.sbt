name := "GlassTeaPot"

version := "1.0"

scalaVersion := "2.11.0"

logLevel:=Level.Info

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
                  "Sonatype snapshots"  at "http://oss.sonatype.org/content/repositories/snapshots/")

javacOptions += "-g:none"
javacOptions ++= Seq("-source", "1.7")
compileOrder := CompileOrder.JavaThenScala

libraryDependencies ++=Seq(
  "org.scalatest" %% "scalatest" % "2.2.1"// % "test"
)

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.5.0" withSources()
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.5.0" withSources()
libraryDependencies += "com.sun.mail" % "javax.mail" % "1.5.2" withSources()
libraryDependencies += "javax.mail" % "javax.mail-api" % "1.5.2" withSources()
libraryDependencies += 	"org.elasticsearch" % "elasticsearch" % "1.4.2" withSources()
libraryDependencies += "org.apache.derby" % "derby" % "10.4.1.3" % "test"


libraryDependencies <++= (scalaVersion)(sv =>
Seq(
    "org.apache.commons" % "commons-io" % "1.3.2" withSources(),
    "commons-lang" % "commons-lang" % "2.6" withSources(),
    "junit" % "junit" % "4.12"
))