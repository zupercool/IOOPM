import java.util.*;
/**
* @brief The Avatar is the player.
* @details The avatar needs HP, inventory. More might be added.
*/
public class Avatar {
  /**
  * Used to take user-input.
  */
  private Scanner input = new Scanner(System.in);
  /**
  * The name of the avatar, can be used in dialogs with NPCs.
  */
  private String name;
  /**
  * The health of the Avatar.
  */
  private int _HP; //Default health can be anything.
  /**
  * The room the player is currently in.
  */
  private Room currentRoom;
  /**
  * List of discovered rooms, like a map.
  */
  private List<Room> discovered = new LinkedList<Room>();
  /**
  * List of actions bound to the player
  */
  private List<Action> actionList = new LinkedList<Action>();
  /**
  * The player inventory
  */
  private Inventory playerInventory = new Inventory(10);
  /**
  * The list of unfinished courses player is enrolled to
  */
  private List<Course> unfinishedCourses = new LinkedList<Course>();
  /**
  * The list of finished courses player is enrolled to
  */
  private List<Course> finishedCourses = new LinkedList<Course>();

  /**
  * Creates the avatar.
  * @param String the name of the player.
  */
  public Avatar(String name) {
    this.name = name;
    //Initial unfinished courses
    while(this.unfinishedCourses.size() < 3) {
      this.setUnfinished(TXTReader.createRandomCourse());
    }
    this._HP = 60;
    //Initial actions
    this.addAction(5, 0); //5% chance to finding a key in Rooms
    this.addAction(5, 1); //5% chance to find a book in Rooms
    this.addAction(100, 2); //100% chance to check if professors have a question on an unfinished course
    this.addAction(100, 3); //100% chance to check if professors have a question on an finished course
  }

  /**
  * Fetches the name of the player.
  * @return String representation of the players name.
  */
  public String getName() {
    return this.name;
  }

  /**
  * Fetches the Room the player is currently in.
  * @return Room that the player is in.
  */
  public Room getCurrentRoom() {
    return this.currentRoom;
  }

  /**
  * Sets currentRoom to the one specified.
  * @param Room to the one specified.
  */
  public void setCurrentRoom(Room newRoom) {
    this.currentRoom = newRoom;
  }

  /**
  * Fetches adjacent Rooms or floors.
  * @param int (0=north, 1=east, 2=west, 3=south).
  */
  public Room getAdjacentRoom(int direction) {
    return this.currentRoom.getAdjacentRoom(direction);
  }

  /**
  * @brief Sets a course as unfinished
  * @Course the course
  * @boolean true if successfull, false if the course is already in list
  */
  public boolean setUnfinished(Course course) {
    for(int n = 0; n < this.unfinishedCourses.size(); n++) {
      if(this.unfinishedCourses.get(n).equal(course)) {
        return false;
      }
    }
    for(int n = 0; n < this.finishedCourses.size(); n++) {
      if(this.finishedCourses.get(n).equal(course)) {
        this.finishedCourses.remove(n);
      }
    }
    this.unfinishedCourses.add(course);
    this._HP -= course.getHP();
    return true;
  }

  /**
  * @brief Fetches the unfinished course at Index
    @param int index of the course
  * @return course if in range, otherwise null
  */
  public Course getUnfinished(int index) {
    return this.unfinishedCourses.get(index);
  }

  /**
  * @brief Fetches the amount of unfinished courses
  * @return int the amount of unfinished courses
  */
  public int getUnfinishedQuantity() {
    return this.unfinishedCourses.size();
  }

  /**
  * @brief Sets a course as finished
  * @Course the course
  * @boolean true if successfull, false if the course is already in list
  */
  public boolean setFinished(Course course) {
    for(int n = 0; n < this.finishedCourses.size(); n++) {
      if(this.finishedCourses.get(n).equal(course)) {
        return false;
      }
    }
    for(int n = 0; n < this.unfinishedCourses.size(); n++) {
      if(this.unfinishedCourses.get(n).equal(course)) {
        this.unfinishedCourses.remove(n);
      }
    }
    this._HP += course.getHP();
    this.finishedCourses.add(course);
    return true;
  }

  /**
  * @brief Fetches the unfinished course at Index
    @param int index of the course
  * @return course if in range, otherwise null
  */
  public Course getFinished(int index) {
    return this.finishedCourses.get(index);
  }

  /**
  * @brief Fetches the amount of unfinished courses
  * @return int the amount of unfinished courses
  */
  public int getFinishedQuantity() {
    return this.finishedCourses.size();
  }

  /**
  * @brief Moves the player to a new room if it is availible.
  * @details Different states in which the function can exit:
  *         0 = Successfull move in room
  *         1 = Successfull move in elevator
  *         2 = Unsuccessfull move, door is locked
  *         3 = Unsuccessfull move, down while in room
  *         4 = Unsuccessfull move, up while in room
  *         5 = Unsuccessfull move in elevator, as room
  *         6 = Unsuccessfull move, elevator ground floor
  *         7 = Unsuccessfull move, elevator top floor
  *         8 = Unsuccessfull move, out of bounds
  * @param int direction (0=north, 1=east, 2=west, 3=south, 4=down. 5=up).
  * @return int representing in which state the function exited.
  */
  public int move(int direction) {
    switch(direction) {
      case 4:
        if(this.currentRoom.isAnElevator() == false) return 3;
        else if(this.currentRoom.getAdjacentRoom(1) == null) return 6;
        else {
        this.currentRoom = this.getAdjacentRoom(1);
        return 1;
        }
      case 5:
        if(this.currentRoom.isAnElevator() == false) return 4;
        else if(this.currentRoom.getAdjacentRoom(2) == null) return 7;
        else {
        this.currentRoom = this.getAdjacentRoom(2);
        return 1;
        }
      default:
        if(this.getAdjacentRoom(direction) == null || (this.currentRoom.isAnElevator() && direction != 0)) return 8;
        else if(this.currentRoom.isAdjacentUnlocked(direction) == false) return 2;
        else {
        this.currentRoom = this.getAdjacentRoom(direction);
        return 0;
        }
    }
  }

