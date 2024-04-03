package server.service;

import commons.EventEntity;
import commons.ExpenseEntity;
import commons.ParticipantEntity;
import dto.CreatorToTitleDto;
import dto.ExpenseCreationDto;
import dto.ParticipantCreationDto;
import dto.view.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import server.exception.ObjectNotFoundException;
import server.repository.EventRepository;

import java.time.LocalTime;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final ParticipantService participantService;

    private final ExpenseService expenseService;


    /**
     * Constructor Injection
     *
     * @param eventRepository the EventEntity repository
     * @param modelMapper     the ModelMapper injected by Spring
     * @param participantService     the user service
     * @param expenseService  the expense service
     */
    public EventService(EventRepository eventRepository,
                        ModelMapper modelMapper, @Lazy ParticipantService participantService,
                        @Lazy ExpenseService expenseService) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.expenseService = expenseService;
        this.participantService = participantService;
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
                .orElseThrow(() -> new ObjectNotFoundException("No such event found"));

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
                .orElseThrow(()-> new ObjectNotFoundException("No such event found"));
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
     * @return the title and id of the event
     */
    public EventDetailsDto createEvent(CreatorToTitleDto creatorToTitleDto) {
        EventEntity newEntity = new EventEntity();
        newEntity.setTitle(creatorToTitleDto.getTitle());
        newEntity.setInviteCode(generateInviteCode(creatorToTitleDto.getTitle()));
        EventEntity event=this.eventRepository.save(newEntity);
        this.addParticipant(event.getId(),
                this.modelMapper.map(creatorToTitleDto, ParticipantCreationDto.class));
        return this.modelMapper.map(event, EventDetailsDto.class);
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
    public List<ParticipantNameDto> getEventParticipants(long eventId) {
        return this.eventRepository.findEventParticipants(eventId)
                .stream()
                .map(p -> this.modelMapper.map(p, ParticipantNameDto.class))
                .collect(Collectors.toList());
    }

    /**
     * find an event by its id
     * @param eventId the event id
     * @return the event
     */
    public EventEntity findEntityById(long eventId) {
        return this.eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("No such event found"));
    }

    /**
     * finds an event by its invite code
     * @param inviteCode the invite code
     * @return the event
     */
    public EventEntity findEntityByInviteCode(String inviteCode) {
        return this.eventRepository.findEventEntityByInviteCode(inviteCode)
                .orElseThrow(() -> new ObjectNotFoundException("No such event found"));
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

        for (ExpenseDetailsDto expense : eventDetailsDto.getExpenses()) {
            ExpenseCreationDto expenseDto = new ExpenseCreationDto();
            expenseDto.setMoney(expense.getMoney());
            expenseDto.setTitle(expense.getTitle());
            expenseDto.setDate(expense.getDate());
            expenseDto.setAuthorId(expense.getAuthor().getId());
            expenseDto.setDebtors(expense.getDebtors());
            expenseDto.setEventId(entity.getId());
            expenseService.createExpense(expenseDto);
        }


        for (ParticipantNameDto user : eventDetailsDto.getParticipants()) {
            ParticipantCreationDto newParticipant=new ParticipantCreationDto()
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setEmail(user.getEmail());
            this.addParticipant(entity.getId(), newParticipant);
        }

        // Map saved entity back to DTO
        EventDetailsDto savedDto = modelMapper.map(entity, EventDetailsDto.class);
        return savedDto;
    }

    /**
     * Create new user given credentials
     * @param eventId the id of the event
     * @param user The user details
     * @return the user credentials
     */
    public ParticipantNameDto addParticipant(Long eventId, ParticipantCreationDto user) {
        EventEntity event=this.eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("No such event found"));
        return this.participantService.createParticipant(user, event);
    }

    /**
     * Deletes a participant of an event
     * @param eventId the eventId
     * @param participantId
     */
    @Transactional
    public void deleteParticipant(Long eventId, Long participantId) {
        this.participantService.deleteParticipant(participantId);
    }

    public EventDetailsDto getByInviteCode(String code) {
        return this.modelMapper.map(
                this.eventRepository.findEventEntityByInviteCode(code)
                        .orElse(new EventEntity()),
                EventDetailsDto.class
        );
    }
}
