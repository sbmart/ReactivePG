name := "play-scala"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.postgresql"       %  "postgresql"                    % "9.4-1202-jdbc42",
  "com.github.mauricio" %% "postgresql-async" % "0.2.18",
  "org.flywaydb" %% "flyway-play" % "2.2.1",
  "org.scalikejdbc"     %% "scalikejdbc-async"             % "0.5.+",
  "org.scalikejdbc"     %% "scalikejdbc-async-play-plugin" % "0.5.+",
  jdbc,
  cache,
  ws,
  specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
