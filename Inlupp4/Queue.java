import java.util.*;

/**
* A generic abstract data type Queue based on the principles of first in first out (FIFO)
**/
public class Queue<T>{

  /**
  * Represents a node inside of a queue.
  **/
  private class Node {

    //instansvariabler
  	private T element;
  	private Node next;

    /**
    * the default constructor for Node with a datatype free of choice.
    **/
  	public Node(T element, Node next){
  	    this.element = element;
  	    this.next = next;
  	}

  }

  //instansvariabler
  public Node first;
  public Node last;
  public int length;

  /**
  * Create a new empty queue.
  **/
  public Queue(){
    this.first = null;
    this.last = null;
    this.length = 0;
  }

  /**
  * Get the length of the queue, aka the number of elements in the queue.
  *
  * @return length of the queue.
  **/
  public int length(){
    return this.length;
  }

  /**
  * Adds an element to the queue, it will be added last in the queue.
  *
  * @param element      the element you want to add to the queue.
  **/
  public void enqueue(T element){

		if(this.first == null) {
	    	this.first = new Node(element, this.last);
	    	this.length = 1;
    } else if(this.first != null && this.last == null) {
      this.last = new Node(element, null);
      this.first.next = this.last;
      this.length = 2;
    } else {
      this.last.next = new Node(element, null);
      this.last = this.last.next;
	    this.length++;
		}

  }

  /**
  * Removes the first element in the queue and returns it
  *
  * @return the element that was first in the queue.
  **/
  public T dequeue(){

    if(this.first != null) {
      T element = this.first.element;
      if(this.length() == 1) {
        this.first = null;
        this.last = this.first;
        this.length = 0;
      } else if(this.length > 1) {
        this.first = this.first.next;
        this.length--;
      }
      return element;
    } else {
      return null;
    }

  }

  /**
  * Get the first element in the queue.
  *
  * @return the first element in the queue, if none return null.
  **/
  public T first(){
    if(this.first != null) {
      return this.first.element;
    } else {
      return null;
    }
  }

  /**
  * Get the last element in the queue.
  *
  * @return the last element in the queue, if none return null.
  **/
  public T last() {
    if(this.last != null) {
      return this.last.element;
    } else {
      return null;
    }
  }

  /**
  * Get the second element in the queue
  *
  * @return the second element in the queue, if none return null.
  **/
  public T next() {
    if(this.first != null && this.first.next != null) {
      return this.first.next.element;
    } else {
      return null;
    }
  }


  public ArrayList<T> getElements() {

    ArrayList<T> elements = new ArrayList<T>();
    Node backup = this.first;

    while(backup != null) {
      elements.add(backup.element);
      backup = backup.next;
    }

    return elements;
  }

  /**
  * String representation of the queue
  *
  * @return String representation of the queue with its elements toString() printed as well.
  **/
  public String toString() {

    Node backup = this.first;
    String render = "[";

    while(backup != null) {
      if(backup.next != null) {
        render += backup.element.toString() + " | ";
      } else {
        render += backup.element.toString();
      }
      backup = backup.next;
    }
    render += "] n(" + this.length() + ")";
    return render;
  }

}
