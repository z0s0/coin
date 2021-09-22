import pl.project13.scala.sbt.JmhPlugin

name := "coin"
version := "0.1"

scalaVersion := "2.13.6"
enablePlugins(JmhPlugin)

libraryDependencies ++= List(
  "org.typelevel" %% "cats-effect" % "3.2.0",
  "dev.zio"       %% "zio" % "2.0.0-M1",
  "io.scalaland" %% "chimney" % "0.6.1"
)