import java.util.*;
import java.io.*;
/**
* @brief The Game-class is responisible for the runtime of the game, taking player inputs and updating the world.
* @details This class will handle the inputs and the continues updates to the world during runtime.
*   When it exits the game exits.
*/
public class Game {
  /**
  * Determines if the game should continue.
  */
  private static boolean isRunning = true;
  /**
  * The world that the game takes place in.
  */
  private static World polack = new World();
  /**
  * The avatar that the player plays as.
  */
  private static Avatar player;
  /**
  * Used to take user-input.
  */
  private static Scanner input = new Scanner(System.in);
  /**
  * Used in switch statements.
  */
  private static String answer;

  /**
  * Used to print out a series of "#" to separate lines.
  * @param int amount of #s.
  */
  public static void SEPARATOR(int i) {
    for(int n = 0; n < i; n++) {
      System.out.print("#");
    }
    System.out.print("\n");
  }

  /**
  * Character creation
  */
  public static void createPlayer() {
    boolean loop = true;
    System.out.print("\033[2J\033[;H");
    System.out.println("Welcome to Polacks, the return of the lost student!");
    while(loop) {
      System.out.print("\033[2J\033[;H");
      System.out.println("What is your name?");
      SEPARATOR(18);
      player = new Avatar(input.nextLine());
      System.out.print("\033[2J\033[;H");
      System.out.println("This is you!");
      SEPARATOR(12);
      System.out.print("\n" +
      player.toString() + "\n" +
      "Are you satisfied with your character?\n" +
      "[y]es\n" +
      "[n]o\n" +
      "\n");
      switch(input.nextLine()) {
        case "y":
        loop = false;
        break;
        case "n":
        break;
        default:
        System.out.print("\n" +
        "Invalid input!\n" +
        "Allowed:\n" +
        "[y]es\n" +
        "[n]o\n" +
        "\n");
        break;
      }
      System.out.print("\033[2J\033[;H");
      player.setCurrentRoom(polack.getStart());
    }
  }

  /**
  * Prints the help-section of the game to the terminal.
  */
  public static void getHelp() {
    System.out.print("\033[2J\033[;H");
    SEPARATOR(9);
    System.out.println("Commands:");
    SEPARATOR(9);
    System.out.print("\n" +
    "q  -           Quits the game\n" +
    "\n" +
    "m<direction> - Moves you to the room in the specified direction\n" +
    "               given that it's valid and unlocked.\n" +
    "               The directions are:\n" +
    "               n=North, e=East, w=West, s=South, u=Up, d=down\n" +
    "\n" +
    "u<direction> - Unlocks the door in the specified direction provided that\n" +
    "               you have keys" +
    "\n" +
    "wm -           Prints all your discovered rooms\n" +
    "\n" +
    "di -           Asks you which item to drop, choosing something that's not\n" +
    "               there will cancel the command" +
    "\n" +
    "ppl  -         Lists all the people in the room" +
    "\n" +
    "ci  -          Lists all the course information" +
    "\n");
  }

  /**
  * Promts the player if he/she really wants to quit the game.
  */
  public static void quitAction() {
    boolean loop = true;
    while(loop) {
      System.out.print("\033[2J\033[;H");
      SEPARATOR(13);
      System.out.println("Are you sure?");
      SEPARATOR(13);
      System.out.print("\n" +
      "<[y]es>\n" +
      "<[n]o>\n" +
      "<[b]ack>\n" +
      "\n");
      answer = input.nextLine();
      switch(answer) {
        case "y":
        isRunning = false;
        loop = false;
        break;
        case "n":
        System.out.print("\033[2J\033[;H");
        gameStatus();
        loop = false;
        break;
        case "b":
        System.out.print("\033[2J\033[;H");
        gameStatus();
        loop = false;
        break;
        default:
        System.out.print("\033[2J\033[;H");
        SEPARATOR(23);
        System.out.println("Invalid input! Allowed:");
        SEPARATOR(23);
        System.out.print("\n" +
        "[y]es\n" +
        "[n]o\n" +
        "[b]ack\n" +
        "\n");
        break;
      }
    }
  }

