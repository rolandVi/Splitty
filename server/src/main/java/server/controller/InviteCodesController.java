package server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import server.service.EventService;

@Controller
@RequestMapping("/")
public class InviteCodesController {
    private final EventService eventService;

    /**
     * The constructor of the controller
     * @param eventService teh event service
     */
    public InviteCodesController(EventService eventService) {
        this.eventService = eventService;
    }


    /**
     * Endpoint for accessing all invite codes
     * @param model the model
     * @return the view
     */
    @GetMapping("invites")
    public String getAllInviteCodes(Model model){
        model.addAttribute("invites", this.eventService.getAllInvites());

        return "inviteCodes";
    }
}
