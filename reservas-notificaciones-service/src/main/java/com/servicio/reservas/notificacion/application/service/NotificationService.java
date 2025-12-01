package com.servicio.reservas.notificacion.application.service;

import com.servicio.reservas.notificacion.application.dto.NotificationEvent;
import com.servicio.reservas.notificacion.infraestructure.exception.EmailSendingException;
import com.servicio.reservas.notificacion.domain.port.INotificationClientPort;
import com.servicio.reservas.notificacion.infraestructure.template.EmailTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final INotificationClientPort notificationClientPort;
    private final EmailTemplateService emailTemplateService;

    public void processNotificationEvent(NotificationEvent event) {
        log.info("Processing notification event for email: {} of type: {}", event.getRecipientEmail(), event.getNotificationType());

        try {
            // Delegates the actual sending (which involves template retrieval and API call)
            notificationClientPort.sendNotification(
                    event.getRecipientEmail(),
                    event.getNotificationType(),
                    event.getTemplateModel()
            );
            log.info("Notification sent successfully for email: {} of type: {}", event.getRecipientEmail(), event.getNotificationType());
        } catch (EmailSendingException e) {
            log.error("Failed to send notification for email: {} of type: {}. Error: {}",
                    event.getRecipientEmail(), event.getNotificationType(), e.getMessage());
            // TODO: Implement retry logic or send to a Dead-Letter Queue (DLQ)
        }
    }
}