  /**
  * Prints the status of the game to the std-output
  */
  public static void gameStatus() {
    player.print();
    player.getCurrentRoom().print();
    player.discoverRoom(player.getCurrentRoom());
  }

  /**
  * @brief Handles movement of the player
  * @details The directions are:\n
      0=North \n
      1=East \n
      2=West \n
      3=South \n
      4=Down \n
      5=Up \n
  * @param int representing a direction ()
  */
  public static void moveAction(int direction) {
    String outPrint;
    switch(player.move(direction)) {
      case 0:
      outPrint = ("You went " + player.getCurrentRoom().grammerHeading() +
      " " + player.getCurrentRoom().getName() + "\n");
      break;
      case 1:
      outPrint = ("The elevator takes you " + player.getCurrentRoom().grammerHeading() +
      " " + player.getCurrentRoom().getName() + "\n");
      break;
      case 2:
      outPrint = ("The door " + player.getAdjacentRoom(direction).grammerHeading() +
      " " + player.getAdjacentRoom(direction).getName() + " is locked!\n");
      break;
      case 3:
      outPrint = ("You put your phone alarm on 15 min and lies down to take a nap.\n" +
      "After 5 min you are abruptly awoken by a student who trips over you and starts cursing\n" +
      "over his spilled coffe. You hurry from the scene feeling very silly!\n");
      break;
      case 4:
      outPrint = ("You see a table that you, with great effort, starts to climb.\n" +
      "When you finally reach the summit you notice that alot of people are staring at you\n" +
      "with curious looks. You feel silly and immediately start your descent\n");
      break;
      case 5:
      outPrint = ("You walk over to the wall in the elevator. You move closer until your nose touches\n" +
      "the wall and then you stare the s*it out of that wall!\n");
      break;
      case 6:
      outPrint = ("You smash the down button once again, harder, because you'll be damned before you let\n" +
      "society tell you when you can't go any lower. So far no results though..\n");
      break;
      case 7:
      outPrint = ("You repeatedly presses the up button while daydreaming of rocketpowered elevators\n");
      break;
      case 8:
      outPrint = ("You bump your nose against the wall and feel silly for trying\n" +
      "to go where you're not supposed to!\n");
      break;
      default:
      outPrint = ("You raise your hand in an atempt to perform the action but accidently pokes your eye\n" +
      "I guess that's what you get for trying to do something you're not supposed to!\n");
      break;
    }
    System.out.print("\033[2J\033[;H");
    gameStatus();
    System.out.print(outPrint);
    player.checkActions();
    System.out.println("");
  }

  /**
  * @brief Handles the unlocking of doors
  * @details The directions are:
      0=North \n
      1=East \n
      2=West \n
      3=South \n
  * @param int The direction
  */
  public static void unlockAction(int direction) {
    String output;
    Room targetRoom = player.getAdjacentRoom(direction);
    if(targetRoom != null) {
      if(player.getCurrentRoom().isAdjacentUnlocked(direction) == false) {
        if(player.getInventory().containKey()) {
          player.getCurrentRoom().unlockDoor(direction);
          player.getInventory().removeKey();
          output = ("You've unlocked the door to " + player.getAdjacentRoom(direction).getName() + "!\n");
        }
        else output = ("You have no key..\n");
      }
      else {
      output = ("After you've digged around for a good 30 second\n" +
      "you look up and realize that the door is already open, how silly..\n" +
      "\n");
    }
    }
    else output = ("You try to fit the key in the wall, it did not work, shocking..\n");

    System.out.print("\033[2J\033[;H");
    gameStatus();
    System.out.print(output);
    System.out.println("");
  }

