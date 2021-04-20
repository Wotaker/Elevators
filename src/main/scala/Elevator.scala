import math.{abs => abs}

class Elevator(val id: Int, var currentFloor: Int, var priorities: List[Int]) {

    private var stopped = false;

    // Before action, elevator checks whether or not it should pickup anyone at current floor
    // if so, stopped is marked as 'true'
    def considerStopping() {
        stopped = false
        if (!priorities.isEmpty) {
            if (priorities.head == currentFloor) {
                stopped = true
                priorities = priorities.tail
            }
        }
    }

    // Updates self's priorities
    def updatePriorities(pickups: List[Int]) {
        priorities = pickups.++(priorities)
    }
    def updatePriorities(floor: Int) {
        priorities = floor :: Nil
    }

    // Orders elevator to transfer to the floor specified by the method's argument
    def addOrder(floor: Int) {
        // if (floor != currentFloor && !priorities.contains(floor)) priorities = priorities.:+(floor)
        if (floor != currentFloor && !priorities.contains(floor) && !priorities.isEmpty) {
            priorities = insertOrder(currentFloor, priorities, floor)
        }
        else priorities = floor :: Nil
    }
    
    // Moves elevator by one floor if possible
    def moove() {
        if (!stopped && !priorities.isEmpty) {
            currentFloor += math.signum(priorities.head - currentFloor)
        }
    }

    // Returns list with insertet floor properly
    private def insertOrder(tempFloor: Int, priorities: List[Int], floor: Int): List[Int] =
        priorities match {
            case head :: Nil if (head == tempFloor) => 
                head :: floor :: Nil
            case head :: tail if ((floor - tempFloor) * (head - floor) > 0 && abs(floor - tempFloor) < abs(head - floor)) =>
                floor :: priorities
            case head :: Nil =>
                head :: floor :: Nil
            case head1 :: head2 :: tail =>
                head1 :: insertOrder(head1, head2 :: tail, floor)
        }

    override def toString(): String = s"Elevator nr $id, at floor $currentFloor, " +
      s"heading ${if (priorities.isEmpty) "NaN" else priorities.head}, stopped=$stopped"
}