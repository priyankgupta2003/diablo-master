import edu.uta.diablo._
import org.apache.spark._
import org.apache.spark.rdd._
import Math._

object mult_add {
  def main ( args: Array[String] ) {
    val conf = new SparkConf().setAppName("Test")
    spark_context = new SparkContext(conf)

    parami(number_of_partitions,10)
    parami(block_dim_size,10)

    val x = q("""
        var M = (tensor*(10,10)[ ((i,j),i*100+j) | i <- 0..10, j <- 0..10]);
        var N = (tensor*(10,10)[ ((i,j),i*100) | i <- 0..10, j <- 0..10]);
        
        var res = (tensor*(10,10)[((i,j),+/c) | ((i,k),a) <- M, ((kk,j),b) <- N, k == kk, let c = a*b, group by (i,j)]);
        
    """)

    println("sum:" + x)

    

     


    
    
    val t = System.currentTimeMillis()

    println("time: "+(System.currentTimeMillis()-t)/1000.0+" secs")
  }
}
