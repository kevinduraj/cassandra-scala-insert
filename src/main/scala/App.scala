/**
  * Querying Cassandra from Scala
  */

object App {

  def main(args: Array[String]) {

    var IP = "127.0.0.1"
    var filename = ""

    if (args.size > 1) {
      println("args: " + args(0))

      IP = args(0).toString
      filename = args(1).toString

    }

    try {
      val cassandra = new ClassCassandra(IP)
      cassandra.flatFileInserts(filename)
      cassandra.disconnect("Method: getQueryResult")
    } catch {
      case e: Exception => println("exception caught: " + e);
    }

    //    try {
    //      ObjCassandra.putQueryInsert(loop).getQueryResult().disconnect("Method: getQueryResult")
    //    } catch {
    //      case e: Exception => println("exception caught: " + e);
    //    }
    //  }


  }

}
