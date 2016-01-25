/**
* Representation of a Customer standing in line waiting to get his/her groceries
* served by a cashier.
**/
public class Customer {

  //instansvariabler
  private int bornTime = 0;
  protected int groceries = 0;
  protected int gender = 0;

  /**
  * the default constructor for Customer
  *
  * @param       bornTime   an abstract time unit of when the customer was born
  * @param       groceries  the number of groceries the customer holds
  **/
  public Customer(int bornTime, int groceries){
          this.bornTime = bornTime;
          this.groceries = groceries;
  };

  /**
  * decrementing the customers groceries by one.
  **/
  public void serve() {
    this.groceries--;
  }

  /**
  * decrementing the customers groceries-count by one.
  *
  * @return      borntime of the customer defined in abstract time units.
  **/
  public int getBornTime() {
    return this.bornTime;
  }

  /**
  * check if a customer is done.
  *
  * @return      a boolean that represents if the customer is done.
  **/
  public boolean isDone() {
    return this.groceries == 0;
  }

  /**
  * return a specific symbol that defines the current customer standing in line.
  *
  * @return      a symbol specific for the customer standing in line.
  **/
  public String getFace() {
      return "🚶";
  }

  /**
  * used to print a customer that is being served.
  *
  * @return      a string representation of a customer.
  **/
  public String toString() {
    return this.getFace() + " (" + groceries + ")";
  }

}
