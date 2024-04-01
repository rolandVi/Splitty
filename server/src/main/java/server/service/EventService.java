package server.service;

import commons.EventEntity;
import commons.ExpenseEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import server.controller.exception.ObjectNotFoundException;
import dto.view.EventDetailsDto;
import dto.view.EventOverviewDto;
import dto.view.EventTitleDto;
import dto.view.UserNameDto;
import server.repository.EventRepository;

import java.time.LocalTime;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;



    /**
     * Constructor Injection
     *
     * @param eventRepository the EventEntity repository
     * @param modelMapper     the ModelMapper injected by Spring
     * @param userService     the user service
     */
    public EventService(EventRepository eventRepository,
                        ModelMapper modelMapper, @Lazy UserService userService) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
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
     * Assigns an expense the event
     * @param expense expense to be added
     * @return the boolean, whether the operation was successful
     */
    public EventEntity addExpense(ExpenseEntity expense){
        EventEntity modifiedEvent = expense.getEvent();
        modifiedEvent.getExpenses().add(expense);
        return eventRepository.save(modifiedEvent);
    }

    /**
     * Removes an expense from the event
     * @param eventId id of the event to remove expense from
     * @param expense the expense entity to remove
     */
    public void removeExpense(Long eventId, ExpenseEntity expense){
        EventEntity modifiedEvent = findEntityById(eventId);
        modifiedEvent.removeExpense(expense);
        eventRepository.save(modifiedEvent);
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

    /**
     * Method for restoring an event via the admin restore scene
     * @param eventDetailsDto An eventDetailsDto from the imported JSON dump
     * @return the eventDetailsDto from the mapped entity from the initial Dto
     */
    public EventDetailsDto saveEvent(EventDetailsDto eventDetailsDto) {
        EventEntity entity = new EventEntity();
        entity.setInviteCode(eventDetailsDto.getInviteCode());
        entity.setTitle(eventDetailsDto.getTitle());
        entity.setCreationDate(eventDetailsDto.getCreationDate());
        entity=this.eventRepository.save(entity);

        // Map expenses DTOs to entities
        //todo implement this since I don't have access to expenses yet

        for (UserNameDto user : eventDetailsDto.getParticipants()) {
            this.userService.join(entity.getInviteCode(), user.getId());
        }

        // Map saved entity back to DTO
        EventDetailsDto savedDto = modelMapper.map(entity, EventDetailsDto.class);
        return savedDto;
    }
}
