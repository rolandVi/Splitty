package server.service;

import commons.EventEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.controller.exception.ObjectNotFoundException;
import server.dto.view.EventDetailsDto;
import server.dto.view.EventTitleDto;
import server.repository.EventRepository;

import java.time.LocalTime;

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
     * @param userService     the userService
     */
    public EventService(EventRepository eventRepository,
                        ModelMapper modelMapper, UserService userService) {
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
        return this.modelMapper.map(this.eventRepository.findById(id)
                .orElseThrow(ObjectNotFoundException::new), EventDetailsDto.class);
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
        return this.modelMapper.map(this.eventRepository.getEventTitleById(id)
                .orElseThrow(ObjectNotFoundException::new), EventTitleDto.class);
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
}
