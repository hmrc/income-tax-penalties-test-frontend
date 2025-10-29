import uk.gov.hmrc.DefaultBuildSettings

ThisBuild / majorVersion := 0
ThisBuild / scalaVersion := "3.3.6"

lazy val microservice = Project("income-tax-penalties-test-frontend", file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .settings(
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    // https://www.scala-lang.org/2021/01/12/configuring-and-suppressing-warnings.html
    // suppress warnings in generated routes files
      scalacOptions ++= Seq(
          "-Werror",
          "-Wconf:msg=unused import&src=html/.*:s",
          "-Wconf:msg=unused import&src=xml/.*:s",
          "-Wconf:msg=unused&src=.*RoutesPrefix\\.scala:s",
          "-Wconf:msg=unused&src=.*Routes\\.scala:s",
          "-Wconf:msg=unused&src=.*ReverseRoutes\\.scala:s",
          "-Wconf:msg=Flag.*repeatedly:s",
          "-Wconf:msg=Setting -Wunused set to all redundantly:s"
      ),
    pipelineStages := Seq(gzip),
    PlayKeys.playDefaultPort := 9189,
  )
  .settings(CodeCoverageSettings.settings *)

lazy val it = project
  .enablePlugins(PlayScala)
  .dependsOn(microservice % "test->test")
  .settings(DefaultBuildSettings.itSettings())
  .settings(libraryDependencies ++= AppDependencies.it)
