package com.nielsen.sports.test.scheduler.repository;

import com.nielsen.sports.test.scheduler.model.Appointment;
import com.nielsen.sports.test.scheduler.model.AppointmentStatus;
import com.nielsen.sports.test.scheduler.model.VehicleService;
import com.nielsen.sports.test.scheduler.util.CommonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private long lastCreatedAppointmentId = 0L;

    private final int secondAppointmentOffset = 30;

    @BeforeEach
    void init() {
        Appointment appointment = Appointment.builder()
                .status(AppointmentStatus.SCHEDULED)
                .dateTime(LocalDateTime.now())
                .vehicleServices(Collections.singletonList(VehicleService.builder()
                        .price(100)
                        .build()))
                .totalPrice(100)
                .build();

        lastCreatedAppointmentId = appointmentRepository.save(appointment).getId();

        Appointment appointment2 = Appointment.builder()
                .status(AppointmentStatus.SCHEDULED)
                .dateTime(CommonUtils.getLocalDateTimeOffset(secondAppointmentOffset))
                .vehicleServices(Collections.singletonList(VehicleService.builder()
                        .price(50)
                        .build()))
                .totalPrice(50)
                .build();

        lastCreatedAppointmentId = appointmentRepository.save(appointment2).getId();
    }

    @Test
    void test_findById() {

        Appointment appointment = appointmentRepository.findById(lastCreatedAppointmentId);

        Assertions.assertNotNull(appointment);

        Assertions.assertEquals(lastCreatedAppointmentId, appointment.getId());
    }

    @Test
    void test_findAllByDateTimeBetween() {

        LocalDateTime start = CommonUtils.getLocalDateTimeOffset(-1);
        LocalDateTime end = CommonUtils.getLocalDateTimeOffset(1);

        List<Appointment> appointments = appointmentRepository.findAllByDateTimeBetween(start, end);

        // We should only expect 1 appointment because our 2nd one is scheduled a month later
        Assertions.assertEquals(1, appointments.size());

    }

    @Test
    void test_findAllByDateTimeBetweenSortedAsc() {

        LocalDateTime start = CommonUtils.getLocalDateTimeOffset(-1);
        LocalDateTime end = CommonUtils.getLocalDateTimeOffset(secondAppointmentOffset + 1);

        List<Appointment> appointments = appointmentRepository.findAllByDateTimeBetween(
                start,
                end,
                Sort.by(Sort.Direction.ASC, "totalPrice"));

        Assertions.assertEquals(2, appointments.size());

        Assertions.assertTrue(appointments.get(0).getTotalPrice() < appointments.get(1).getTotalPrice());
    }
}
