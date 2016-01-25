import org.junit.*;

public class AvatarTest {
    @BeforeClass
    public static void setUpClass() throws Exception {
        // Kod som körs innan den första testmetoden
    }

    @Before
    public void setUp() throws Exception {
        // Kod som körs innan varje test
    }
 
    @Test
    public void testOneThing() {
        // Kod som testar en del
    }

    @Test
    public void testAnotherThing() {
        // Kod som testar en annan del
    }

    @Test
    public void testSomethingElse() {
        // Kod som testar någonting annat
    }

    @After
    public void tearDown() throws Exception {
        // Kod som körs efter varje test  
    }
 
    @AfterClass
    public static void tearDownClass() throws Exception {
        // Kod som körs efter den sista testmetoden
    }
}