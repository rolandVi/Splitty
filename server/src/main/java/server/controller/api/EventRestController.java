package server.controller.api;

import commons.EventEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
     * Controller when accessing /api/events/{id}
     * @param id the id of the event that is present in the URL
     * @return ResponseEntity with badRequest status if invalid id was presented
     *         or ResponseEntity with the request event as body
     */
    @GetMapping(name = "/{id}", produces = "application/json")
    public ResponseEntity<EventEntity> getById(@PathVariable(name = "id") long id){
        if (!checkIdValidity(id)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this.eventService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable(name = "id") long id){
        if (!checkIdValidity(id)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<EventEntity> create(@RequestBody EventEntity event){
        if (!checkEventValidity(event)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this.eventService.save(event));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventEntity> updateEventTitleById(@PathVariable(name = "id") long id, @RequestBody String title){
        if (!checkIdValidity(id) || title.isBlank()){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this.eventService.updateById(id, title));
    }

    private boolean checkIdValidity(long id){
        return id>0 && this.eventService.existsById(id);
    }

    private boolean checkEventValidity(EventEntity event){
        return event!=null && !event.getInviteCode().isBlank() && !event.getTitle().isBlank();
    }
}
