package server.controller.api;

import commons.EventEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dto.view.EventDetailsDto;
import server.dto.view.EventParticipantsDto;
import server.dto.view.EventTitleDto;
import server.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

    private final EventService eventService;

    /**
     * Constructor injection
     * @param eventService the Service for the Event Entity
     */
    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Access the event with the given id
     * @param id the id of the event that is present in the URL
     * @return ResponseEntity with badRequest status if invalid id was presented
     *         or ResponseEntity with the requested event as body
     */
    @GetMapping(name = "/{id}", produces = "application/json")
    public ResponseEntity<EventDetailsDto> getById(@PathVariable(name = "id") long id){
        if (!checkIdValidity(id)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this.eventService.getById(id));
    }

    /**
     * Delete an event with the given id
     * @param id the id of the event to access
     * @return ResponseEntity with badRequest status if invalid id was presented
     *         or ok status if it was deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable(name = "id") long id){
        if (!checkIdValidity(id)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Update the title of the event
     * @param id the id of the wanted event
     * @param eventTitle the new title
     * @return ResponseEntity with badRequest status if invalid id was presented
     *       or ResponseEntity with the requested event as body
     */
    @PatchMapping("/{id}")
    public ResponseEntity<EventTitleDto> updateEventTitleById(@PathVariable(name = "id") long id,
                                                   @Valid @RequestBody EventTitleDto eventTitle){
        if (!checkIdValidity(id)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this.eventService.updateById(id, eventTitle));
    }

    @PostMapping("/")
    public ResponseEntity<EventTitleDto> createEvent(@RequestBody String title){
        if (title.isBlank()){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this.eventService.createEvent(title));
    }

    @PatchMapping("/{id}/add")
    public ResponseEntity<EventParticipantsDto> addParticipant(@PathVariable(name = "id") long eventId, @RequestBody long userId){
        if (!checkIdValidity(eventId) || !checkIdValidity(userId)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this.eventService.addParticipant(eventId, userId));
    }

    /**
     * Helper method to check if and id is valid
     * @param id the id to check
     * @return true if it is valid
     *      and false if it is invalid
     */
    private boolean checkIdValidity(long id){
        return id>0 && this.eventService.existsById(id);
    }
}
