import java.util.*;

/**
* Representation of a register in a store serving customers standing in line.
**/
public class Register {

    //instansvariabler
    private boolean open;
    private Queue queue;

    /**
    * the default constructor for Register
    * creates a register with an empty queue and defaults to being closed.
    **/
    public Register() {
      this.open = false;
      this.queue = new Queue<Customer>();
    }

    /**
    * open the register
    **/
    public void open(){
      this.open = true;
    }

    /**
    * close the register
    **/
    public void close(){
      this.open = false;
    }

    /**
    * find out if the register is open
    *
    * @return a boolean indicating if the register is open and ready to serve customers.
    **/
    public boolean isOpen(){
      return this.open;
    }

    /**
    * as the simulation increments by one time unit, the following might happen
    * if the register is open: the customer standing first in line gets one grocery registrered
    * if the register is closed: nothing happens.
    **/
    public void step() {
      if(this.isOpen()) {
        Customer c = this.getFirstCustomer();
        if(c != null) {
          if(c.isDone()){
            this.removeCurrentCustomer();
          } else {
            c.serve();
          }
        }
      }
    }

    /**
    * checks if the register has any customers waiting in line.
    *
    * @return a boolean that represents if there are any customers at the register
    **/
    public boolean hasCustomers() {
      return (this.queue.length > 0);
    }

    /**
    * checks if the first customer in line is done (which means the customer has no more groceries to register)
    *
    * @return a boolean that represents if the first customer has no more groceries.
    **/
    public boolean currentCustomerIsDone(){
      Customer c = this.getFirstCustomer();
      if(c != null) {
        return c.isDone();
      } else {
        return false;
      }
    }

    /**
    * add a customer to the queue
    *
    * @param c the customer that will be added to the register queue.
    **/
    public void addToQueue(Customer c){
      this.queue.enqueue(c);
    }

    /**
    * remove the first customer standing in line
    *
    * @return the customer that was removed from line.
    **/
    public Customer removeCurrentCustomer(){
      return (Customer)this.queue.dequeue();
    }

    /**
    * get the customer that is standing first in line
    *
    * @return the customer standing first in line.
    **/
    public Customer getFirstCustomer() {
      return (Customer)this.queue.first();
    }

    /**
    * length of the current queue
    *
    * @return the number of Customers standing in line.
    **/
    public int getQueueLength(){
      return this.queue.length;
    }

    /**
    * @TODO
    **/
    private ArrayList<Customer> getCustomers() {
      return (ArrayList<Customer>) this.queue.getElements();
    }

    /**
    * A specific symbol for the current register, depends if open or closed.
    *
    * @return a specific symbol for the register depending on if it's open or closed.
    **/
    private String getFace() {
      return (this.isOpen()) ? "🙋" : "⛔️";
    }

    /**
    * a representation of the register graphically.
    * WARNING: emojis.
    *
    * @return a string representation of the Register with its customers.
    **/
    public String toString() {

      String face = this.getFace();
      String render = face + "  [] ";
      boolean first = true;

      if(this.hasCustomers()) {

        for(Customer c : this.getCustomers()) {
          if(first == false) {
            render += c.getFace() + " ";
          } else {
            render += c.toString() + " ";
            first = false;
          }
        }

      } else {
        if(this.isOpen()) {
          render += "EMPTY";
        }
      }

      render += "\n"; //radbrytning
      return render;

    }

}
