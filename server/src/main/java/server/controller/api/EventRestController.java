package server.controller.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dto.view.EventDetailsDto;
import server.dto.view.EventOverviewDto;
import server.dto.view.EventTitleDto;
import server.service.EventService;

import java.util.List;

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
    @GetMapping(value = "/{id}")
    public ResponseEntity<EventDetailsDto> getById(@PathVariable("id") long id){
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
        this.eventService.removeById(id);
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
        return ResponseEntity.ok(this.eventService.updateById(id, eventTitle));
    }

    /**
     * Creates an event with the given title
     * @param title the title of the new event
     * @return the newly created event title and id
     */
    @PostMapping("/")
    public ResponseEntity<EventTitleDto> createEvent(@NotBlank @RequestBody String title){
        return ResponseEntity.ok(this.eventService.createEvent(title));
    }

    /**
     * Adding a participant to an event
     * @param inviteCode the event invite code
     * @param userId the user id
     * @return whether the operation was successful
     */
    @PostMapping("/add/{invite}")
    public ResponseEntity<Void> addParticipant(@PathVariable(name = "invite") String inviteCode,
                                               @RequestBody long userId){
        this.eventService.addParticipant(inviteCode, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete a participant from an event
     * @param eventId the id of the event
     * @param userId the id of the user
     * @return whether the operation was successful
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteParticipant(@PathVariable(name = "id") long eventId,
                                                  @RequestBody long userId) {
        this.eventService.deleteParticipant(eventId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Get all events endpoint
     * @return all events
     */
    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<EventOverviewDto>> getAllEvents(){
        return ResponseEntity.ok(this.eventService.getAllEvents());
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
