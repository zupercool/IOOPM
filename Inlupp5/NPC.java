import java.util.*;
/**
* @brief The NPC-class handles the different kinds of NPCs in the game.
* @details The NPC can be either a student or a professor (More might come).
*   Things that they all have includes name, type(Student, Prof), action(dialog, question, etc),
*   inventory(Keys, test, etc). The best way to use this might be to let two new classes,
*   student and professor, inherit the NPC class.
*/

public class NPC {
  /**
  * The name of the NPC.
  */
  protected String name;
  /**
  * The list of actions bound to a professor
  */
  protected List<Action> actionList = new LinkedList<Action>();

  /**
  * Creates a new NPC with specified name.
  * @param String the name of the NPC.
  */
  public NPC(String name) {
    this.name = name;
  }

  /**
  * @Fetches the name of the NPC
  */
  public String getName() {
    return this.name;
  }

  public int getCourseID() {
    System.out.println("Error, in NPC!");
    return 0;
  }

  public int checkActions(int actionID) {
    System.out.println("Error, in NPC!");
    return -1;
  }

}
