package com.servicio.reservas.notificacion.infraestructure.config;
import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SendGridClientConfig {

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Bean
    public SendGrid sendGrid() {
        // This bean is used by the SendGridAdapter to make API calls
        return new SendGrid(sendGridApiKey);
    }
}