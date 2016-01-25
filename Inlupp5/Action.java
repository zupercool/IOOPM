import java.util.Random;
/**
* @brief Class to handle the potential actions in each room
* @details This class is responisible for handling the potential actions that might occur in each room.
*          Each room will have a list of actions. Every time the player enters a room there will be a
*          specified chance of them happening.
*/
public class Action {
  /**
  * Random generator, is used to determine if an action should occur.
  */
  private Random rand = new Random();
  /**
  * The id of the action.
  */
  protected int actionID;
  /**
  * The probability that an action occur.
  */
  protected int probability = 0;

  /**
  * Used to create a new action.
  * @param int between 0-100, the chance of the action occuring in percent.
  */
  public Action(int percent, int id) {
    this.probability = percent;
    this.actionID = id;
  }

  /**
  * Random generator for action-probability
  */
  public int randomNum() {
    int randomNum = rand.nextInt((100 - 0) + 0);
    return randomNum;
  }

  /**
  * Sets a new probability
  * @param int the new probability in percent (0-100)
  */
  public void setProbability(int percent) {
    this.probability = percent;
  }

  /**
  * @brief Checks if the action should be performed or not
  * @return int -1 if action should not run, otherwise actionID
  */
  public int checkAction() {
    if(this.randomNum() < this.probability) {
      return this.actionID;
    }
    return -1;
  }

}
