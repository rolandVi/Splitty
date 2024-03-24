package server.service;

import commons.BankAccountEntity;
import commons.EventEntity;
import commons.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import server.dto.view.EventOverviewDto;
import server.repository.UserRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        ModelMapper mapper=new ModelMapper();
        this.userService=new UserService(mapper,userRepository);
    }

    @Test
    void existsById_ShouldExist(){
        long id=1L;
        when(userRepository.existsById(id)).thenReturn(true);

        assertTrue(this.userService.existsById(id));
    }

    @Test
    void existsById_ShouldNotExist(){
        long id=1L;
        when(userRepository.existsById(id)).thenReturn(false);

        assertFalse(this.userService.existsById(id));
    }

    @Test
    void findById_ReturnsUser(){
        // Arrange
        long id = 1L;
        UserEntity user = new UserEntity(id, "vwv", "vwer", "vwer@fewr",
                new HashSet<>(), new BankAccountEntity("12345", "vwve", "1324"));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        UserEntity result = userService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getEvents(), result.getEvents());
        assertEquals(user.getBankAccount(), result.getBankAccount());
    }

    @Test
    void getUserEvents(){
        long id = 1L;
        HashSet<EventEntity> events=new HashSet<>();
        for (long i = 0L; i < 5; i++) {
            events.add(new EventEntity(i, "invCode"+i, "title"+i,
                    new HashSet<>(), new HashSet<>(), new Date(), new Date()));
        }
        UserEntity user = new UserEntity(id, "vwv", "vwer", "vwer@fewr",
                events, new BankAccountEntity("12345", "vwve", "1324"));

        when(this.userRepository.getEventsByUserId(id)).thenReturn(events);

        List<EventOverviewDto> modifiedEvents= events.stream()
                .map(e-> new EventOverviewDto()
                        .setId(e.getId())
                        .setTitle(e.getTitle())
                        .setInviteCode(e.getInviteCode()))
                .collect(Collectors.toList());

        assertEquals(modifiedEvents, this.userService.getUserEvents(id));
    }

    @Test
    void findIdByEmail() {
        String email = "email@mail.com";
        when(this.userRepository.getUserIdByUserEmail(email)).thenReturn(1L);
        assertEquals(1L, userService.findIdByEmail(email));
    }
}
