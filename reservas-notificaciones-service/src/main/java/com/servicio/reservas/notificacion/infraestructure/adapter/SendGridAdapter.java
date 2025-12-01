package com.servicio.reservas.notificacion.infraestructure.adapter;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.servicio.reservas.notificacion.domain.entities.NotificationType;
import com.servicio.reservas.notificacion.infraestructure.exception.EmailSendingException;
import com.servicio.reservas.notificacion.domain.port.INotificationClientPort;
import com.servicio.reservas.notificacion.infraestructure.template.EmailTemplateService; // Necesario para obtener el HTML
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendGridAdapter implements INotificationClientPort {

    private final SendGrid sendGridClient;
    private final EmailTemplateService emailTemplateService;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    @Value("${sendgrid.from-name}")
    private String fromName;

    @Override
    public void sendNotification(String recipientEmail, NotificationType type, Map<String, Object> templateModel) throws EmailSendingException {
        try {
            String subject = emailTemplateService.getSubjectForType(type);
            String htmlContent = emailTemplateService.getHtmlContent(type, templateModel);

            Email from = new Email(fromEmail, fromName);
            Email to = new Email(recipientEmail);
            Content content = new Content("text/html", htmlContent);

            Mail mail = new Mail(from, subject, to, content);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGridClient.api(request);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                log.info("Email sent successfully to {} for type {}. Status Code: {}", recipientEmail, type, response.getStatusCode());
            } else {
                // Log and throw exception on API error
                log.error("Failed to send email to {} for type {}. Status Code: {}, Body: {}", recipientEmail, type, response.getStatusCode(), response.getBody());
                throw new EmailSendingException("SendGrid API returned status code " + response.getStatusCode());
            }

        } catch (IOException e) {
            log.error("IO error while sending email to {} for type {}: {}", recipientEmail, type, e.getMessage());
            throw new EmailSendingException("IO error during SendGrid email sending", e);
        }
    }
}