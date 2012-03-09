import org.sandwich.network.Server
object Sandwich { 
  def main(args: Array[String]) = println("Humm...a great sandwich!") 
  
  val server = new Server
  
  server.start();
}
