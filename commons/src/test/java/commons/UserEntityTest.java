package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest {
    private ParticipantEntity user;

    @BeforeEach
    public void initUser() {

        this.user = new ParticipantEntity(1L, "FirstName", "LastName", "email@gmail.com",
                new EventEntity(), new BankAccountEntity());
    }

    @Test
    public void testIdGetter() {
        assertEquals(1, this.user.getId());
    }

    @Test
    public void testFirstNameGetter() {
        assertEquals("FirstName", this.user.getFirstName());
    }

    @Test
    public void testLastNameGetter() {
        assertEquals("LastName", this.user.getLastName());
    }

    @Test
    public void testEmailGetter() {
        assertEquals("email@gmail.com", this.user.getEmail());
    }

    @Test
    public void testEventsGetter() {
        assertEquals(new EventEntity(), this.user.getEvent());
    }

    @Test
    public void testBankAccountGetter() {
        assertEquals(new BankAccountEntity(), this.user.getBankAccount());
    }

    @Test
    public void testEqualsWithTheSameObject() {
        assertTrue(this.user.equals(this.user));
    }

    @Test
    public void testEqualsWithEqualObject(){
        ParticipantEntity userEntity = new ParticipantEntity(1L, "FirstName", "LastName",
                "email@gmail.com", new EventEntity(), new BankAccountEntity());
        assertEquals(this.user, userEntity);
    }

    @Test
    public void testEqualsWhenNotEqual(){
        ParticipantEntity userEntity = new ParticipantEntity(2L, "", "", "", new EventEntity(), new BankAccountEntity());
        assertNotEquals(this.user, userEntity);
    }

    @Test
    public void testSameHash(){
        ParticipantEntity userEntity = new ParticipantEntity(1L, "FirstName", "LastName",
                "email@gmail.com", new EventEntity(), new BankAccountEntity());
        assertEquals(this.user.hashCode(), userEntity.hashCode());
    }

    @Test
    void testEmptyConstructor() {
        ParticipantEntity emptyUser = new ParticipantEntity();
        assertNotNull(emptyUser);
    }

    @Test
    void testSetFirstName() {
        String newFirstName = "Jane";
        this.user.setFirstName(newFirstName);
        assertEquals(newFirstName, this.user.getFirstName());
    }

    @Test
    void testSetLastName() {
        String newLastName = "Smith";
        this.user.setLastName(newLastName);
        assertEquals(newLastName, this.user.getLastName());
    }

    @Test
    void testSetEmail() {
        String newEmail = "janesmith@example.com";
        this.user.setEmail(newEmail);
        assertEquals(newEmail, this.user.getEmail());
    }

    @Test
    void testSetBankAccount() {
        BankAccountEntity newBankAccount = new BankAccountEntity();

        this.user.setBankAccount(newBankAccount);
        assertEquals(newBankAccount, this.user.getBankAccount());
    }
}
