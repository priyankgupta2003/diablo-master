import edu.uta.diablo._
import org.apache.spark._
import org.apache.spark.rdd._
import Math._

object Slicer_Test {
  def main ( args: Array[String] ) {
    val conf = new SparkConf().setAppName("Test")
    spark_context = new SparkContext(conf)

    parami(number_of_partitions,10)
    parami(block_dim_size,10)

    val x = q("""
        var M = tensor*(100,100)[ ((i,j),i*100+j) | i <- 0..99, j <- 0..99];
       
        var V = M[10:20:3,10:20:3];
        V;
    """)

        val y = q("""
        var M = tensor*(100,100)[ ((i,j),i*100+j) | i <- 0..99, j <- 0..99];
       
        var V = M[10:20:3,10:20:3];
        V;
    """)

        val z = q("""
        var M = tensor*(100,100)[ ((i,j),i*100+j) | i <- 0..99, j <- 0..99];
       
        var V = M[10:20:3,10:20:3];
        V;
    """)

    println("_----Res_1:" + x)
    println("_----Res_2:" + y)
    println("_----Res_3:" + z)


     


    
    
    val t = System.currentTimeMillis()

    println("time: "+(System.currentTimeMillis()-t)/1000.0+" secs")
  }
}