  /**
  * @brief Adds room to discovered rooms
  * @param Room undiscovered room
  */
  public void discoverRoom(Room newRoom) {
    for(int n = 0; n < this.discovered.size(); n++) {
      if(discovered.get(n).equals(newRoom)) {
        return;
      }
    }
    if(newRoom.isAnElevator()) {
      return;
    }
    discovered.add(newRoom);
  }

  /**
  * @brief Fetches the list of discovered rooms
  */
  public List<Room> getDiscovered() {
    return this.discovered;
  }

  /**
  * @brief Prints discovered rooms.
  */
  public void printDiscovered() {
    System.out.println("Discovered rooms:");
    for(int n = 0; n < this.discovered.size(); n++) {
      System.out.print(this.discovered.get(n).getName() +
      " => " + this.discovered.get(n).toString() + "\n");
    }
  }

  /**
  * Fetches the player inventory
  */
  public Inventory getInventory() {
    return this.playerInventory;
  }

  /**
  * @brief This function adds actions to the player.
  * @details actionIDs: \n
              0 - Found key
              1 - Found book
  * @param int probability that the action occurs
  * @param int actionID which action it is, see details for all IDs
  */
  public void addAction(int probability, int actionID) {
    this.actionList.add(new Action(probability, actionID));
  }

  /**
  * @brief This checks and runs the players actions including them bound to the room he/she is currently in.
  */
  public void checkActions() {
    if(this.actionList.size() < 1) return;
    for(int n = 0; n < this.actionList.size(); n++) {
      runAction(this.actionList.get(n).checkAction());
    }
    this.currentRoom.checkActions();
  }

  public void runAction(int actionID) {
    int correct = -1;
    switch(actionID) {
      case 0:
        System.out.println("You found a key!");
        if(this.playerInventory.addKey()) {
          System.out.print("You put the key in you backpack.\n\n");
        }
        else {
          System.out.print("You can't carry any more, you swallow the key for later use!\n\n");
        }
      break;
      case 1:
        System.out.println("You found a book!");
        Book foundBook = TXTReader.createRandomBook();
        if(this.playerInventory.addBook(foundBook)) {
          System.out.print("You put '" + foundBook.getName() + "'\n" +
          "in you backpack." +
          "\n\n");
        }
        else {
          System.out.println("You can't carry anymore books, asa you put it back it vanishes in to thick air, spookey..");
        }
      break;
      case 2:
        for(int c = 0; c < this.getUnfinishedQuantity(); c++) {
          for(int p = 0; p < this.getCurrentRoom().getNPCQuantity(); p++) {
            if(this.getUnfinished(c).compareID(this.getCurrentRoom().getNPC(p).getCourseID())) {
              if((correct = this.getCurrentRoom().getNPC(p).checkActions(0)) != -1) {
                correct++;
                if(input.nextLine().equals(String.valueOf(correct))) {
                  System.out.println("That is correct! Added " + this.getUnfinished(c).getHP() + "HP!");
                  this.setFinished(this.getUnfinished(c));
                }
                else {
                  System.out.println("That is wrong!");
                }
              }
            }
          }
        }
      break;
      case 3:
        for(int c = 0; c < this.getFinishedQuantity(); c++) {
          for(int p = 0; p < this.getCurrentRoom().getNPCQuantity(); p++) {
            if(this.getFinished(c).compareID(this.getCurrentRoom().getNPC(p).getCourseID())) {
              if((correct = this.getCurrentRoom().getNPC(p).checkActions(1)) != -1) {
                correct++;
                if(input.nextLine().equals(String.valueOf(correct))) {
                  System.out.println("That is correct!");
                }
                else {
                  System.out.println("That's wrong! Lost " + this.getFinished(c).getHP() + "HP!");
                  this.setUnfinished(this.getFinished(c));
                }
              }
            }
          }
        }
      break;
      default: break;
    }
  }

  /**
  * @brief Fetches the HP of the player.
  * @return int HP of the player.
  */
  public int getHP() {
    return this._HP;
  }

  /**
  * @brief Fetches the HP of the player.
  * @return int HP of the player.
  */
  public void setHP(int newHP) {
    this._HP = newHP;
  }

  /**
  * @brief Gives a string representation of the player.
  * @return String representation of the player.
  */
  public String toString() {
    return ("Player name: " + this.name + " | " +
    "Player HP: " + String.valueOf(this._HP) + " |"
    );
  }

  /**
  * @brief Prints the char-summary to the terminal.
  */
  public void print() {
    System.out.print("Player Info:\n" +
    this.toString() + "\n" +
    "\n");
  }

}
//Here ends the Avatar-class
