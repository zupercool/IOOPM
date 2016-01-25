import java.util.*;

/**
* @brief Handles the functions associated with books
*/
public class Book {
  /**
  * The ID of the course to which the book is bound
  */
  private int courseID;
  /**
  * The ID of the book
  */
  private int bookID;
  /**
  * An int used for the equals function
  */
  private int bookCode;
  /**
  * @brief Handles the creation of the book object
  * @param int The ID of the course to which the book is bound
  * @param int The ID of the book
  */

  public Book(int courseID, int bookID) {
    this.courseID = courseID;
    this.bookID = bookID;
    this.bookCode = (10*courseID) + bookID;
  }

  /**
  * @brief Gets the name of the book
  * @return String The name of the book
  */
  public String getName() {
    return TXTReader.getBookName(this.courseID, this.bookID);
  }

  /**
  * @brief Gets the author of the book
  * @return String The author of the book
  */
  public String getAuthor() {
    return TXTReader.getBookAuthor(this.courseID, this.bookID);
  }

  /**
  * @brief Gets the year the book was published
  * @return int Year the book was published
  */
  public int getYear() {
    return TXTReader.getBookYear(this.courseID, this.bookID);
  }

  /**
  * @brief Gets the size of the book
  * @return int The size of the book in litres
  */
  public int getSize() {
    return TXTReader.getBookSize(this.courseID, this.bookID);
  }

  /**
  * @brief Converts the info about the book to a string representation
  * @return String representation of the book
  */
  public String toString() {
    return (this.getName() + " by " +
    this.getAuthor() + ", published in " +
    this.getYear() + " | Size: " +
    this.getSize() + " litres");
  }

  /**
  * @brief Prints the info about the book
  */
  public void print() {
    System.out.println(this.toString());
    System.out.println("   Useful in the course " + TXTReader.getCourseName(this.courseID));
  }

}
