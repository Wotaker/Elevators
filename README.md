# Elevator System
This project is a symulation of the elevator system.

## Running the project:
1. In sbt terminal type 'run'
2. At the beggining, the manual will prompt. Below you will be asked to initiate your elevator system
3. The program will guide you from now on

## Functionality
- Initiation of multiple elevators (no upper boundary)
- every floor can call an elevator, and specify desired direction (pickups)
- you can order each of the elevator separately to transfer  to the desired floor (orders)
- symulation step is the move of the elevators from one floor to another (executed by hiting [ENTER])

## Algorithm

#### The elevator system
It is the brain of the operation, it controlls the fleet of the elevators. It has a pending list which accepts evry pickup and from which elevators 
can than choose their destinations. The systems knows the order of actions in the symulation step. Firstly each of the elevators check whether to pause on the current floor.
Than Elevators updates their prioritis by choosing requests from the pending list. Lastly the elevators moves one floor (if needed) according to their priorities

#### Elevators have priority lists
Each elevator has its priority at the given time. Elevator moves through floors according to them. Although those can be modified at any time
#### Elevators don't like to change directions
If an elevator has a choosen direction, it wants to preserve it. In that case, the elevator only accepts to its priority list the floors that 
are not conflicted with that direction. (Apart from the orders, becouse who can stop the passengers?)
#### Elevators are lazy but efficient
If an elevator is heading to a floor, lets say the elevator Ela is at 3rd floor, and she is heading to the 10th. Ela dont want to gather requests that are above 10th, 
but she will happily accept floors between 3 and 10 (4, 5, 6, 7, 8 or 9)
#### Elevators are ordered :(
Elevators are choosing their destination in order they were creadted. So in symulation step, the elevator 1 decides firstly where to move, than the 2nd can decides, and 
the last elevators can only pick what is left in the pending list.

## Further improvements
- The order in which elevators updates their priorities is a flaw. It may happen that the best destination for the 1st elevator is not at all efficient globaly. 
The system may be improved by counting distances of each floor in the pending list to every elevator and choosing the best for the floor. But this distance shuld also take
into account elevator's direction and  relative position to the floor processed.
- The floor orders are strongly influencing the elevators movement. That is unavoidable with users interfeering the system. In my implementation orders are updating 
elevators priorities immediately. It can be as well implemented with some buffor, similar to the pending list buffor.
