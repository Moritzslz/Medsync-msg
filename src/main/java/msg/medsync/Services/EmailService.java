package msg.medsync.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import msg.medsync.Models.Patient;
import msg.medsync.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.time.ZoneId;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final ZoneId berlinZone = ZoneId.of("Europe/Berlin");

    public EmailService(PatientRepository patientRepository, JavaMailSender emailSender) {
        this.emailSender =  emailSender;
    }

    @Async()
    public void sendEmail(Patient patient, String emailText, String emailSubject, URI link, String buttonText, String expiresIn) {
        try {
            String emailHTML = buildEmail(patient.getName(), emailText, link, buttonText, expiresIn);

            MimeMessage mimeMessage = emailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setText(emailHTML, true);
            mimeMessageHelper.setTo(patient.getEmail());
            mimeMessageHelper.setSubject(emailSubject);
            mimeMessageHelper.setFrom("noreply@medsync.com", "Medsync");

            emailSender.send(mimeMessage);
            System.out.println("Sent email to " + patient.getPatientId());

        } catch (MessagingException e) {
            System.out.println("Error sending email to " + patient.getPatientId());
            throw new IllegalArgumentException(e);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error sending email to " + patient.getPatientId());
            throw new RuntimeException(e);
        }
    }

    private static String buildEmail(String name, String emailText, URI link, String buttonText, String expiresIn) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"de\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>JobBot validation Email</title>\n" +
                "</head>\n" +
                "<body style=\"font-family: Helvetica, sans-serif; color: #272D2D\">\n" +
                "<div style=\"max-width: 600px; margin: 0 auto; padding: 1rem;\">\n" +
                "    <h1 style=\"color: #33CC7B\">" + buttonText + "</h1>\n" +
                "    <h2>Hi " + name + ",</h2>\n" +
                "    <p>" + emailText + "</p>\n" +
                "    <br>\n" +
                "    <a href=\"" + link + "\" target=\"_blank\" style=\"display: inline-block; min-width: 10rem; max-width: 20rem; padding: 0.75rem 1rem 0.75rem 1rem; margin: auto 0 auto 0; border-radius: 2rem; font-weight: bold; font-size: 1rem; text-align: center; text-decoration: none; color: #FFFFFF; background-color: #33CC7B; border: 3px solid #33CC7B; cursor: pointer;\">" + buttonText + "</a>\n" +
                "    <br>\n" +
                "    <br>\n" +
                "    <p>Bitte öffne diesen Link in deinem Browser, falls der Button nicht funktioniert:<br>" + link + "</p>\n" +
                "    <br>\n" +
                "    <p>Der Link wird in <strong style=\"color: #33CC7B\">" + expiresIn + "</strong> ablaufen.</p>\n" +
                "    <p>Sollte es ein Problem geben, antworte nicht auf diese Email, sondern sende uns bitte dein Anliegen an support@jobbot.wtf.</p>\n" +
                "    <br>\n" +
                "    <p>Liebe Grüße und viel Erfolg mit deinen Bewerbungen, </p>\n" +
                "    <p>Dein JobBot Team</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }
}
