package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EventEntityTest {
    private EventEntity event;

    @BeforeEach
    public void initEvent() {

        List<ExpenseEntity> expenses = new ArrayList<>();
        List<UserEntity> users = new ArrayList<>();

        event = new EventEntity(1L, "test_invite_code", "test_password", expenses, users);
    }

    @Test
    public void testIdGetter() {
        assertEquals(1L, this.event.getId());
    }

    @Test
    public void testInviteCodeGetter() {
        assertEquals("test_invite_code", this.event.getInviteCode());
    }

    @Test
    public void testPasswordGetter() {
        assertEquals("test_password", this.event.getPassword());
    }

    @Test
    public void testExpensesGetter() {
        List<ExpenseEntity> expectedExpenses = new ArrayList<>();

        assertEquals(expectedExpenses, this.event.getExpenses());
    }

    @Test
    public void testUsersGetter() {
        List<UserEntity> expectedUsers = new ArrayList<>();

        assertEquals(expectedUsers, this.event.getUsers());
    }

    @Test
    public void testEqualsWithTheSameObject() {
        assertTrue(this.event.equals(this.event));
    }

    @Test
    public void testEqualsWithEqualObject(){
        List<ExpenseEntity> expenses = new ArrayList<>();
        List<UserEntity> users = new ArrayList<>();

        EventEntity eventEntity = new EventEntity(1L, "test_invite_code",
                "test_password", expenses, users);

        assertEquals(this.event, eventEntity);
    }

    @Test
    public void testEqualsWhenNotEqual(){
        List<ExpenseEntity> expenses = new ArrayList<>();
        List<UserEntity> users = new ArrayList<>();

        EventEntity eventEntity = new EventEntity(2L, "test_invite_code",
                "test_password", expenses, users);

        assertNotEquals(this.event, eventEntity);
    }

    @Test
    public void testSameHash(){
        List<ExpenseEntity> expenses = new ArrayList<>();
        List<UserEntity> users = new ArrayList<>();

        EventEntity eventEntity = new EventEntity(1L, "test_invite_code",
                "test_password", expenses, users);

        assertEquals(this.event.hashCode(), eventEntity.hashCode());
    }
}
