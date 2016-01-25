import java.util.*;
/**
* @brief Sub class of NPC.
* @details A Professor is a special type of NPC, which is linked to a course.
* 	He/she can ask questions about the course and give the Avatar HP.
*/
public class Professor extends NPC {
  /**
  * The courseID of the course the professor is bound to
  */
  private int courseID;

  /**
  * @brief Handles the cration of the professor Class
  * @param String name of the Professor
  * @param int ID of the course he/she is bound to
  */

	public Professor(String name, int courseID) {
	super(name);
	this.courseID = courseID;
  this.addAction(75, 0);
  this.addAction(50, 1);
	}

  /**
  * @brief This function adds actions to the player.
  * @details actionIDs: \n
              0 - Ask unfinished course
              1 - Ask finished course
  * @param int probability that the action occurs
  * @param int actionID which action it is, see details for all IDs
  */
  public void addAction(int probability, int actionID) {
    this.actionList.add(new Action(probability, actionID));
  }

  /**
  * @brief This checks and runs the players actions including them bound to the room he/she is currently in.
  * @param int actionID
  */
  public int checkActions(int actionID) {
    if(this.actionList.size() < 1) return -1;
    System.out.println("Checking action: " + actionID);
    return runAction(this.actionList.get(actionID).checkAction());
  }

  public int runAction(int actionID) {
    switch(actionID) {
      case 0:
        System.out.println(this.getName() + " wants to ask you a question in " + this.getCourse() + "!(Unfinished)");
        return this.askQuestion();
      case 1:
        System.out.println(this.getName() + " wants to ask you a question in " + this.getCourse() + "!(Finished)");
        return this.askQuestion();
      default: return -1;
    }
  }

  /**
  * @brief Fetches the course the professor is bound to
  */
  public String getCourse() {
    return TXTReader.getCourseName(this.courseID);
  }

  /**
  * @brief Fetches the ID of the course to which the professor is bound
  * @return int ID of the course
  */
  public int getCourseID() {
    return this.courseID;
  }

  /**
  * @brief Asks a question bound to the professors course
  * @return int representing the right answer
  */
  public int askQuestion() {
    return TXTReader.askCourseQuestion(this.courseID);
  }

  /**
  * @brief Makes String representation of a professor
  */
  public String toString() {
    return (this.name + " professor in " + this.getCourse());
  }

  /**
  * @brief Compares the courses ID to another ID
  * @param int ID to be compared
  * @boolean true if equal, false if not
  */
  public boolean compareID(int id) {
    return (this.courseID == id);
  }

  /**
  * @brief Prints the professor
  */
  public void print() {
    System.out.print(this.toString() +
    "\n");
  }

}