  /**
  * @brief handles the dropping of items from player inventory
  * @param int Index of the item you wish to drop.
  */
  public static void dropAction(int index) {
    String output;
    switch(index) {
      case 0:
      if(player.getInventory().containKey()) {
        player.getInventory().removeKey();
        output = ("You've dropped a key, the rules of this game says that\n" +
        "it broke and so did the fourth wall just now..\n");
      }
      else {
        output = ("You reach down in your pocket and picks up nothing, you then throw nothing on the floor.\n");
      }
      break;
      default:
      if((index -1) < player.getInventory().booksQuantity()) {
        output = ("You threw away " + player.getInventory().getBook(index -1) + "\n");
        player.getInventory().removeBook(index -1);
      }
      else {
        output = ("You can't just press a button and hope for the best, choose wiesly!\n");
      }
      break;
    }
    System.out.print("\033[2J\033[;H");
    gameStatus();
    System.out.print(output);
    System.out.println("");
  }

  /**
  * @brief Handles interactions with people in the Room
  */
  public static void peopleAction() {
    String output = ("People in the room:\n");
    for(int n = 0; n < player.getCurrentRoom().getNPCQuantity(); n++) {
      output += (player.getCurrentRoom().getNPC(n).toString() + "\n");
    }
    System.out.print("\033[2J\033[;H");
    gameStatus();
    System.out.print(output);
    System.out.println("");
  }

  /**
  * @Prints the course information
  */
  public static void courseInfoAction() {
    String output = "Finished courses(" + player.getFinishedQuantity() + "):\n";
    for(int n = 0; n < player.getFinishedQuantity(); n++) {
      output += player.getFinished(n).toString() + "\n";
    }
    output += "\nUnfinished courses(" + player.getUnfinishedQuantity() + "):\n";
    for(int n = 0; n < player.getUnfinishedQuantity(); n++) {
      output += player.getUnfinished(n).toString() + "\n";
    }
    System.out.print("\033[2J\033[;H");
    gameStatus();
    System.out.print(output);
    System.out.println("");
  }

  public static void main(String[] args) {
    createPlayer();
    player.discoverRoom(player.getCurrentRoom());
    gameStatus();
    while(isRunning) {
      System.out.println("Please enter what you would like to do:");
      switch(input.nextLine()) {
        case "mn":
        moveAction(0);
        break;
        case "me":
        moveAction(1);
        break;
        case "mw":
        moveAction(2);
        break;
        case "ms":
        moveAction(3);
        break;
        case "md":
        moveAction(4);
        break;
        case "mu":
        moveAction(5);
        break;
        case "un":
        unlockAction(0);
        break;
        case "ue":
        unlockAction(1);
        break;
        case "uw":
        unlockAction(2);
        break;
        case "us":
        unlockAction(3);
        break;
        case "pi":
        System.out.print("\033[2J\033[;H");
        gameStatus();
        player.getInventory().print();
        break;
        case "di":
        System.out.print("\033[2J\033[;H");
        gameStatus();
        player.getInventory().print();
        System.out.println("Whick item do you wish to drop?");
        //Bug: Crash if input is not an integer
        dropAction(Integer.parseInt(input.nextLine()));
        break;
        case "ppl":
        peopleAction();
        break;
        case "ci":
        courseInfoAction();
        break;
        case "wm":
        System.out.print("\033[2J\033[;H");
        gameStatus();
        player.printDiscovered();
        break;
        case "h":
        System.out.print("\033[2J\033[;H");
        gameStatus();
        getHelp();
        break;
        case "q":
        quitAction();
        break;
        default:
        System.out.print("\033[2J\033[;H");
        gameStatus();
        System.out.print("\n" +
        "Invalid input! Try 'h' for help!\n" +
        "\n");
        SEPARATOR(40);
        break;
      }
    }
    System.out.print("\033[2J\033[;H");
    System.out.print("\n" +
    "You've exited the game!\n" +
    "\n");
  }
}
