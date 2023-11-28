import edu.uta.diablo._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import Math._

object Test {
  def main ( args: Array[String] ) {
    val conf = new SparkConf().setAppName("Test")
    spark_context = new SparkContext(conf)
    val spark = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._
    param(data_frames,true)
    parami(block_dim_size,10)

    val N = 2

    def f ( i: Int, j: Int = 1 ): Double = (i*11)%3+j*1.1

    var t = System.currentTimeMillis()

    val C = q("""
        var V1 = tensor*(N)[ (i,f(i)) | i <- 0..(N-1) ]              // dense block vector
        var V2 = tensor*()(N)[ (i,f(i)) | i <- 0..(N-1) ]            // sparse block vector
        var M1 = tensor*(N,N)[ ((i,j),f(i,j)) | i <- 0..(N-1), j <- 0..(N-1) ]     // dense block matrix
        var M2 = tensor*(N)(N)[ ((i,j),f(i,j)) | i <- 0..(N-1), j <- 0..(N-1) ]    // dense rows, sparse columns
        var M3 = tensor*()(N,N)[ ((i,j),f(i,j)) | i <- 0..(N-1), j <- 0..(N-1) ]   // sparse rows & columns


        tensor*(N)[ ((i+1)%N,v+1) | (i,v) <- V2 ];

/*
        var M = tensor*(100,100)[ ((i,j),i*100.0+j) | i <- 0..99, j <- 0..99 ];
        var V = tensor*(100)[ (i,i*100.0) | i <- 0..99 ];
        var N = M;
        var R = M;

        for i = 0, 99 do
            for j = 0, 99 do
               V[i] += M[i,j];

        for i = 0, 99 do
            for j = 0, 99 do
               M[i,j] += 1.0;

        for i = 0, 99 do
            for j = 0, 99 do
               R[i,j] = M[i,j]+N[i,j];

        for i = 0, 99 do
            for j = 0, 99 do {
               R[i,j] = 0.0;
               for k = 0, 99 do
                  R[i,j] += M[i,k]*N[k,j];
            };
*/
        M1;
/*

      var M = tensor(N,N)[ ((i,j),if (random()>0.5) 0.0 else random()*100) | i <- 0..N-1, j <- 0..N-1 ];

      var E = tensor*(N,N)[ ((i,j),M[i,j]) | i <- 0..N-1, j <- 0..N-1 ];
      var EE = E;

      //tensor*(N,N)[ ((i,j),(+/c)/c.length) | ((i,k),a) <- E, ((kk,j),b) <- EE, k == kk, let c = a*b, group by (i,j) ];
      //tensor*(N,N)[ ((i,j),a+b) | ((i,j),a) <- E, ((ii,jj),b) <- EE, ii == i, jj == j ];
      tensor*(N,N)[ (((i+1)%N,j),a+b) | ((i,j),a) <- E, ((ii,jj),b) <- EE, ii == i, jj == j ];
*/
    """)

    println(C._3.queryExecution)
    C._3.show
    C._3.count()

    println("time: "+(System.currentTimeMillis()-t)/1000.0+" secs")

  }
}
