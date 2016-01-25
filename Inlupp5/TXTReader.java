import java.util.*;
import java.io.*;
/**
* @brief The class responisible for handling course info found in txt files
* @details The courses all have their own id as well as books with their id.
* The IDs are:
* 1 = Kaffetermos Dynamic
* 2 = Zombieanatomi
*/
public class TXTReader {
  /**
  * Random generator, is used to create random books.
  */
  private static Random rand = new Random();
  /**
  * The name of our .txt containing out course information
  */
  private static String course_txt = "courselist.txt";

  /**
  * @brief Function that reads one line at a time
  * @param int Nr on the line to fetch
  * @param int index on the line
  */
  private static String getInfo(String txtFile, int lineNr, int lineIndex) {
    BufferedReader in = null;
    try {
      in = new BufferedReader(new FileReader(txtFile));
      String inLine = null;
      while ((inLine = in.readLine()) != null) {
        String[] parts = inLine.split(";");
        if(Integer.parseInt(parts[0]) == lineNr) {
          return parts[lineIndex];
        }
      }
    } catch (IOException e) {
      System.out.println("Caught a IOEception!");
      return null;
    } finally {
      try {
        in.close();
      } catch (Exception e) {
      }
    }
    return null;
  }

  /**
  * @brief Gets how many books the course has
  * @param txtFile is the file to check
  * @return int Number of lines in the file
  */
  public static int getLinesFile(String txtFile) {
    BufferedReader in = null;
    try {
      in = new BufferedReader(new FileReader(txtFile));
      String inLine = null;
      int counter = 0;
      while ((inLine = in.readLine()) != null) {
        counter++;
      }
      return counter;
    } catch (IOException e) {
      System.out.println("Caught a IOEception!");
      return 0;
    } finally {
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }

  /**
  * @brief Random generator for action-probability
  * @param int min is the minimum possible outcome
  * @param int max is the maximum possible outcome
  * @return int Random int between min and max
  */
  public static int randomNum(int min, int max) {
    System.out.println("Min: " + min + " | Max: " + max);
    int randomNum = rand.nextInt(((max - min) +1) + min);
    System.out.println("The random number is: " + randomNum);
    return randomNum;
  }

  /**
  * @brief Gets the amount of courses
  * @return int Number of courses in course_txt
  */
  public static int getCoursesAmount() {
    return getLinesFile(course_txt);
  }

  /**
  * @brief Creates a random course
  * @Course a new random course
  */
  public static Course createRandomCourse() {
    int courseID = randomNum(0, (getCoursesAmount()-1));
    return new Course(courseID);
  }

  /**
  * @brief Gets how many books the course has
  * @param int Id of the course
  * @return Number of books bound the course
  */
  public static int getCourseBookAmount(int courseID) {
    return getLinesFile(getCourseBookFile(courseID));
  }

  /**
  * @brief Fetches the name of the course bound to the provided id
  * @param int Id of the course
  * @return String the name of the course
  */
  public static String getCourseName(int courseID) {
    return getInfo(course_txt, courseID, 1);
  }

  /**
  * @brief Gets the courses HP value
  * @param int Id of the course
  * @return String representation of the HP the course is worth
  */
  public static String getCourseHP(int courseID) {
    return getInfo(course_txt, courseID, 2);
  }

  /**
  * @brief Fetches the name of the Book txt-file for the course
  * @param int Id of the course
  * @return String name of the file containing the books bound to the course
  */
  public static String getCourseBookFile(int courseID) {
    return getInfo(course_txt, courseID, 3) + "_b.txt";
  }

  /**
  * @brief Fetches the name of the Professor txt-File boubd to the course
  * @param int ID of the course
  * @return String name of the file containing the professors bound to the course
  */
  public static String getProfNameFile(int courseID) {
    //return getInfo(course_txt, courseID, 3) + "_p.txt";
    return "names_p.txt";
  }

  /**
  * @brief Fetches the name of the Questions txt-File bound to the course
  * @param int ID of the course
  * @return String name of the file containing the questions bound the course
  */
  public static String getQuestionFile(int courseID) {
    //return getInfo(course_txt, courseID, 3) + "_q.txt";
    return "questions_q.txt";
  }

  /**
  * @brief Prints the course
  */
  public static void printCourse(int courseID) {
    System.out.print("\n" +
    getInfo(course_txt, courseID, 1) +
    "\n");
  }

  /**
  * @brief Created a professor with a random name
  */
  public static Professor createRandomProf() {
    int courseID = randomNum(0, (getCoursesAmount()-1));
    int nameID = randomNum(0, (getCourseProfAmount(courseID)-1));
    return new Professor(getProfName(courseID, nameID), courseID);
  }

  /**
  * @brief Gets the amount of lines in the Professor txt-file
  * @param int ID of the course
  * @return int The amount of names in the Professor txt-file
  */
  public static int getCourseProfAmount(int courseID) {
    return getLinesFile(getProfNameFile(courseID));
  }

  /**
  * @brief Fetches a name from the Professor txt-file
  * @param int index of the names
  * @return String the name
  */
  public static String getProfName(int courseID, int nameID) {
    return getInfo(getProfNameFile(courseID), nameID, 1);
  }

  /**
  * @brief Fetches the amount of lines in the Question txt-file
  * @param int ID of the Course
  * @return int The amount of questions in the txt-file
  */
  public static int getCourseQuestionAmount(int courseID) {
    return getLinesFile(getQuestionFile(courseID));
  }

  /**
  * @brief Asks a random question bound to the course with the provided ID
  * @param int courseID
  * @return int representing the index of the correct answer
  */
  public static int askCourseQuestion(int courseID) {
    int questionID = randomNum(0, (getCourseQuestionAmount(courseID)-1));
    String question = getInfo(getQuestionFile(courseID), questionID, 1);
    String answer1 = getInfo(getQuestionFile(courseID), questionID, 2);
    String answer2 = getInfo(getQuestionFile(courseID), questionID, 3);
    String answer3 = getInfo(getQuestionFile(courseID), questionID, 4);
    int correct = Integer.parseInt(getInfo(getQuestionFile(courseID), questionID, 5));

    System.out.print(question + "\n" +
    "1." + answer1 + "\n" +
    "2." + answer2 + "\n" +
    "3." + answer3 + "\n" +
    "\n");

    return correct;
  }

  /**
  * @brief Creates a random book from a random course
  */
  public static Book createRandomBook() {
    int courseID = randomNum(0, (getCoursesAmount()-1));
    int bookID = randomNum(0, (getCourseBookAmount(courseID)-1));
    return new Book(courseID, bookID);
  }

  /**
  * @brief Gets the name of the book specified by the provided index
  * @param int Id of the course the book is bound to
  * @param int Id of the book
  */
  public static String getBookName(int courseID, int bookID) {
    return getInfo(getCourseBookFile(courseID), bookID, 1);
  }

  /**
  * @brief Gets the name of the author to the book specified by the provided index
  * @param int Id of the course the book is bound to
  * @param int Id of the book
  */
  public static String getBookAuthor(int courseID, int bookID) {
    return getInfo(getCourseBookFile(courseID), bookID, 2);
  }

  /**
  * @brief Gets the year when the book was published
  * @param int Id of the course the book is bound to
  * @param int Id of the book
  */
  public static int getBookYear(int courseID, int bookID) {
    return Integer.parseInt(getInfo(getCourseBookFile(courseID), bookID, 3));
  }

  /**
  * @brief Gets the size of the book in litres
  * @param int Id of the course the book is bound to
  * @param int Id of the book
  */
  public static int getBookSize(int courseID, int bookID) {
    return Integer.parseInt(getInfo(getCourseBookFile(courseID), bookID, 4));
  }

}
