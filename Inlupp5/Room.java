import java.util.*;
/**
* @brief The Room-class managing all the functions related to the rooms.
* @details Each room will contain different objects and npcs that the Game-class
*   can fetch by using functions from this class.
*/

public class Room {

  /**
  * The name of the room.
  */
  protected String name;
  /**
  * A short description of the room.
  */
  protected String description;
  /**
  * Grammer for using it in sentances.
  */
  protected String currently;
  /**
  * Grammer for using it in sentances.
  */
  protected String heading;
  /**
  * List of NPCs in the room.
  */
  private List<NPC> npcList = new LinkedList<NPC>();
  /**
  * List of actions bound to this room.
  */
  private List<Action> actionList = new LinkedList<Action>();
  /**
  * Determines if the room is an elevator or not
  */
  protected boolean isAnElevator = false;
  /**
  * The adjacent rooms (0=north, 1=east, 2=west, 3=south).
  */
  protected List<Room> adjacentRooms = new LinkedList<Room>();
  /**
  * Info about which doors that are locked or not.
  * {north, east, west, south, up, down}
  */
  private boolean[] doorLocks = {true, true, true, true};

  /**
  * @brief Creates a room.
  * @details Should take rooms in all four cardinals (north, south, etc) as arguments.
  * @param String the name of the new room in the form of a String.
  * @param String the correct words to connect name to "you are currently <name>"
  * @param String the correct words to connect name to "you are heading <name>"
  */
  public Room(String name, String currently, String heading) {
    this.name = name;
    this.currently = currently;
    this.heading = heading;
    this.addNpc(TXTReader.createRandomProf());
    //Initial actions
  }

  /**
  * Connects the room to other rooms.
  * @param Room n (North)
  * @param Room e (East)
  * @param Room w (West)
  * @param Room s (South)
  */
  public void connect(Room n, Room e, Room w, Room s) {
    this.adjacentRooms.add(n);
    this.adjacentRooms.add(e);
    this.adjacentRooms.add(w);
    this.adjacentRooms.add(s);
  }

  /**
  * Sets the state of the doors in the room to locked or unlocked.
  * {North, East, West, North}
  * @param boolean n for north door
  * @param boolean e for east door
  * @param boolean w for west door
  * @param boolean s for south door
  */
  public void setLocks(boolean n, boolean e, boolean w, boolean s) {
    this.doorLocks[0] = n;
    this.doorLocks[1] = e;
    this.doorLocks[2] = w;
    this.doorLocks[3] = s;
  }

  /**
  * Gets the grammer for currently <room>.
  * @return String grammer.
  */
  public String grammerCurrently() {
    return this.currently;
  }

  /**
  * Gets the grammer for heading <room>.
  * @return String grammer.
  */
  public String grammerHeading() {
    return this.heading;
  }

  /**
  * Gets the name of a room.
  * @return String name of the room.
  */
  public String getName() {
    return this.name;
  }

  /**
  * Gives a description to the room.
  * @param String description of the room.
  */
  public void setDesc(String desc) {
    this.description = desc;
  }

  /**
  * Gets the description of a room.
  * @return String description of the room.
  */
  public String getDesc() {
    return this.description;
  }

  /**
  * @brief Adds an NPC to the room.
  * @param NPC new NPC
  */
  public void addNpc(NPC npc){
    this.npcList.add(npc);
  }

  /**
  * @brief Fetches a npc at index
  * @param int Index of the npc
  * @return NPC the NPC if it's in range, otherwise null
  */
  public NPC getNPC(int index) {
    return this.npcList.get(index);
  }

  /**
  * @brief Fetches the amount of NPCs in the Room
  * @return int The amount of npcs in the room
  */
  public int getNPCQuantity() {
    return this.npcList.size();
  }

  /**
  * Asks if a certain door is locked.
  * @param int (0=North, 1=East, 2=West, 3=South).
  * @return Boolean true means unlocked, false means locked.
  */
  public boolean isAdjacentUnlocked(int cardinal) {
    return this.doorLocks[cardinal];
  }

  /**
  * Unlocks the door at cardinal.
  * @param int (0=North, 1=East, 2=West, 3=South)
  */
  public void unlockDoor(int cardinal) {
      this.doorLocks[cardinal] = true;
  }

  /**
  * Fetches the connected room at cardinal.
  * @param int (0=North, 1=East, 2=West, 3=South)
  */
  public Room getAdjacentRoom(int cardinal) {
    if(0 <= cardinal && cardinal < this.adjacentRooms.size()) {
        return this.adjacentRooms.get(cardinal);
    }
    else {
      return null;
    }
  }

  /**
  * @brief This function adds actions to the room.
  * @details actionIDs: \n
              0 - Found key
  * @param int probability that the action occurs
  * @param int actionID which action it is, see details for all IDs
  */
  public void addAction(int probability, int actionID) {
    this.actionList.add(new Action(probability, actionID));
  }

