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
        var M = (tensor*(100,100)[ ((i,j),i*100+j) | i <- 0..99, j <- 0..99]);
        var N = (tensor*(100,100)[ ((i,j),i*100+j) | i <- 0..99, j <- 0..99]);
        
        var res = tensor*(100,100)[((i,j),a+b) | ((i,j),a) <- M, ((ii,jj),b) <- N, ii == i, jj == j];
            
      

        res
        
                       
    """)

    println("sum:" + x)


     


    
    
    val t = System.currentTimeMillis()

    println("time: "+(System.currentTimeMillis()-t)/1000.0+" secs")
  }
}
