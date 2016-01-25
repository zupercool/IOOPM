import java.util.*;
/**
* Representation of a store with a number of registers and customers.
**/
class Store {

  List<Register> registers;

  /**
  * Representation of a store with a number of registers and customers.
  * The store will always start with one of the registers open.
  *
  * @param numberOfRegisters  the number of registers the store should have
  **/
  public Store(int numberOfRegisters) {
    this.registers = new ArrayList<Register>();

    if(numberOfRegisters > 0) {
      for(int i = 0; i < numberOfRegisters; i++) {
        Register r = new Register();

        if(i == 0) {
          r.open();
        }

        this.registers.add(r);
      } //EOF loop
    }
  }
  public Store(){
    this(10);
  }

  /**
  * the average queue length in the store, this is calculated across all of the OPEN registers.
  *
  * @return the average queue length for the store as a decimal
  **/
  public double getAverageQueueLength() {
    double c = 0;
    int r = 0;

    for(Register reg : this.registers) {

      if(reg.isOpen()){
        c = c + reg.getQueueLength();
        r++;
      }
    }
    return c/r;
  }

  /**
  * Add a customer to the stores registers, the customer will be placed in the queue that is shortest based on queue length.
  *
  * @param c the customer that needs its groceries to be registered.
  **/
  public void newCustomer(Customer c) {

    int shortest_register_index = this.getFastestRegisterIndex();
    Register r = this.registers.get(shortest_register_index);
    r.addToQueue(c);

  }

  /**
  * Finds the register that has the shortest queue length.
  * If two has the same length, the first one is chosen.
  *
  * @return the index for the register list that holds the register with the shortest queue.
  **/
  private int getFastestRegisterIndex() {

    int index = 0;
    int shortest_index = 0;
    int shortest_queue = -1;

    for(Register r : this.registers) {

      if(r.isOpen() == true) {
        if(shortest_queue == -1) {
          shortest_index = index;
          shortest_queue = r.getQueueLength();
        } else if(r.getQueueLength() < shortest_queue) {
          shortest_index = index;
          shortest_queue = r.getQueueLength();
        }
      }

      index++;

    }

    return shortest_index;

  }

  /**
  * Sends a message to all the registers that a time unit has passed.
  **/
  public void step() {

    for(Register r : this.registers) {

      r.step();

    }

  }

  /**
  * Opens a new register (if possible)
  **/
  public void openNewRegisters() {

    for(Register r : this.registers) {
      if(!r.isOpen()){
        r.open();
        break;
      }
    }

  }

  /**
  * Get all of the customers that are done (aka got served) in all of the registers in the current step.
  *
  * @return a list of customers that is done.
  **/
  public ArrayList<Customer> getDoneCustomers() {
    ArrayList<Customer> customers = new ArrayList<Customer>();

    for(Register r : this.registers) {
      if(r.currentCustomerIsDone() == true) {
        customers.add(r.getFirstCustomer());
      }
    }
    return customers;
  }

  /**
  * A string representation of the store, the registers and its customers.
  *
  * @return a string representation of the store.
  **/
  public String toString() {

    String render = "";

    for(Register r : this.registers) {
      render += r.toString();
    }

    return render;

  }

}
