import java.util.*;

/**
* @brief The World-class is responisible for building the world and managing all rooms and
*   connections between them.
* @details This class will build the world at startup of the game. At runtime it will keep track
*   of connections between rooms.
*/

public class World {

  //Rooms:
  private Room _0th_Floor = new Room("Basement", "in the", "to the");
    private Elevator _0f = new Elevator("Elevator 0th floor", "in the", "to the");
    private Room _DVcave = new Room("DV Cave", "in the", "to the");
    private Room _FooBar = new Room("FooBar", "in", "to");
  //
  private Room _1st_Floor = new Room("Hallway 1st Floor", "on the", "to the");
    private Elevator _1f = new Elevator("Elevator 1st floor", "in the", "to the");
    private Room _Outside = new Room("Outside", "", "");
  //
  private Room _2nd_Floor = new Room("Hallway 2nd Floor", "on the", "to the");
    private Elevator _2f = new Elevator("Elevator 2nd floor", "in the", "to the");
    private Room _1211 = new Room("1211", "in", "to");
    private Room _Skrubben = new Room("Skrubben", "in", "to");
  //
  private Room _3rd_Floor = new Room("Hallway 3rd Floor", "on the", "to the");
    private Elevator _3f = new Elevator("Elevator 3rd floor", "in the", "to the");
  //
  private Room _4th_Floor = new Room("Hallway 4th Floor", "on the", "to the");
    private Elevator _4f = new Elevator("Elevator 4th floor", "in the", "to the");
  //
  private Room _5th_Floor = new Room("Hallway 5th Floor", "on the", "to the");
    private Elevator _5f = new Elevator("Elevator 5th floor", "in the", "to the");
    private Room _1549 = new Room("1549", "in", "to");
    private Room _1515 = new Room("1515", "in", "to");
    private Room _1511 = new Room("1511", "in", "to");
  //
  private Room _Attic = new Room("The Secret Attic", "in the", "to the");

  private Room start = _Outside;
  //

  /**
  * @brief Creates the world.
  * @details This functions specifies everything needed to build the world including new rooms
  *   and connections between them. The connections are written in the form of (North, East, West, South)
  *   where each cardinal is a room or null.
  */
  public World() {
    //Elevator
    _0f.connect(null, _0th_Floor, _1f);
    _1f.connect(_0f, _1st_Floor, _2f);
    _2f.connect(_1f, _2nd_Floor, _3f);
    _3f.connect(_2f, _3rd_Floor, _4f);
    _4f.connect(_3f, _4th_Floor, _5f);
    _5f.connect(_4f, _5th_Floor, null);

    //0th floor
    _0th_Floor.connect(_FooBar, null, _DVcave, _0f);
    _0th_Floor.setLocks(true, true, false, true);
      _FooBar.connect(null, null, null, _0th_Floor);
      _DVcave.connect(null, null, null, _0th_Floor);
    //1th floor
    _1st_Floor.connect(_Outside, null, null, _1f);
    _1st_Floor.unlockDoor(0);
    _1st_Floor.unlockDoor(3);
      _Outside.connect(null, null, null, _1st_Floor);
      _Outside.unlockDoor(3);
    //2nd floor
    _2nd_Floor.connect(null, _1211, _Skrubben, _2f);
    _2nd_Floor.unlockDoor(3);
      _1211.connect(null, null, null, _2nd_Floor);
      _Skrubben.connect(null, null, null, _2nd_Floor);
    //3rd floor
    _3rd_Floor.connect(null, null, null, _3f);
    _3rd_Floor.unlockDoor(3);
    //4th floor
    _4th_Floor.connect(null, null, null, _4f);
    _4th_Floor.unlockDoor(3);
    //5th floor
    _5th_Floor.connect(_1515, _1549, _1511, _5f);
    _5th_Floor.unlockDoor(3);
      _1549.connect(null, null, null, _5th_Floor);
      _1515.connect(null, null, null, _5th_Floor);
      _1511.connect(null, null, null, _5th_Floor);

    //Descriptions:
    //Elevator
    _0f.setDesc(
    "A slow but reliable elevator that always goes in the oposite direction of \n" +
    "where you're standing");
    _1f.setDesc(
    "A slow but reliable elevator that always goes in the oposite direction of \n" +
    "where you're standing");
    _2f.setDesc(
    "A slow but reliable elevator that always goes in the oposite direction of \n" +
    "where you're standing");
    _3f.setDesc(
    "A slow but reliable elevator that always goes in the oposite direction of \n" +
    "where you're standing");
    _4f.setDesc(
    "A slow but reliable elevator that always goes in the oposite direction of \n" +
    "where you're standing");
    _5f.setDesc(
    "A slow but reliable elevator that always goes in the oposite direction of \n" +
    "where you're standing");
    //0th Floor
    _0th_Floor.setDesc(
    "The foundation of the building, all kinds of creatures dwell here."
    );
      _FooBar.setDesc(
      "The meetingplace for rival gangs such as IT:are and DV:are."
      );
      _DVcave.setDesc(
      "BEAWARE! No IT:are has ever sat foot here and returned."
      );
    _1st_Floor.setDesc(
    "The connection to the real world."
    );
      _Outside.setDesc(
      "It has a really good resolution, sharp colors too!"
      );
    _2nd_Floor.setDesc(
    "Inofficially owned by IT"
    );
      _1211.setDesc(
      "Too small!"
      );
      _Skrubben.setDesc(
      "Land of the free, home of the brave (And snickers)"
      );
    _3rd_Floor.setDesc(
    "No one really goes here, it just kind of exists."
    );
    _4th_Floor.setDesc(
    "The floor where the school hordes it's old computers.\n" +
    "Unlucky student are sometimes forced to use them.."
    );
    _5th_Floor.setDesc(
    "The entire floor is kept warm using the excess heat generated by students brains \n" +
    "as a result of overthinking simple problems in haskell."
    );
      _1511.setDesc(
      "I think there is a room called 1511"
      );
      _1515.setDesc(
      "Where magic happends"
      );
      _1549.setDesc(
      "The cosy counterpart to 1515"
      );

    // Actions:
  }

  /**
  * Fetches start-room
  */
  public Room getStart() {
    return this.start;
  }

}
