import scala.io.StdIn.{readLine => readf}
import scala.util.matching.Regex

class Parser {

    // Patterns for regex expresions:
    private val h = "(h)".r
    private val q = "(q)".r
    private val newSymulation = "n([1-9][0-9]*)".r
    private val step = "".r
    private val down = "d([1-9][0-9]*)".r
    private val up0 = "u0".r
    private val up = "u([1-9][0-9]*)".r
    private val order0 = "o([1-9][0-9]*)-0".r
    private val order = "o([1-9][0-9]*)-([1-9][0-9]*)".r

    // help message:
    private val help: String = 
      "\n=== Elevator System by Wojciech Ciezobka ===\n\n" +
      "Commands:\n" +
      "     h help\n" +
      "         prints manual\n\n" +
      "     q quit\n" +
      "         exits the program\n\n" +
      "     n[1..16] new symualtion\n" +
      "         creates new elevator system symulation. Number of elevator is specified after n.\n\n" +
      "     [Enter] step\n" +
      "         next step in your elevator system\n\n" +
      "     d[1...] down pickup\n" +
      "         adds floor specified after d, desired to transfer down\n\n" +
      "     u[0...] up pickup\n" +
      "         adds floor specified after u, desired to transfer up\n\n" +
      "     o[1..n]-[0...] elevator order\n" +
      "         example: o1-10 orders 1st elevator to transfer to the 10th floor\n\n"
    
    println(help)
    
    // Mainloop
    private var myElevators: ElevatorSystem = new ElevatorSystem(newSymulation.findFirstIn(readf(
        "Create new elevator system by typing n[1..16] (for example n4)\n"
        )).getOrElse("n4").tail.toInt)
    private var run = true
    while (run) parse(readf("type some command, 'h' for help or 'q' to exit the program\n"))


    // Method to parse input commands
    private def parse(toMatch: String): Unit = {
        toMatch match {
            case h(_) => println(help)
            case q(_) => run = false
            case newSymulation(n) if (n != "0") => myElevators = new ElevatorSystem(n.toInt)
            case step() => {
                myElevators.step()
                println(myElevators)
            }
            case down(f) if (f == "1") => {     // Easter egg :)
                println("Hey, don't be lazy, next time take stairs!")
                myElevators.pickup(f.toInt, -1)
            }
            case down(f) => myElevators.pickup(f.toInt, -1)
            case up0() => myElevators.pickup(0, 1)
            case up(f) => myElevators.pickup(f.toInt, 1)
            case order0(e) => myElevators.order(e.toInt, 0)
            case order(e, f) => myElevators.order(e.toInt, f.toInt)
            case _ => println("Command not foud, type 'h' if you need help")
        }
    } 
}