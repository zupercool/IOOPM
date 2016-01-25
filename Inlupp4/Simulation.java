import java.util.*;
/**
* Representation of a simulation that is taking place in a Store with a number of Registers and Customers.
* Variables such as customer intensity, number of registers is defined by the developer.
**/
public class Simulation {

  private static final int INTENSITY_OLDIES = 10;
  private static final int INTENSITY_FAMILIES = 10;
  private static final int NOT_SET = -1;

  Store store; //Varuhuset som simuleras
  int time = 0; //Antalet tidssteg sedan simuleringen startade
  int intensity; //Sannolikheten (i procent) att det ska komma en ny kund vid varje tidssteg.
  int maxGroceries; // Maxantalet varor som en kund kan ha när hen kommer till kassan.
  int thresholdForNewRegister; //Vid vilken snittlängd en ny kassa öppnas.

  private int customersServed = 0;
  private int totalWaitTime = 0;
  private int maxWaitTime = Simulation.NOT_SET;

  public Simulation(int intensity, int maxGroceries, int registerThreshold, int numberOfRegisters) {

    this.intensity = intensity;
    this.maxGroceries = maxGroceries;
    this.thresholdForNewRegister = registerThreshold;
    this.store = new Store(numberOfRegisters);

  }

  /**
  * Incremements time by one time unit.
  * Randomly decides based on the intensity variable if there's a new customer that needs to be served, if so, adds it to a register.
  * Checks if it's time to open a new register based on the register threshold, if the queue is longer than the threshold that would be.
  * Updates variables for statistical use (max wait time, customers served and queue length)
  **/
  void step() {

    this.store.step();
    this.time++; //ökar tidsenhet

    Customer customer = this.generateNewCustomer();

    if(customer != null) {
      this.store.newCustomer(customer);
    }

    if(timeToOpenNewRegister() == true) {
      this.store.openNewRegisters();
    }

    this.customersServed += this.store.getDoneCustomers().size();
    this.updateMaxWaitTime();
    updateTotalWaitTime();

  }

  /**
  * updates total wait time based on the customers in the store.
  **/
  private void updateTotalWaitTime() {
    for(Customer c : this.store.getDoneCustomers()) {
      this.totalWaitTime += this.waitTimeForCustomer(c);
    }
  }

  /**
  * calculates the average wait time in the store (if there's any to calculate)
  *
  * @return the average waiting time in the store across all of the customers served.
  **/
  private double getAverageWaitTime() {
    if(this.customersServed != 0) {
      return this.totalWaitTime / this.customersServed;
    } else {
      return Simulation.NOT_SET;
    }
  }

  /**
  * determines if the simulation has a new customer or not, the customer can
  * can be of several different types (OldCustomer, Family or ordinary Customer)
  *
  * @return A Customer, an OldCustomer or a Family, depending on a random number and tresholds defined for oldies and families.
  **/
  private Customer generateNewCustomer() {

    int random = ((int) (Math.random() * 100));

    if(this.intensity > random) {

      int oldieTreshold = this.intensity / Simulation.INTENSITY_OLDIES;
      int familyTreshold = oldieTreshold + (this.intensity / Simulation.INTENSITY_FAMILIES);

      Customer c;

      if(random <= oldieTreshold) { //create oldie
        c = new OldCustomer(this.time, this.generateNumberOfGroceries());
      } else if(random > oldieTreshold && random <= familyTreshold) { //create family
        c = new Family(this.time, this.generateNumberOfGroceries());
      } else { //create customer
        c = new Customer(this.time, this.generateNumberOfGroceries());
      }

      return c;

    } else {
      return null;
    }
  }

  /**
  * checks if it's time to open a new register or not based on the average queue length.
  *
  * @return true if you should open a new register.
  **/
  private boolean timeToOpenNewRegister() {
    return (this.store.getAverageQueueLength() > this.thresholdForNewRegister);
  }

  /**
  * randomly picks a number between 1 and maxGroceries set in the current simulation.
  *
  * @return a random number between 1 and maxGroceries.
  **/
  private int generateNumberOfGroceries() {
    int nGroceries = (int)(Math.random() * this.maxGroceries + 0.5);
    return (nGroceries > 0) ? nGroceries : 1; //at least 1 grocery.
  }

  /**
  * update max wait time variable.
  * Calculated and compares across all of the "done" customers in every register in the simulation.
  **/
  private void updateMaxWaitTime() {
      ArrayList<Customer> customers = this.store.getDoneCustomers();
      for(Customer c : customers) {
       int cTime = this.waitTimeForCustomer(c);
       if(cTime > this.maxWaitTime) {
         this.maxWaitTime = cTime;
       }
     }
  }

  /**
  * get the current waiting time for a certain customer.
  *
  * @param c The customer that you want to calculate the wait time for.
  * @return the current waiting time for the customer.
  **/
  private int waitTimeForCustomer(Customer c) {
    return this.time - c.getBornTime();
  }

  /**
  * string representation of the current simulation, representing the store plus statistics for the simulation.
  *
  * @return String representation of the current simulation.
  **/
  public String toString() {

    String render = "";
    render += this.store.toString();
    render += "\n🕗  " + this.time;
    render += "\nCustomers served: " + this.customersServed;

    if(this.maxWaitTime != Simulation.NOT_SET) {
      render += "\nMax Wait Time: " + this.maxWaitTime;
    }

    if(this.getAverageWaitTime() != Simulation.NOT_SET) {
      render += "\nAverage Wait Time: " + this.getAverageWaitTime();
    }

    return render;
  }

}
