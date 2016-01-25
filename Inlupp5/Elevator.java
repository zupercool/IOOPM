import java.util.*;
/**
* @brief Handles the Elevator.
* @details Manages the connections between floors.
*/
public class Elevator extends Room {

  /**
  * Creates a new elevator.
  * @param String the name of the elevator.
  */
  public Elevator(String name, String currently, String heading) {
    super(name, currently, heading);
    this.isAnElevator = true;
    //Initial actions
    this.addAction(40, 1);
  }

  /**
  * Connects the rooms together
  * @param Room new floor
  */
  public void connect(Elevator down, Room connectingRoom, Elevator up) {
      this.adjacentRooms.add(connectingRoom);
      this.adjacentRooms.add(down);
      this.adjacentRooms.add(up);
  }

  /**
  * Converts the Elevator to String representation
  * @return String representation of Elevator class
  */
  public String toString() {
    String output = this.getName() + " -> | ";
    output += "Down(";
    if(this.getAdjacentRoom(1) != null) output += (this.getAdjacentRoom(1).getName() + ") | ");
    else output += "X) | ";
    output += "North(" + (this.getAdjacentRoom(0).getName() + ") | ");
    output += "Up(";
    if(this.getAdjacentRoom(2) != null) output += (this.getAdjacentRoom(2).getName() + ") | ");
    else output += "X) | ";

    return output;
  }

}
