package server.service;

import commons.EventEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.controller.exception.ObjectNotFoundException;
import server.dto.view.*;
import server.repository.EventRepository;

import java.time.LocalTime;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ExpenseService expenseService;

    /**
     * Constructor Injection
     *
     * @param eventRepository the EventEntity repository
     * @param modelMapper     the ModelMapper injected by Spring
     * @param userService     the userService
     * @param expenseService the expense service
     */
    public EventService(EventRepository eventRepository,
                        ModelMapper modelMapper, UserService userService,
                        ExpenseService expenseService) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.expenseService = expenseService;
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
    public EventTitleDto createEvent(String title) {
        EventEntity newEntity=new EventEntity();
        newEntity.setTitle(title);
        newEntity.setInviteCode(generateInviteCode(title));
        EventEntity result=this.eventRepository.save(newEntity);
        return modelMapper.map(result, EventTitleDto.class);
    }

    /**
     * Adding a participant in the event
     * @param eventId the event id
     * @param userId the user id
     * @return true if the operation was successful and false otherwise
     *
     */
    public Boolean addParticipant(long eventId, long userId) {
        EventEntity event=this.eventRepository.findById(eventId)
                        .orElseThrow(ObjectNotFoundException::new);
        event.getParticipants().add(this.userService.findById(userId));
        this.eventRepository.save(event);
        return true;
    }

    /**
     * Deleting a participant from an event
     * @param eventId the event id
     * @param userId the user id
     * @return true if it was deleted successfully and false otherwise
     */
    public boolean deleteParticipant(long eventId, long userId) {
        if (this.eventRepository.existsById(eventId) ||
                userService.existsById(userId)) return false;

        EventEntity event=this.eventRepository.findById(eventId)
                        .orElseThrow(IllegalArgumentException::new);
        event.getParticipants().remove(this.userService.findById(userId));
        this.eventRepository.save(event);
        return true;
    }

    /**
     * Retrieves all participants of the specified event
     * @param eventId the id of the event
     * @return the list of all participants of the event
     */
    public List<UserNameDto> getParticipantsById(long eventId) {
        return eventRepository.getParticipantsById(eventId).stream()
                .map(p -> this.modelMapper.map(p, UserNameDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Assigns an expense the event
     * @param eventId the id of the event
     * @param expenseId the id of the expense
     * @return the boolean, whether the operation was successful
     */
    public boolean addExpense(long eventId, long expenseId){
        EventEntity event = this.eventRepository.findById(eventId)
                .orElseThrow(ObjectNotFoundException::new);
        event.getExpenses().add(this.expenseService.getEntityById(expenseId));
        return true;
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
}
