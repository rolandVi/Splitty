package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankaccountTest {

    /*
     random IBAN generated via http://www.randomiban.com/?country=Netherlands
     */

    Bankaccount bankaccount = new Bankaccount("NL29INGB3506581023", "testUser1", "INGBNL2A");
    Bankaccount bankaccount1 = new Bankaccount("NL29INGB3506581023", "testUser1", "INGBNL2A");

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
        assertEquals(bankaccount,bankaccount1);
    }

    @Test
    void testHashCode() {
        Bankaccount hashTest = new Bankaccount("NL29INGB3506581023", "testUser1", "INGBNL2A");
        Bankaccount hashTest1 = new Bankaccount("NL29INGB3506581023", "testUser1", "INGBNL2A");
        assertTrue(hashTest.equals(hashTest1) && hashTest1.equals(hashTest));
        assertEquals(hashTest.hashCode(), hashTest1.hashCode());
    }
}