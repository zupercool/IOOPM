public class Family extends Customer {

	public Family(int bornTime, int groceries) {
		super(bornTime, groceries);
	}

	/**
	* decrementing the customers groceries, all at once.
	**/
	public void serve() {
  	this.groceries = 0;
	}

  public String getFace() {
    return " 👪 ";
  }

}
