package com.servicio.reservas.notificacion.infraestructure.template;

import com.servicio.reservas.notificacion.domain.entities.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateService {

    private final TemplateEngine templateEngine;

    // Define mappings for templates and subjects based on NotificationType
    private static final Map<NotificationType, String> TEMPLATE_MAPPING = Map.of(
            NotificationType.RESERVATION_CONFIRMED, "reservation_confirmed",
            NotificationType.PAYMENT_REJECTED, "payment_rejected"
    );

    private static final Map<NotificationType, String> SUBJECT_MAPPING = Map.of(
            NotificationType.RESERVATION_CONFIRMED, "Your Reservation is Confirmed!",
            NotificationType.PAYMENT_REJECTED, "Payment for Your Reservation Was Rejected"
    );

    public String getHtmlContent(NotificationType type, Map<String, Object> model) {
        String templateName = TEMPLATE_MAPPING.get(type);
        if (templateName == null) {
            log.error("No template found for notification type: {}", type);
            throw new IllegalArgumentException("No template defined for " + type);
        }

        Context context = new Context(Locale.ENGLISH, model); // Use English locale
        return templateEngine.process(templateName, context);
    }

    public String getSubjectForType(NotificationType type) {
        String subject = SUBJECT_MAPPING.get(type);
        if (subject == null) {
            log.error("No subject found for notification type: {}", type);
            throw new IllegalArgumentException("No subject defined for " + type);
        }
        return subject;
    }
}