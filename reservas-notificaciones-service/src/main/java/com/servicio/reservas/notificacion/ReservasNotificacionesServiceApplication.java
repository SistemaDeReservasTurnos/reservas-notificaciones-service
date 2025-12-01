package com.servicio.reservas.notificacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.amqp.rabbit.annotation.EnableRabbit; // Añadido para SRT-81

@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit // Enable RabbitMQ listener management
public class ReservasNotificacionesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservasNotificacionesServiceApplication.class, args);
    }

}