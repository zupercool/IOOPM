import java.util.*;
import java.io.*;
/**
* @brief The class responisible for handling the creation of course objects
*/
public class Course {
  /**
  * The name of the course
  */
  private int courseID;
  /**
  * The amount of books the course has
  */
  private int bookAmount;

  /**
  * @brief Handles the creation of the Course object
  */
  public Course(int courseID) {
    this.courseID = courseID;
    this.bookAmount = TXTReader.getCourseBookAmount(courseID);
  }

  /**
  * @brief Fetches the ID of the course
  * @return int ID of the course
  */
  public int getID() {
    return this.courseID;
  }

  /**
  * @brief Gets the name of the course
  * @return String the name of the course
  */
  public String getName() {
    return TXTReader.getCourseName(this.courseID);
  }

  /**
  * @brief Fetches the HP value of the course
  * @return int HP value
  */
  public int getHP() {
    return Integer.parseInt(TXTReader.getCourseHP(this.courseID));
  }

  /**
  * @brief Compares if courses are equals
  * @param Course to be compared
  */
  public boolean equal(Course compared) {
    return (this.courseID == compared.getID());
  }

  /**
  * @brief Compares the courses ID to another ID
  * @param int ID to be compared
  * @boolean true if equal, false if not
  */
  public boolean compareID(int id) {
    return (this.courseID == id);
  }

  /**
  * @brief Converts the course to a string representation
  * @return String representation of the course
  */
  public String toString() {
    return (this.getName() + " " + this.getHP() + "HP");
  }

}
