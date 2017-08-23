import com.datastax.driver.core.Cluster
import scala.io.Source
import java.nio.charset.CodingErrorAction
import scala.io.Codec

class ClassCassandra(IP: String) {

    val page = 2 
    val clusterBuilder = Cluster.builder()
    clusterBuilder.addContactPoint(IP)
    clusterBuilder.withPort(9042)
    // clusterBuilder.withCredentials("admin", "password") // optional
    val cluster = clusterBuilder.build()
    val session = cluster.connect("test")
    println(session.getCluster().getClusterName + " connection successful\n")

/*----------------------------------------------------------------------------------------------------------
    Avoiding Nulls with Scala’s Option Class    
  ----------------------------------------------------------------------------------------------------------*/
    def flatFileInserts(filename: String): Unit = {

        println("def flatFileInserts(filename: String): Unit ")
        println(s"filename = $filename")

        var cnt = 0
        //val buffer = io.Source.fromFile("/home/spider/raw/links.csv")

        for (line <- Source.fromFile(filename).getLines) { 

            println(line)

            val col = line.split("\\|").map(_.trim)
            println(s"${col(0)}|${col(1)}")
          
            if(col.length == 2) {

                if ((col(0).length() > 2) && (col(1).length() > 2)) {

                    val domain = col(0).replaceAll("\\p{C}", " ")
                    val url = col(1).replaceAll("\\p{C}", " ")

                    val SQL = String.format("INSERT INTO test.links (domain, url) VALUES('%s','%s');",
                              domain.replaceAll("'", "''"), url.replaceAll("'", "''"))
                    //if ((cnt % page) == 0) println(cnt + " " + SQL)
                    println(cnt + " " + SQL)
                    session.execute(SQL)
                    cnt += 1
                } else {
                  println("Column is too short")
                }
            }                
        }
    }


/*----------------------------------------------------------------------------------------------------------
    Avoiding Nulls with Scala’s Option Class    
  ----------------------------------------------------------------------------------------------------------*/
    def csvFileInserts(filename: String): Unit = {

        println("def csvFileInserts(filename: String): Unit ")

        implicit val codec = Codec("UTF-8")
        codec.onMalformedInput(CodingErrorAction.REPLACE)
        codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

        var cnt = 0

        for (line <- Source.fromFile(filename).getLines) { 

            try {

                val col = line.split("\",\"").map(_.trim)
                //println(s"${col(0)}|${col(1)}")
          
                if(col.length == 2) {

                    if ((col(0).length() > 8) && (col(1).length() > 8)) {

                        var domain  = col(0).replaceAll("\"", "").replaceAll("\\p{C}", " ").replaceAll("'", "''")
                        var url     = col(1).replaceAll("\"", "").replaceAll("\\p{C}", " ").replaceAll("'", "''")

                        val SQL = String.format("INSERT INTO test.links (domain, url) VALUES('%s','%s');",  domain, url) 
                        //if ((cnt % page) == 0) println(cnt + " " + SQL)
                        println(cnt + " " + SQL)
                        session.execute(SQL)
                        cnt += 1
                    }
                }

            } catch {
                 case e: Exception => println(e) 
            }
        }
    }

    def disconnect(str: String) {
        cluster.close()
            session.close()
            println("\n" + str + " successfully closed")
    }

    private def randomAlphaNumericString(length: Int): String = {
        val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')
            randomStringFromCharList(length, chars)
    }

    private def randomStringFromCharList(length: Int, chars: Seq[Char]): String = {
        val sb = new StringBuilder
            for (i <- 1 to length) {
                val randomNum = util.Random.nextInt(chars.length)
                    sb.append(chars(randomNum))
            }
        sb.toString
    }
}
