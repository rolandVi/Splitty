package server.controller.api;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailRestController {


    /**
     *
     * @param to email of the invited participant
     * @param invite invite code of the event
     * @return String info that it was sent
     */
    @GetMapping("/send-email/{to}/{inviteCode}")
    public String sendEmail(@PathVariable("to")String to,
                            @PathVariable("inviteCode")String invite) {

        Email email = EmailBuilder.startingBlank()
                .from("Splitty", "ssplittyteam37@gmail.com")
                .to("To", to)
                .withSubject("Invitation to an Event")
                .withPlainText("You have been invited to an event with invite code: " + invite)
                .buildEmail();

        MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "ssplittyteam37", "ldge ebrc wcrk zsjf")
                .buildMailer()
                .sendMail(email);

        return "Email send successfully";
    }
}

