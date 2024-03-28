package server.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.EmailService;

@RestController
public class EmailRestController {

    private final EmailService emailService;

    /**
     *
     * @param emailService email service class
     */
    @Autowired
    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     *
     * @param to email of the invited participant
     * @param invite invite code of the event
     * @return String info that it was sent
     */
    @GetMapping("/send-email")
    public String sendEmail(String to, String invite) {
        String toTest = "rolikjr@gmail.com";
        String inviteTest = "#inviteCode#";

        emailService.sendSimpleMessage(toTest, inviteTest);

        return "Email Sent!";
    }
}

