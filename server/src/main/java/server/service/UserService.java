package server.service;

import commons.EventEntity;
import commons.UserEntity;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.controller.exception.ObjectNotFoundException;
import server.dto.UserCreationDto;
import server.dto.view.EventOverviewDto;
import server.dto.view.UserNameDto;
import server.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final EventService eventService;


    /**
     * Constructor
     *
     * @param modelMapper    the ModelMapper injected by Spring
     * @param userRepository the UserRepository injected by Spring
     * @param eventService
     */
    public UserService(ModelMapper modelMapper, UserRepository userRepository, EventService eventService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.eventService = eventService;
    }

    /**
     * Checks if entity exists by id
     * @param id the id
     * @return true if it exists and false otherwise
     */
    public boolean existsById(long id) {
        return this.userRepository.existsById(id);
    }

    /**
     * Get and entity by id
     * @param userId the id
     * @return the entity
     */
    public UserEntity findById(long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Persist a user to the database
     * @param user the id of the user in a UserNameDto
     * @return the created UserEntity
     */
    public UserEntity saveUserByID(UserNameDto user) {
        UserEntity userEntity = this.modelMapper.map(user, UserEntity.class);
        return this.userRepository.save(userEntity);
    }

    /**
     * Create new user given credentials
     * @param user The user details
     * @return the user credentials
     */
    public UserNameDto createUser(UserCreationDto user) {
        UserEntity result = this.userRepository.save(this.modelMapper.map(user, UserEntity.class));
        return modelMapper.map(result, UserNameDto.class);
    }

    /**
     * Get the events of a specific user
     * @param id the id of the user
     * @return the events of the user
     */
    public List<EventOverviewDto> getUserEvents(long id) {
        var result=this.userRepository.getEventsByUserId(id);
        return result.stream().map(e-> this.modelMapper.map(e, EventOverviewDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Checks if such an email exists
     * @param email the email
     * @return true if it exists and false otherwise
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Leaves a particular event
     * @param eventId the event id
     * @param userId the user id
     */
    @Transactional
    public void leave(long eventId, long userId) {
        EventEntity event=eventService.findEntityById(eventId);
        UserEntity user= this.userRepository.findById(userId)
                        .orElseThrow(IllegalArgumentException::new);
        user.leave(event);
        this.userRepository.saveAndFlush(user);
    }


    /**
     * Adding a participant in the event
     * @param inviteCode the event invite code
     * @param userId the user id
     */
    public void join(String inviteCode, long userId) {
        UserEntity user=this.userRepository.findById(userId)
                .orElseThrow(ObjectNotFoundException::new);
        EventEntity event = eventService.findEntityByInviteCode(inviteCode);
        user.join(event);
        userRepository.save(user);
    }
}
