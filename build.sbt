name := "macro-tests"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % scalaVersion.value,
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.specs2" %% "specs2-core" % "4.3.4" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos", "-language:experimental.macros")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
