/**
* Kickstart the simulation yo.
**/
public class Simulator {

    private static Simulation simulation;

    private static int customerIntensity = 50;
    private static int customerMaxGroceries = 10;
    private static int registerTreshold = 5;
    private static int registerCount = 8;
    private static int simulationSteps = 100;
    private static int simulationSpeed = 500;
    private static boolean advancedSimulation = true;

    public static void main(String[] args) throws InterruptedException {

      Simulator.configure(args);

      Simulator.printHeader();
      Thread.sleep(1000);

      Simulator.simulation = new Simulation(
        Simulator.customerIntensity,
        Simulator.customerMaxGroceries,
        Simulator.registerTreshold,
        Simulator.registerCount
      );

      for(int i = 0; i < Simulator.simulationSteps; i++){
          Simulator.clearScreen();
          Simulator.simulation.step();
          System.out.println(Simulator.simulation);
          Thread.sleep(Simulator.simulationSpeed);
      }

      System.out.println("");
      System.out.println("Simulation finished!");

    }

    private static void printHelp() {
      System.out.println("");
      System.out.println("--s || --s(teps) : number simulation steps (Default: " + Simulator.simulationSteps + ")");
      System.out.println("--sp || --(sp)eed : the speed defined in ms for each step in the simulation loop (Default: " + Simulator.simulationSpeed + ")");
      System.out.println("--rt || --r(egister)t(reshold) : queue length before opening a new register (Default: " + Simulator.registerTreshold + ")");
      System.out.println("--r || --r(egisters) : the number of registers that are available (Default: " + Simulator.registerCount + ")");
      System.out.println("--cg || --c(ustomer)g(roceries) : the maximum amount of groceries one customer can have (Default: " + Simulator.customerMaxGroceries + ")");
      System.out.println("--ci || --c(ustomer)i(ntensity) : the intensity of customers, ex: 50 means 50% chance (Default: " + Simulator.customerIntensity + ")");
      System.out.println("");
      System.exit(1);
    }

    private static void configure(String[] args) {
      //check if there are more than 0 arguments and if it's an even number of arguments.
      if(args.length > 0 && (args.length % 2 == 0)) {

        for(int i = 0; i < args.length; i++) { //loops through every argument supplied

          String arg = new String(args[i]); //to access .substring method

          if(arg.length() > 2 && ((i % 2) == 0)) { //if the argument is longer than 2 chars and index is even.

            if(arg.substring(0, 2).equals("--")) { //if it's a complete flag

              int value = Integer.parseInt(args[i+1]);
              String flag = arg.substring(2);

              switch(flag) {

                case "customerintensity":
                case "ci":
                  Simulator.customerIntensity = value;
                break;

                case "customergroceries":
                case "cg":
                  Simulator.customerMaxGroceries = value;
                break;

                case "registers":
                case "r":
                  Simulator.registerCount = value;
                break;

                case "registertreshold":
                case "rt":
                  Simulator.registerTreshold = value;
                break;

                case "steps":
                case "s":
                  Simulator.simulationSteps = value;
                break;

                case "sp":
                case "speed":
                  Simulator.simulationSpeed = value;
                break;

                default:
                  System.out.println("[WARNING] Unrecognised flag \"" + flag + "\", skipping...");
                break;

              }

              i++;

            } else {
              Simulator.wrongFormat();
            }

          } else {
            Simulator.wrongFormat();
          }

        } //EOF loop

      } else {

        if(args.length == 1 && args[0].equals("--help")) {
          Simulator.printHelp();
        } else if(args.length > 0) {
          Simulator.wrongFormat();
        }

      }
    }

    private static void wrongFormat() {
      System.out.println("Wrong format, ex: java Simulator --ci 50 --rt 5 --r 5 --s 100 -cg 10");
      System.exit(1);
    }

    private static void printHeader() {
      Simulator.clearScreen();
      System.out.println("Welcome to Kassa🐮.");
    }

    private static void clearScreen() {
      System.out.print("\033[2J\033[;H");
    }

}
