package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserEntityTest {
    private UserEntity user;

    @BeforeEach
    public void initUser() {
        this.user = new UserEntity(1L, "FirstName", "LastName", "email@gmail.com",
                "Some password", true);
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
    public void testPasswordGetter() {
        assertEquals("Some password", this.user.getPassword());
    }

    @Test
    public void testAdminGetter() {
        assertTrue(this.user.isAdmin());
    }

    @Test
    public void testEqualsWithTheSameObject() {
        assertTrue(this.user.equals(this.user));
    }

    @Test
    public void testEqualsWithEqualObject(){
        UserEntity userEntity = new UserEntity(1L, "FirstName", "LastName",
                "email@gmail.com", "Some password", true);
        assertEquals(this.user, userEntity);
    }

    @Test
    public void testEqualsWhenNotEqual(){
        UserEntity userEntity = new UserEntity(2L, "", "", "", "", true);
        assertNotEquals(this.user, userEntity);
    }

    @Test
    public void testSameHash(){
        UserEntity userEntity = new UserEntity(1L, "FirstName", "LastName",
                "email@gmail.com", "Some password", true);
        assertEquals(this.user.hashCode(), userEntity.hashCode());
    }
}
