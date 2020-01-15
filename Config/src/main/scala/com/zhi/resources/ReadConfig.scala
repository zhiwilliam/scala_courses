package com.zhi.resources

import scala.io.Source

object ReadConfig extends App {
  val managedIO = ResourceManage.readClosableSource(Source.fromFile("""C:\\code\test.txt"""))(StringFunctions.parseProperties)
  val config = RuntimeFunctions.unsafeBlockingRunSync(managedIO)
  println(config)
  RuntimeFunctions.shutdown()
  import MapOperations._
  println(config.getIntOrElse("test", 0))

  import RuntimeFunctions._
  import ResourceManage._
  import StringFunctions._

  val timeout = Source.fromFile("""C:\\code\test.txt""").processAs(parseProperties).unsafeRunSync().getIntOrElse("timeoutms", 0)
  println(timeout)
}
