package com.servicio.reservas.notificacion.infraestructure.listener;

import com.servicio.reservas.notificacion.application.dto.NotificationEvent;
import com.servicio.reservas.notificacion.application.service.NotificationService;
import com.servicio.reservas.notificacion.infraestructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentRejectedListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PAYMENT_REJECTED)
    public void handlePaymentRejectedNotification(NotificationEvent event) {
        try {
            log.info("Received PAYMENT_REJECTED event from RabbitMQ: {}", event);

            // Business logic: process the notification (e.g., send an email to the user)
            notificationService.processNotificationEvent(event);

            log.info("Successfully processed PAYMENT_REJECTED event for: {}", event.getRecipientEmail());

        } catch (Exception e) {
            log.error("Failed to process PAYMENT_REJECTED event for {}. Error: {}",
                    event.getRecipientEmail(), e.getMessage(), e);
        }
    }
}