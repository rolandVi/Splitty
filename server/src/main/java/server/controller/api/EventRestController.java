package server.controller.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dto.view.EventDetailsDto;
import server.dto.view.EventOverviewDto;
import server.dto.view.EventTitleDto;
import server.dto.view.UserNameDto;
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
     * Adding an expense to an event
     * @param eventId the event id
     * @param expenseId the expense id
     * @return the response entity, defining whether the operation was successful
     */
    @PatchMapping("/{id}/add_expense")
    public ResponseEntity<Void> addExpense(@PathVariable(name = "id") long eventId,
                                           @RequestBody long expenseId){
        this.eventService.addExpense(eventId, expenseId);
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
     * Endpoint to get the participants of an event
     * @param eventId the event id
     * @return the participants
     */
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<UserNameDto>> getEventParticipants(
            @PathVariable(name = "id") long eventId){
        return ResponseEntity.ok(this.eventService.getEventParticipants(eventId));
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
