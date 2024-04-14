package dto.view;

import dto.ExpenseCreationDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ExpenseCreationDtoTest {

    private ExpenseCreationDto expenseCreationDto;

    @BeforeEach
    void setUp() {
        expenseCreationDto = new ExpenseCreationDto();
    }

    @Test
    void testGetSetTitle() {
        expenseCreationDto.setTitle("Test Title");
        assertEquals("Test Title", expenseCreationDto.getTitle());
    }

    @Test
    void testGetSetMoney() {
        expenseCreationDto.setMoney(100.0);
        assertEquals(100.0, expenseCreationDto.getMoney());
    }

    @Test
    void testGetSetAuthorId() {
        expenseCreationDto.setAuthorId(123L);
        assertEquals(123L, expenseCreationDto.getAuthorId());
    }

    @Test
    void testGetSetDebtors() {
        Set<ParticipantNameDto> debtors = new HashSet<>();
        ParticipantNameDto debtor1 = new ParticipantNameDto();
        debtors.add(debtor1);
        expenseCreationDto.setDebtors(debtors);
        assertEquals(debtors, expenseCreationDto.getDebtors());
    }

    @Test
    void testGetSetEventId() {
        expenseCreationDto.setEventId(456L);
        assertEquals(456L, expenseCreationDto.getEventId());
    }

    @Test
    void testGetSetDate() {
        Date date = new Date();
        expenseCreationDto.setDate(date);
        assertEquals(date, expenseCreationDto.getDate());
    }

    @Test
    void testGetSetTag() {
        TagDto tagDto = new TagDto();
        expenseCreationDto.setTag(tagDto);
        assertEquals(tagDto, expenseCreationDto.getTag());
    }

    @Test
    void testEquals() {
        ExpenseCreationDto dto1 = new ExpenseCreationDto("Title",
                100.0, 123L, new HashSet<>(), 456L, new Date(), null);
        ExpenseCreationDto dto2 = new ExpenseCreationDto("Title",
                100.0, 123L, new HashSet<>(), 456L, new Date(), null);
        ExpenseCreationDto dto3 = new ExpenseCreationDto("Different",
                100.0, 123L, new HashSet<>(), 456L, new Date(), null);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testHashCode() {
        ExpenseCreationDto dto1 = new ExpenseCreationDto("Title", 100.0,
                123L, new HashSet<>(), 456L, new Date(), null);
        ExpenseCreationDto dto2 = new ExpenseCreationDto("Title", 100.0,
                123L, new HashSet<>(), 456L, new Date(), null);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
