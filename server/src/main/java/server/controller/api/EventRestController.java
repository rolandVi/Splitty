package server.controller.api;

import commons.ExpenseEntity;
import dto.CreatorToTitleDto;
import dto.ParticipantCreationDto;
import dto.view.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.exception.FieldValidationException;
import server.service.EventService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

    private final EventService eventService;

    /**
     * Constructor injection
     *
     * @param eventService the Service for the Event Entity
     */
    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Access the event with the given id
     *
     * @param id the id of the event
     * @return ResponseEntity with badRequest status if invalid id was presented
     * or ResponseEntity with the requested event as body
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<EventDetailsDto> getById(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(this.eventService.getById(id));
    }

    /**
     * Returns the event details of an event with the given invite code
     *
     * @param code the invite code
     * @return the event
     */
    @GetMapping("/invites/{inviteCode}")
    public ResponseEntity<EventDetailsDto> getByInviteCode(
            @PathVariable("inviteCode") String code) {
        return ResponseEntity.ok(this.eventService.getByInviteCode(code));
    }

    /**
     * Creates an event with the given title
     *
     * @param creatorToTitleDto the title of the new event along with the id of the creator
     * @return the newly created event title and id
     */
    @PostMapping("/")
    public ResponseEntity<EventDetailsDto> createEvent(
            @Valid @RequestBody CreatorToTitleDto creatorToTitleDto){
        EventDetailsDto event = this.eventService.createEvent(creatorToTitleDto);
        ModelMapper modelMapper = new ModelMapper();
        listeners.forEach((k, l) -> l.accept(modelMapper.map(event, EventOverviewDto.class)));
        return ResponseEntity.ok(event);

    }

    /**
     * Delete an event with the given id
     *
     * @param id the id of the event to access
     * @return ResponseEntity with badRequest status if invalid id was presented
     * or ok status if it was deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable(name = "id") long id) {
        if (!checkIdValidity(id)) {
            return ResponseEntity.badRequest().build();
        }
        this.eventService.removeById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Update the title of the event
     *
     * @param id         the id of the wanted event
     * @param eventTitle the new title
     * @return ResponseEntity with badRequest status if invalid id was presented
     * or ResponseEntity with the requested event as body
     */
    @PatchMapping("/{id}")
    public ResponseEntity<EventTitleDto> updateEventTitleById(@PathVariable(name = "id") long id,
                                                              @Valid @RequestBody EventTitleDto eventTitle) {
        return ResponseEntity.ok(this.eventService.updateById(id, eventTitle));
    }


    /**
     * Adding an expense to an event
     *
     * @param eventId the event id
     * @param expense the expense creation details
     * @return the response entity, defining whether the operation was successful
     */
    @PatchMapping("/{id}/add_expense")
    public ResponseEntity<Void> addExpense(@PathVariable(name = "id") long eventId,
                                           @RequestBody ExpenseEntity expense) {
        this.eventService.addExpense(expense);
        return ResponseEntity.ok().build();
    }

    /**
     * Get all events endpoint
     *
     * @return all events
     */
    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<EventOverviewDto>> getAllEvents() {
        return ResponseEntity.ok(this.eventService.getAllEvents());
    }

    private Map<Object, Consumer<EventOverviewDto>> listeners = new HashMap<>();

    /**
     * Get updates
     * @return all events
     */
    @GetMapping("/updates")
    public DeferredResult<ResponseEntity<EventOverviewDto>> getUpdates(){
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<EventOverviewDto>>(5000L, noContent);

        var key = new Object();
        listeners.put(key, q -> {
            res.setResult(ResponseEntity.ok(q));
        });

        res.onCompletion(() -> {
            listeners.remove(key);
        });

        return res;
    }

    /**
     * Endpoint to get the participants of an event
     *
     * @param eventId the event id
     * @return the participants
     */
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantNameDto>> getEventParticipants(
            @PathVariable(name = "id") long eventId) {
        return ResponseEntity.ok(this.eventService.getEventParticipants(eventId));
    }

    /**
     * Create a new event and persist it in the database
     *
     * @param eventDetailsDto JSON object representing the event details
     * @return ResponseEntity with the created event details
     */
    @PostMapping("/restore")
    public ResponseEntity<EventDetailsDto> createEvent
    (@Valid @RequestBody EventDetailsDto eventDetailsDto) {
        EventDetailsDto createdEvent = eventService.saveEvent(eventDetailsDto);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);

    }

    /**
     * creates a user with the given parameters
     *
     * @param eventId the id of the event - path variable
     * @param user    user
     * @param result  the validation result
     * @return the newly created user and id
     */
    @PostMapping("/{eventId}/participants")
    public ResponseEntity<ParticipantNameDto> addParticipant(
            @PathVariable(name = "eventId") Long eventId,
            @Valid @RequestBody ParticipantCreationDto user,
            BindingResult result) {
        if (result.hasErrors()) {
            throw new FieldValidationException("Invalid participant details");
        }
        return ResponseEntity.ok(this.eventService.addParticipant(eventId, user));
    }

    /**
     * Delete a participant
     *
     * @param eventId       the event id
     * @param participantId the participantId
     * @return whether the request was successful
     */
    @DeleteMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<Void> deleteParticipant(
            @PathVariable(name = "eventId") Long eventId,
            @PathVariable(name = "participantId") Long participantId) {
        this.eventService.deleteParticipant(eventId, participantId);
        return ResponseEntity.ok().build();
    }


    /**
     * Get all expenses for a specific event.
     *
     * @param id The ID of the event.
     * @return ResponseEntity containing the list of expenses for the event.
     */
    @GetMapping("/{id}/expenses")
    public ResponseEntity<List<ExpenseDetailsDto>>
    getAllExpensesForEvent(@PathVariable("id") long id) {
        List<ExpenseDetailsDto> expenses = eventService.getAllExpensesForEvent(id);
        return ResponseEntity.ok(expenses);
    }


    /**
     * Helper method to check if and id is valid
     *
     * @param id the id to check
     * @return true if it is valid
     * and false if it is invalid
     */
    private boolean checkIdValidity(long id) {
        return id > 0 && this.eventService.existsById(id);
    }


}
