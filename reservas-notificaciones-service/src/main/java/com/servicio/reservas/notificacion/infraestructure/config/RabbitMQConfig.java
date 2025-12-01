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
    public static final String EXCHANGE_NOTIFICATION = "x.notification"; // Topic Exchange
    public static final String ROUTING_KEY_RESERVATION_CONFIRMED = "rk.reservation.confirmed";
    public static final String ROUTING_KEY_PAYMENT_REJECTED = "rk.payment.rejected";

    // Define Queues
    @Bean
    public Queue reservationConfirmedQueue() {
        return new Queue(QUEUE_RESERVATION_CONFIRMED, true); // Durable queue
    }

    @Bean
    public Queue paymentRejectedQueue() {
        return new Queue(QUEUE_PAYMENT_REJECTED, true); // Durable queue
    }

    // Define Exchange
    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(EXCHANGE_NOTIFICATION);
    }

    // Define Bindings (Connecting queues to exchange)
    @Bean
    public Binding bindReservationConfirmedQueue(Queue reservationConfirmedQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(reservationConfirmedQueue)
                .to(notificationExchange)
                .with(ROUTING_KEY_RESERVATION_CONFIRMED);
    }

    @Bean
    public Binding bindPaymentRejectedQueue(Queue paymentRejectedQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(paymentRejectedQueue)
                .to(notificationExchange)
                .with(ROUTING_KEY_PAYMENT_REJECTED);
    }

    // Configure Message Converter (to send and receive JSON objects)
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Configure RabbitTemplate (required to send messages, though primarily a listener service)
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}