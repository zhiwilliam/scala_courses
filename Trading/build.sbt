name := "Trading"

version := "1.0"

scalaVersion := "2.13.1"

libraryDependencies ++= typelevel ++ reactive ++ auxiliary

lazy val typelevel = cats ++ shapeless ++ monix ++ zio
lazy val reactive = akka ++ http ++ circe ++ graphql
lazy val auxiliary = logs ++ enums // ++ args ++ validation

lazy val cats = {
  Seq(
    "org.typelevel" %% "kittens",
    "org.typelevel" %% "cats-effect",
    "org.typelevel" %% "cats-core",
    "org.typelevel" %% "cats-free"
  ).map(_ % "2.0.0")
}

lazy val zio = {
  Seq(
    "dev.zio"           %% "zio"                   % "1.0.0-RC17",
    "dev.zio"           %% "zio-streams"           % "1.0.0-RC17",
    "dev.zio"           %% "zio-test"              % "1.0.0-RC17" % "test",
    "dev.zio"           %% "zio-test-sbt"          % "1.0.0-RC17" % "test",
  )
}


lazy val shapeless = {
  Seq(
    "com.chuusai" %% "shapeless" % "2.3.3",
    "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.3"
  )
}

lazy val monix = {
  Seq("io.monix" %% "monix" % "3.0.0")
}

lazy val validation = {
  Seq(
    "org.scalacheck" %% "scalacheck" % "1.14.1",
    "org.scalatest" %% "scalatest" % "3.0.8",
    "org.scalamock" %% "scalamock" % "4.4.0"
  ).map(_ % Test)
}


lazy val akka = {
  val akkaVersion = "2.6.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-persistence-typed" % akkaVersion excludeAll (ExclusionRule(
      "io.netty"
    )),
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test
  )
}


lazy val http = {
  val akkaHttpVersion = "10.1.11"
  Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
    "de.heikoseeberger" %% "akka-http-circe" % "1.28.0",
    "com.emarsys" %% "jwt-akka-http" % "1.1.4"
  )
}

lazy val logs = {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  )
}
//
//lazy val args = {
//  val version = "3.5.1"
//  Seq(
//    "org.backuity.clist" %% "clist-core" % version,
//    "org.backuity.clist" %% "clist-macros" % version % "provided"
//  )
//}

lazy val enums = {
  Seq(
    "com.beachape" %% "enumeratum" % "1.5.13",
    "com.beachape" %% "enumeratum-circe" % "1.5.22",
    "com.beachape" %% "enumeratum-cats" % "1.5.16"
  )
}


lazy val circe = {
  val circeVersion = "0.12.3"
  Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % circeVersion)
}

lazy val graphql = {
  Seq(
  "org.sangria-graphql" %% "sangria" % "2.0.0-M1",
  "org.sangria-graphql" %% "sangria-circe" % "1.3.0",
  )
}