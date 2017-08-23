import com.datastax.driver.core.Cluster
import scala.io.Source

class ClassCassandra(IP: String) {

  val clusterBuilder = Cluster.builder()
  clusterBuilder.addContactPoint(IP)
  clusterBuilder.withPort(9042)
  //optional clusterBuilder.withCredentials("admin", "password")

  val cluster = clusterBuilder.build()
  val session = cluster.connect("test")
  val page = 2

  println(session.getCluster().getClusterName + " connection successful\n")

  /**
    * Insert data from a flat file
    *
    * @param filename
    */
  def flatFileInserts(filename: String): Unit = {

    println("def flatFileInserts(filename: String): Unit ")
    println(s"filename = $filename")

    var cnt = 0
    //val buffer = io.Source.fromFile("data/links.dat")

    for (line <- Source.fromFile(filename).getLines) {

      val col = line.split("\\|").map(_.trim)
      //println(s"${col(0)}|${col(1)}")

      if (col.length == 2) {

        if ((col(0).length() > 2) && (col(1).length() > 2)) {

          val domain = col(0).replaceAll("\\p{C}", " ")
          val url = col(1).replaceAll("\\p{C}", " ")

          val SQL = String.format("INSERT INTO test.links (domain, url) VALUES('%s','%s');",
                        domain.replaceAll("'", "''"), url.replaceAll("'", "''"))

          if ((cnt % page) == 0) println(cnt + " " + SQL)
          session.execute(SQL)
          cnt += 1

        } else {
          println("Column is too short")
        }
      }
    }
  }

  /**
    * Disconnect from Cassandra
    * @param str
    */
  def disconnect(str: String) {
    cluster.close()
    session.close()
    println("\n" + str + " successfully closed")
  }


}
