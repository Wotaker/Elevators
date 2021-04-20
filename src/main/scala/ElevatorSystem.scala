import scala.collection.mutable

class ElevatorSystem(val fleetSize: Int) {

    // Initialize:
    private val fleet: List[Elevator] = List.tabulate(fleetSize)(n =>
        new Elevator(n + 1, 0, Nil))
    private var pendingUp: List[Int] = Nil
    private var pendingDown: List[Int] = Nil


    // This method adds pickup request (floor, direction) to the appropriate pending list
    def pickup(floor: Int, direction: Int) =
        if (direction == 1) pendingUp = insertFloor(pendingUp, floor, true)
        else pendingDown = insertFloor(pendingDown, floor, false)
    
    // This method orders the elevator with id = elvID to transfer to the floor in second argument
    def order(elvId: Int, floor: Int) =
        fleet.take(elvId).lastOption.getOrElse(fleet.head).addOrder(floor)

    // Symulation step
    def step() = {
        // I manage stops
        fleet.foreach(elv => elv.considerStopping())

        // II update elevators' priorities 
            //handling elevators with priorities:
        fleet.filter(elv => !elv.priorities.isEmpty).foreach(elv => elv.updatePriorities(pickups(elv)))
            // Handling elevators without priorities:
        if (!pendingUp.isEmpty || !pendingDown.isEmpty)
            fleet.filter(elv => elv.priorities.isEmpty).map(elv =>
                newDestination(elv)).filter(tuple => tuple._2 < Int.MaxValue).foreach(tuple =>
                tuple._1.updatePriorities(tuple._2))
        
        // III move elevators
        fleet.foreach(elv => elv.moove())
    }


    // Finds a new destination for an elevator with no priorities.
    // Returns (elv, Int.MaxValue) if no destination found
    private def newDestination(elv: Elevator): (Elevator, Int) = {
        val result: (Elevator, Int) = (
            elv,
            pendingDown.++(pendingUp).toSet.filter(floor => 
            floor != elv.currentFloor).minByOption(floor =>
            math.abs(floor - elv.currentFloor)).getOrElse(Int.MaxValue)
        )

        if (result._2 == Int.MaxValue) result
        else {
            this.removeFromPendings(result._2, 1)
            this.removeFromPendings(result._2, -1)
            result
        }
    }

    // Returns a list of elevatotrs possible pickups from pending list,
    // which was heading to its priority
    private def pickups(elv: Elevator): List[Int] = {
        // Elevator is moving
        val result =
            if (elv.currentFloor < elv.priorities.head)
                pendingUp.filter(floor => floor > elv.currentFloor && floor < elv.priorities.head)
            else
                pendingDown.filter(floor => floor < elv.currentFloor && floor > elv.priorities.head)
        this.removeFromPendings(result, math.signum(elv.priorities.head - elv.currentFloor))
        result
    }

    // Returns a list, wich is an appropriate pending list updated with floor
    private def insertFloor(pendingList: List[Int], floor: Int, asc: Boolean): List[Int] =
        pendingList match {
            case Nil => floor :: Nil
            case head :: tail if head == floor => pendingList
            case head :: tail if asc && head < floor => head :: insertFloor(tail, floor, true)
            case head :: tail if !asc && head > floor => head :: insertFloor(tail, floor, false)
            case _ => floor :: pendingList
        }
    
    // Removes floors from appropriate Pending list
    private def removeFromPendings(floors: List[Int], direction: Int) {
        if (direction == 1) pendingUp = pendingUp.filter(pendingFloor => !floors.contains(pendingFloor))
        else pendingDown = pendingDown.filter(pendingFloor => !floors.contains(pendingFloor))
    }

    // Removes floor from appropriate Pending list
    private def removeFromPendings(floor: Int, direction: Int) {
        if (direction == 1) pendingUp = pendingUp.filter(floorUp => floorUp != floor)
        else pendingDown = pendingDown.filter(floorDown => floorDown != floor)
    }

    override def toString(): String = "Elevators fleet:\n[ " + fleet.mkString(",\n  ") + " ]"
}