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
        log.info("Received PAYMENT_REJECTED event from RabbitMQ: {}", event);
        notificationService.processNotificationEvent(event);
    }
}