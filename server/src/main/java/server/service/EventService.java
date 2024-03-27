package server.service;

import commons.EventEntity;
import commons.UserEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import server.controller.exception.ObjectNotFoundException;
import server.dto.view.EventDetailsDto;
import server.dto.view.EventOverviewDto;
import server.dto.view.EventTitleDto;
import server.dto.view.UserNameDto;
import server.repository.EventRepository;
import server.repository.UserRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;


    /**
     * Constructor Injection
     *
     * @param eventRepository the EventEntity repository
     * @param modelMapper     the ModelMapper injected by Spring
     * @param jdbcTemplate
     * @param userRepository
     */
    public EventService(EventRepository eventRepository,
                        ModelMapper modelMapper, JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
//        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;

    }

    /**
     * Checks if an EventEntity with such id exists
     * @param id the id of the requested EventEntity
     * @return true if it exists and false - if it doesn't
     */
    public boolean existsById(long id) {
        return this.eventRepository.existsById(id);
    }


    /**
     * Getting an EventEntity by id
     * @param id the id of the requested EventEntity
     * @return the requested EventEntity
     */
    public EventDetailsDto getById(long id) {
        var event=this.eventRepository.findById(id)
                .orElseThrow(ObjectNotFoundException::new);
        return this.modelMapper.map(event, EventDetailsDto.class);
    }

    /**
     * Removing and event by its id
     * @param id the id of the event we want to delete
     */
    @Transactional
    public void removeById(long id){
        this.eventRepository.deleteById(id);
    }

    /**
     * Updating the title of an event
     * @param id the id of the event we want to update
     * @param title the new title to give the event
     * @return the updated event
     */
    @Transactional
    public EventTitleDto updateById(long id, @Valid EventTitleDto title) {
        this.eventRepository.updateEventTitleById(id, title.getTitle());
        EventEntity eventTitleById = this.eventRepository.findById(id)
                .orElseThrow(ObjectNotFoundException::new);
        eventTitleById.updateLastModifiedDate();
        EventTitleDto result=this.modelMapper
                .map(eventTitleById, EventTitleDto.class);
        return result;
    }

    /**
     * Persist an event to the database
     * @param event the title of the event in a EventPersistDto
     * @return the created EventEntity
     */
    public EventEntity saveEventByTitle(EventTitleDto event) {
        EventEntity entity = this.modelMapper.map(event, EventEntity.class);
        entity.setInviteCode(this.generateInviteCode(entity.getTitle()));
        return this.eventRepository.save(entity);
    }

    //TODO: improve generate invite code method
    private String generateInviteCode(String title) {
        return title + LocalTime.now().getSecond() + LocalTime.now().getNano();
    }


    /**
     * Create a new event given a title
     * @param title the title
     * @return the title and id of the event
     */
    public EventEntity createEvent(String title) {
        EventEntity newEntity = new EventEntity();
        newEntity.setTitle(title);
        newEntity.setInviteCode(generateInviteCode(title));
        return this.eventRepository.save(newEntity);
    }

    /**
     * Get all events
     * @return all events from the database
     */
    public List<EventOverviewDto> getAllEvents() {
        return this.eventRepository.findAll().stream()
                .map(e -> this.modelMapper.map(e, EventOverviewDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Returns all the participants of a particular event
     * @param eventId the event id
     * @return the participants
     */
    public List<UserNameDto> getEventParticipants(long eventId) {
        return this.eventRepository.findEventParticipants(eventId)
                .stream()
                .map(p -> this.modelMapper.map(p, UserNameDto.class))
                .collect(Collectors.toList());
    }

    /**
     * find an event by its id
     * @param eventId the event id
     * @return the event
     */
    public EventEntity findEntityById(long eventId) {
        return this.eventRepository.findById(eventId)
                .orElseThrow(ObjectNotFoundException::new);
    }

    /**
     * finds an event by its invite code
     * @param inviteCode the invite code
     * @return the event
     */
    public EventEntity findEntityByInviteCode(String inviteCode) {
        return this.eventRepository.findEventEntityByInviteCode(inviteCode)
                .orElseThrow(ObjectNotFoundException::new);
    }

    public EventDetailsDto saveEvent(EventDetailsDto eventDetailsDto) {
        EventEntity entity = new EventEntity();
        entity.setInviteCode(eventDetailsDto.getInviteCode());
        entity.setTitle(eventDetailsDto.getTitle());

        // Map expenses DTOs to entities
        //todo implement this since I don't have access to expenses yet
        /*
        Set<ExpenseEntity> expenseEntities = eventDetailsDto.getExpenses().stream()
                .map(expenseDto -> modelMapper.map(expenseDto, ExpenseEntity.class))
                .collect(Collectors.toSet());
        entity.setExpenses(expenseEntities);
        */

        // Map participants DTOs to entities ,chatgpt
//        Set<UserEntity> userEntities = eventDetailsDto.getParticipants().stream()
//                .map(userDto -> modelMapper.map(userDto, UserEntity.class))
//                .collect(Collectors.toSet());

        //
//        Set<UserNameDto> userDtos = eventDetailsDto.getParticipants();
//        for (UserNameDto userNameDto: userDtos){
//            joinRestore(entity.getInviteCode(), userNameDto.getId());
//        }

        Set<UserEntity> userEntities = eventDetailsDto.getParticipants().stream()
                .map(userNameDto -> {
                    // Assuming there's a service or repository method to retrieve UserEntity by ID
                    UserEntity userEntity = findById(userNameDto.getId());
                    // If userEntity is null, you might want to handle this case (e.g., throw an exception)
                    if (userEntity == null) {
                        throw new IllegalArgumentException("User with ID " + userNameDto.getId() + " not found.");
                    }
                    return userEntity;
                })
                .collect(Collectors.toSet());
        entity.setParticipants(userEntities);

        // Save the entity and return the saved details
        EventEntity savedEntity = modelMapper.map(eventDetailsDto, EventEntity.class);
        savedEntity = eventRepository.save(savedEntity);

        // Map saved entity back to DTO
        EventDetailsDto savedDto = modelMapper.map(savedEntity, EventDetailsDto.class);
        return savedDto;
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


}
