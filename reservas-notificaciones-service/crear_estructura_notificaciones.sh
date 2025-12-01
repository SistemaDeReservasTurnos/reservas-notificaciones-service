#!/bin/bash

echo "Creando estructura del servicio de notificaciones..."

# Directorio base según tu proyecto actual
BASE_DIR="src/main/java/com/servicio/reservas/notificacion"
RESOURCES_DIR="src/main/resources"
TEST_DIR="src/test/java/com/servicio/reservas/notificacion"

# --- MAIN JAVA ---
mkdir -p $BASE_DIR/application/dto
mkdir -p $BASE_DIR/application/service

mkdir -p $BASE_DIR/domain/entities
mkdir -p $BASE_DIR/domain/port

# EXCEPTION en infraestructura (NO en domain)
mkdir -p $BASE_DIR/infraestructure/exception
mkdir -p $BASE_DIR/infraestructure/adapter
mkdir -p $BASE_DIR/infraestructure/config
mkdir -p $BASE_DIR/infraestructure/listener
mkdir -p $BASE_DIR/infraestructure/template

# Clases vacías
touch $BASE_DIR/application/dto/NotificationEvent.java
touch $BASE_DIR/application/service/NotificationService.java

touch $BASE_DIR/domain/entities/NotificationType.java
touch $BASE_DIR/domain/port/INotificationClientPort.java

touch $BASE_DIR/infraestructure/adapter/SendGridAdapter.java
touch $BASE_DIR/infraestructure/config/RabbitMQConfig.java
touch $BASE_DIR/infraestructure/listener/PaymentRejectedListener.java
touch $BASE_DIR/infraestructure/listener/ReservationConfirmedListener.java
touch $BASE_DIR/infraestructure/template/EmailTemplateService.java
touch $BASE_DIR/infraestructure/exception/EmailSendingException.java

# --- RESOURCES ---
mkdir -p $RESOURCES_DIR/templates

touch $RESOURCES_DIR/application.properties
touch $RESOURCES_DIR/application-local.properties
touch $RESOURCES_DIR/application-prod.properties
touch $RESOURCES_DIR/templates/payment_rejected.html
touch $RESOURCES_DIR/templates/reservation_confirmed.html

# --- TESTS ---
mkdir -p $TEST_DIR
touch $TEST_DIR/ReservasNotificacionesServiceApplicationTests.java

echo "Estructura creada correctamente ✔️"