  /**
  * This checks and runs the rooms actions
  */
  public void checkActions() {
    if(this.actionList.size() < 1) return;
    for(int n = 0; n < this.actionList.size(); n++) {
      runAction(this.actionList.get(n).checkAction());
    }
  }

  /**
  * @brief This function fetches the right function based on the actionID
  * @param int actionID
  */
  public void runAction(int actionID) {
    switch(actionID) {
      case 0:
        System.out.println("A professor is aproaching fast!");
      break;
      case 1:
        System.out.print("There is people in the elevator, you suddenly find the roof of the elevator\n" +
        "mesmerizing!\n");
      break;
      default: break;
    }
  }

  /**
  * Function to check if room is an elevator or not
  * @return boolean true if it is an elevator
  */
  public boolean isAnElevator() {
    return this.isAnElevator;
  }

  /**
  * Function used to compare rooms
  * @param Room to compare with
  */
  public boolean equals(Room comp) {
    if(this.getName() == comp.getName()) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
  * Makes a string of a room.
  * @return String representation of a room.
  */
  public String toString() {
    String output = "North";
    if(this.adjacentRooms.get(0) != null) {
      output += ("(" + this.adjacentRooms.get(0).getName() + ")");
      if(isAdjacentUnlocked(0) == false) output += "<Locked>";
    }
    else output += ("(X)");
    output += " |";

    output += " East";
    if(this.adjacentRooms.get(1) != null) {
      output += ("(" + this.adjacentRooms.get(1).getName()+ ")");
      if(isAdjacentUnlocked(1) == false) output += "<Locked>";
    }
    else output += ("(X)");
    output += " |";

    output += " West";
    if(this.adjacentRooms.get(2) != null) {
      output += ("(" + this.adjacentRooms.get(2).getName()+ ")");
      if(isAdjacentUnlocked(2) == false) output += "<Locked>";
    }
    else output += ("(X)");
    output += " |";

    output += " South";
    if(this.adjacentRooms.get(3) != null) {
      output += ("(" + this.adjacentRooms.get(3).getName()+ ")");
      if(isAdjacentUnlocked(3) == false) output += "<Locked>";
    }
    else output += ("(X)");
    output += " |";

    return output;
  }

  /**
  * Prints the position-summary to the terminal.
  */
  public void print() {
    //Map
    //Row 1
    System.out.print(" ");
    for(int n = 0; n < 6; n++) System.out.print("#");
    if(this.getAdjacentRoom(0) != null) System.out.print("[n]");
    else System.out.print("###");
    for(int n = 0; n < 6; n++) System.out.print("#");
    if(this.isAnElevator() && this.getAdjacentRoom(2) != null) {
        System.out.print(" \u2191");
    }
    System.out.print("\n");
    //Row 2
    System.out.print(" #");
    for(int i = 0; i < 13; i++) System.out.print(" ");
    System.out.print("#");
    if(this.isAnElevator() && this.getAdjacentRoom(2) != null) {
      System.out.print("[u]");
    }
    System.out.print("\n");
    //Row 3
    System.out.print(" #");
    for(int i = 0; i < 13; i++) System.out.print(" ");
    System.out.print("#");
    if(this.isAnElevator() && this.getAdjacentRoom(1) != null) {
      System.out.print("[d]");
    }
    System.out.print("\n");
    //Row 4
    if(this.getAdjacentRoom(2) != null && this.isAnElevator() == false) {
      System.out.print("[w]");
    }
    else System.out.print(" # ");
    for(int n = 0; n < 11; n++) System.out.print(" ");
    if(this.getAdjacentRoom(1) != null && this.isAnElevator() == false) {
      System.out.print("[e]");
    }
    else System.out.print(" #");
    if(this.isAnElevator() && this.getAdjacentRoom(1) != null) {
      System.out.print(" \u2193");
    }
    System.out.print("\n");
    //Row 5
    System.out.print(" #");
    for(int i = 0; i < 13; i++) System.out.print(" ");
    System.out.print("#   ");
    System.out.print("GPS:");
    System.out.print("\n");
    //Row 6
    System.out.print(" #");
    for(int i = 0; i < 13; i++) System.out.print(" ");
    System.out.print("#   ");
    System.out.print(this.getName() + " ");
    for(int n = 0; n < (this.toString().length() - this.getName().length() -1); n++) {
      System.out.print("#");
    }
    System.out.print("\n");
    //Row 7
    System.out.print(" ");
    for(int n = 0; n < 6; n++) System.out.print("#");
    if(this.getAdjacentRoom(3) != null) System.out.print("|s|");
    else System.out.print("###");
    for(int n = 0; n < 6; n++) System.out.print("#");
    System.out.print("   " + this.toString());
    System.out.print("\n\n");

    //GPS
    System.out.print(
    "You are currently " + this.currently + " " + this.getName() + "\n" +
    this.getDesc() + "\n\n");
  }

}
//Here ends the Room-class
