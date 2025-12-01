package com.servicio.reservas.notificacion.application.dto;

import com.servicio.reservas.notificacion.domain.entities.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent implements Serializable {
    @NotNull
    @Email
    private String recipientEmail;

    @NotNull
    private NotificationType notificationType;

    @NotNull
    private Map<String, Object> templateModel; // Data for the email template
    private LocalDateTime timestamp;
}