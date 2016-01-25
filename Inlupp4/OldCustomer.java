/**
* This class defines an older customer which is slower than an ordinary customer.
**/
public class OldCustomer extends Customer {

  /**
  * A factor that defines how much slower an old customer should be handling its groceries.
  * ex: 2 would make it twice as slow as an ordinary customer.
  **/
  private static final int SLOWDOWN_RATE = 2;

  /**
  * used to initialise an old customer, the number of groceries will be doubled.
  * which means that this customer is half as fast an ordinary customer.
  *
  * @param       bornTime   an abstract time unit of when the customer was born
  * @param       groceries  the number of groceries the customer holds
  **/
  public OldCustomer(int bornTime, int groceries) {

  		super(bornTime, (OldCustomer.SLOWDOWN_RATE * groceries));

  }

  public String getFace() {
    return "👵";
  }

  /**
  * used to print an old customer with the correct number of groceries corresponding the SLOWDOWN_RATE.
  *
  * @return      a string representation of an old customer.
  **/
  public String toString() {
    int groceries = this.groceries / OldCustomer.SLOWDOWN_RATE;
    return this.getFace() + " (" + groceries + ")";
  }
}
