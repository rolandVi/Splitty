package commons;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankaccountTest {


    private static Bankaccount bankaccount;
    private static Bankaccount bankaccount1;

    @BeforeAll
    static void init() {
        /*
         random IBAN generated via http://www.randomiban.com/?country=Netherlands
         */
        bankaccount = new Bankaccount("NL29INGB3506581023", "testUser1", "INGBNL2A");
        bankaccount1 = new Bankaccount("NL29INGB3506581023", "testUser1", "INGBNL2A");
    }
    @Test
    void getIban() {
        assertEquals("NL29INGB3506581023", bankaccount.getIban());
    }

    @Test
    void getHolder() {
        assertEquals("testUser1", bankaccount.getHolder());
    }

    @Test
    void getBic() {
        assertEquals("INGBNL2A", bankaccount.getBic());
    }

    @Test
    void setIban() {
        bankaccount.setIban("NL91ABNA0417164300");
        assertEquals("NL91ABNA0417164300", bankaccount.getIban());
    }

    @Test
    void setBic() {
        bankaccount.setBic("ABNANL2A");
        assertEquals("ABNANL2A", bankaccount.getBic());
    }

    @Test
    void testEquals() {
        /*
        added these in the test themselves because the setter messed with the values so it would fail the test otherwise
         */
        Bankaccount equalsTest = new Bankaccount("NL29INGB3506581023", "testUser1", "INGBNL2A");
        Bankaccount equalsTest1 = new Bankaccount("NL29INGB3506581023", "testUser1", "INGBNL2A");
        assertEquals(equalsTest,equalsTest1);
    }

    @Test
    void testHashCode() {
        /*
        added these in the test themselves because the setter messed with the values so it would fail the test otherwise
         */
        Bankaccount hashTest = new Bankaccount("NL29INGB3506581023", "testUser1", "INGBNL2A");
        Bankaccount hashTest1 = new Bankaccount("NL29INGB3506581023", "testUser1", "INGBNL2A");
        assertTrue(hashTest.equals(hashTest1) && hashTest1.equals(hashTest));
        assertEquals(hashTest.hashCode(), hashTest1.hashCode());
    }

    @Test
    void setHolder() {
        bankaccount1.setHolder("testUser2");
        assertEquals("testUser2", bankaccount1.getHolder());
    }
}