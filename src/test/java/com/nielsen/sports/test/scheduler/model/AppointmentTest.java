package com.nielsen.sports.test.scheduler.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

public class AppointmentTest {

    private Appointment appointment;

    private final LocalDateTime expectedNowDateTime = LocalDateTime.now();

    private final String expectedCustomerName = "Dave";

    private final AppointmentStatus expectedAppointmentStatus = AppointmentStatus.SCHEDULED;

    private final String expectedService = "Oil Change";

    @BeforeEach
    void init() {
        appointment = Appointment.builder()
                .dateTime(expectedNowDateTime)
                .status(expectedAppointmentStatus)
                .customer(Customer.builder()
                        .name(expectedCustomerName)
                        .build())
                .vehicleServices(Collections.singletonList(VehicleService.builder()
                        .serviceName(expectedService)
                        .build()))
                .build();
    }

    @Test
    void test_appointmentBuilder_getDateTime() {
        Assertions.assertNotNull(appointment.getDateTime());

        Assertions.assertEquals(expectedNowDateTime, appointment.getDateTime());
    }

    @Test
    void test_appointmentBuilder_getStatus() {
        Assertions.assertEquals(expectedAppointmentStatus, appointment.getStatus());
    }

    @Test
    void test_appointmentBuilder_getCustomerName() {
        Assertions.assertEquals(expectedCustomerName, appointment.getCustomer().getName());
    }

    @Test
    void test_appointmentBuilder_getLicensePlate() {
        Assertions.assertEquals(expectedService, appointment.getVehicleServices().get(0).getServiceName());
    }
}
