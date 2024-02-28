package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class UserEntityTest {
    private UserEntity user;

    @BeforeEach
    public void initUser() {

        this.user = new UserEntity(1L, "FirstName", "LastName", "email@gmail.com",
                new HashSet<>(), new BankAccountEntity());
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
        assertEquals(new HashSet<>(), this.user.getEvents());
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
        UserEntity userEntity = new UserEntity(1L, "FirstName", "LastName",
                "email@gmail.com", new HashSet<>(), new BankAccountEntity());
        assertEquals(this.user, userEntity);
    }

    @Test
    public void testEqualsWhenNotEqual(){
        UserEntity userEntity = new UserEntity(2L, "", "", "", new HashSet<>(), new BankAccountEntity());
        assertNotEquals(this.user, userEntity);
    }

    @Test
    public void testSameHash(){
        UserEntity userEntity = new UserEntity(1L, "FirstName", "LastName",
                "email@gmail.com", new HashSet<>(), new BankAccountEntity());
        assertEquals(this.user.hashCode(), userEntity.hashCode());
    }

    @Test
    void testEmptyConstructor() {
        UserEntity emptyUser = new UserEntity();
        assertNotNull(emptyUser);
    }
}
