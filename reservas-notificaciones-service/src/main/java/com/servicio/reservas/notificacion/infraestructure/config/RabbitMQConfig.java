package com.servicio.reservas.notificacion.infraestructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Queue and Exchange Names (Centralized definition)
    public static final String QUEUE_RESERVATION_CONFIRMED = "q.reservation.confirmed.notification";
    public static final String QUEUE_PAYMENT_REJECTED = "q.payment.rejected.notification";
    public static final String EXCHANGE_NOTIFICATION = "x.notification";
    public static final String ROUTING_KEY_RESERVATION_CONFIRMED = "rk.reservation.confirmed";
    public static final String ROUTING_KEY_PAYMENT_REJECTED = "rk.payment.rejected";

    // Define Queues
    @Bean
    public Queue reservationConfirmedQueue() {
        return new Queue(QUEUE_RESERVATION_CONFIRMED, true);
    }

    @Bean
    public Queue paymentRejectedQueue() {
        return new Queue(QUEUE_PAYMENT_REJECTED, true);
    }

    // Define Exchange
    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(EXCHANGE_NOTIFICATION);
    }

    // Bindings
    @Bean
    public Binding bindReservationConfirmedQueue(
            Queue reservationConfirmedQueue,
            TopicExchange notificationExchange) {
        return BindingBuilder.bind(reservationConfirmedQueue)
                .to(notificationExchange)
                .with(ROUTING_KEY_RESERVATION_CONFIRMED);
    }

    @Bean
    public Binding bindPaymentRejectedQueue(
            Queue paymentRejectedQueue,
            TopicExchange notificationExchange) {
        return BindingBuilder.bind(paymentRejectedQueue)
                .to(notificationExchange)
                .with(ROUTING_KEY_PAYMENT_REJECTED);
    }

    // JSON Message Converter (deprecated but required in Spring Boot 4)
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate with converter
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
