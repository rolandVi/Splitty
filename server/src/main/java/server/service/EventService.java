package server.service;

import commons.EventEntity;
import org.springframework.stereotype.Service;
import server.repository.EventRepository;

@Service
public class EventService {
    private final EventRepository eventRepository;

    /**
     * Constructor Injection
     * @param eventRepository the EventEntity repository
     */
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
    public EventEntity getById(long id) {
        return this.eventRepository.getReferenceById(id);
    }
}
