import java.util.*;

/**
* @brief Class resposible for different inventorys such as the avatars backpack.
*/
public class Inventory {
  /**
  * @brief The inventorys max capacity, 0 represents unlimited.
  */
  private int maxCapacity = 0;
  /**
  * @brief The inventorys current capacity.
  */
  private int currentCapacity = 0;
  /**
  * @brief The number of keys in the Inventory
  */
  private int keys = 0;
  /**
  * @brief The list of books we are carrying
  */
  private List<Book> books = new LinkedList<Book>();

  /**
  * @brief Handles the creation of a new Inventory
  * @param int maxCapacity
  */
  public Inventory(int maxCapacity) {
    this.maxCapacity = maxCapacity;
    this.currentCapacity = 0;
  }

  /**
  * Adds a key to the player Inventory
  * @return true if Successfull add, false if Inventory is full.
  */
  public boolean addKey() {
    if(this.maxCapacity < this.currentCapacity + 1) {
      return false;
    }
    else {
      this.keys++;
      this.currentCapacity++;
      return true;
    }
  }

  /**
  * Checks if there is any keys in the inventory
  * @return boolean true if there are, false if not.
  */
  public boolean containKey() {
    return (this.keys > 0);
  }

  /**
  * Removes a key from the inventory
  * @return boolean true if sucessfull, false if not
  */
  public boolean removeKey() {
    if(this.containKey()) {
      this.keys--;
      this.currentCapacity--;
      return true;
    }
    return false;
  }

  /**
  * @brief Adds a book to the inventory
  * @param Book
  * @boolean true if successfull, false if not
  */
  public boolean addBook(Book newBook) {
    if(this.maxCapacity < this.currentCapacity + newBook.getSize()) {
      return false;
    }
    else {
      this.books.add(newBook);
      this.currentCapacity += newBook.getSize();
      return true;
    }
  }

  /**
  * @brief Gets the book at index
  * @param int index of the book
  * @return Book if index is in range, otherwise null
  */
  public Book getBook(int index) {
    return this.books.get(index);
  }

  /**
  * @brief Removes the book at provided index
  * @param int Index of the book to removes
  * @return boolean true if successfull, false if not
  */
  public boolean removeBook(int index) {
    if(this.books.get(index) != null) {
      this.currentCapacity -= books.get(index).getSize();
      books.remove(index);
      return true;
    }
    else {
      return false;
    }
  }

  /**
  * @brief Fetches how many books are currently in the inventory
  * @return int Amount of books in inventory
  */
  public int booksQuantity() {
    return books.size();
  }

  /**
  * Prints the Inventory to std-output
  */
  public void print() {
    System.out.print(
    "Inventory(" + (this.currentCapacity) + "/" + (this.maxCapacity) + "): \n" +
    "0. Keys: " + this.keys + "\n" +
    "Books:\n");
    for(int n = 0; n < this.books.size(); n++) {
      System.out.print((n+1) + ". ");
      this.books.get(n).print();
    }
    System.out.print("\n\n");
  }

}
