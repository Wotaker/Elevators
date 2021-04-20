import math.{abs => abs}

object Main extends App {
    val program = new Parser

    // var list = List(2, 6, 8, 12)
    // println(list.mkString(", "))
    // list = insertOrder(4, list, 3)
    // println(list.mkString(", "))

    // def insertOrder(tempFloor: Int, priorities: List[Int], floor: Int): List[Int] =
    //     priorities match {
    //         case head :: Nil if (head == tempFloor) => 
    //             head :: floor :: Nil
    //         case head :: tail if ((floor - tempFloor) * (head - floor) > 0 && abs(floor - tempFloor) < abs(head - floor)) =>
    //             floor :: priorities
    //         case head :: Nil =>
    //             head :: floor :: Nil
    //         case head1 :: head2 :: tail =>
    //             head1 :: insertOrder(head1, head2 :: tail, floor)
    //     }
}
