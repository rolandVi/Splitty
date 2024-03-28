package server.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


@Service
public class EmailService {

    private final JavaMailSenderImpl emailSender;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    /**
     *
     * @param emailSender i dont know
     */
    @Autowired
    public EmailService(JavaMailSenderImpl emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * a
     * @param to a
     * @param invite a
     */
    public void sendSimpleMessage(String to, String invite) {
        String subject = "Invitation to an Event";
        String text = "You have been invited to an event with invite code: " + invite;
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            emailSender.setHost(host);
            emailSender.setPort(port);
            emailSender.setUsername(username);
            emailSender.setPassword(password);

            emailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
