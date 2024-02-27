package server.controller.api;

import commons.EventEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @GetMapping("/{id}")
    public ResponseEntity<EventEntity> getById(@PathVariable(name = "id") long id){
        if (id<0 || !this.eventService.existsById(id)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this.eventService.getById(id));
    }
}
