package com.servicio.reservas.notificacion.application.dto;

import com.servicio.reservas.notificacion.domain.entities.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent implements Serializable {

    private String recipientEmail;
    private NotificationType notificationType;
    private Map<String, Object> templateModel; // Data for the email template
    private LocalDateTime timestamp;
}