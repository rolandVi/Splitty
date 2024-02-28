package server.service;

import commons.EventEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.dto.view.EventDetailsDto;
import server.dto.view.EventTitleDto;
import server.repository.EventRepository;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    /**
     * Constructor Injection
     *
     * @param eventRepository the EventEntity repository
     * @param modelMapper the ModelMapper injected by Spring
     */
    public EventService(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
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
        return this.modelMapper.map(this.eventRepository.getReferenceById(id), EventDetailsDto.class);
    }

    /**
     * Removing and event by its id
     * @param id the id of the event we want to delete
     */
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
        return this.modelMapper.map(this.eventRepository.updateEventTitleById(id, title.getTitle()), EventTitleDto.class);
    }

    /**
     * Persist an event to the database
     * @param event the title of the event in a EventPersistDto
     * @return the created EventEntity
     */
    public EventEntity saveEventByTitle(EventTitleDto event) {
        EventEntity entity = this.modelMapper.map(event, EventEntity.class);
        entity.setInviteCode(this.generateInviteCode());
        return this.eventRepository.save(entity);
    }

    //TODO: create a method to generate Invite code
    private String generateInviteCode() {
        return "";
    }
}
