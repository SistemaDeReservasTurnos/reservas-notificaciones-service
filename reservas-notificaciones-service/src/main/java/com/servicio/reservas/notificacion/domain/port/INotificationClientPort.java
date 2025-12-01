package com.servicio.reservas.notificacion.domain.port;

import com.servicio.reservas.notificacion.domain.entities.NotificationType;
import com.servicio.reservas.notificacion.infraestructure.exception.EmailSendingException;

import java.util.Map;

public interface INotificationClientPort {
    void sendNotification(String recipientEmail, NotificationType type, Map<String, Object> templateModel) throws EmailSendingException;

}