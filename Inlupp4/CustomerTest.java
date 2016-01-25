public class CustomerTest {
    public static void main(String args[]) {

    //a customer with n groceries.
    int n = 3;
    Customer c = new Customer(0, n);

    System.out.println("[CUSTOMER_SERVE_TEST] serve a customer " + n + " times and check isDone().");
    for(int i = 0; i < n; i++) {
      c.serve();
    }
    assert c.isDone() == true;

    //a queue with m numbers in sequence being en- and de-queued.
    int m = 10;
    Queue q = new Queue<Integer>();

    /*TEST*/
    System.out.println("[ENQUEUE_DEQUEUE_TEST] enqueue and dequeue " + m + " elements directly after each other.");
    for(int i = 0; i < m; i++) {
      q.enqueue(i);
      assert q.length() == 1;
      q.dequeue();
      assert q.length() == 0;
    }
    assert q.length() == 0;

    /*TEST*/
    System.out.println("[ENQUEUE_TEST] trying to enqueue " + m + " elements in the empty queue.");
    for(int i = 0; i < m; i++) {
      q.enqueue(i);
      assert q.length() == (i + 1);
    }
    assert q.length() == m;

    /*TEST*/
    System.out.println("[OUT_OF_BOUNDS_TEST] trying to dequeue " + (m + 1) + " elements an empty queue.");
    for(int i = 0; i < (m + 1); i++) {
      q.dequeue();
    }
    assert q.length() == 0;

    /*TEST*/
    System.out.println("[OLDCUSTOMER_SERVE] Testing OldCustomer.");
    Customer oc = new OldCustomer(0, 2);
    oc.serve();
    oc.serve();
    assert oc.isDone() == false;
    oc.serve();
    oc.serve();
    assert oc.isDone() == true;

    /*TEST*/
    System.out.println("[FAMILY_SERVE] Testing Family");
    Customer fam = new Family(0, 100);
    fam.serve();
    assert fam.isDone() == true;

    }

}
