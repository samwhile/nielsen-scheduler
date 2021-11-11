package com.nielsen.sports.test.scheduler.services.appointment;

import com.nielsen.sports.test.scheduler.model.Appointment;
import com.nielsen.sports.test.scheduler.model.AppointmentStatus;
import com.nielsen.sports.test.scheduler.model.VehicleService;
import com.nielsen.sports.test.scheduler.services.filters.AppointmentFilter;
import com.nielsen.sports.test.scheduler.util.CommonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class AppointmentControllerTest {

    @Autowired
    private AppointmentController appointmentController;

    private long lastCreatedAppointmentId = 0L;

    private long bogusAppointmentId = 999999L;

    @BeforeEach
    @Test
    void initializeAppointment() {
        Appointment appointment = Appointment.builder()
                .status(AppointmentStatus.SCHEDULED)
                .dateTime(LocalDateTime.now())
                .vehicleServices(Collections.singletonList(VehicleService.builder()
                        .serviceName("Tire Rotation")
                        .price(100)
                        .build()))
                .build();

        lastCreatedAppointmentId = appointmentController.createAppointment(appointment);

        // Also test appointment creation
        Assertions.assertTrue(lastCreatedAppointmentId != 0L);
    }

    @Test
    void test_appointmentController_notNull() {
        Assertions.assertNotNull(appointmentController);
    }

    @Test
    void test_appointmentController_getAppointment_found() {
        Appointment lastCreatedAppointment = appointmentController.getAppointment(lastCreatedAppointmentId);

        Assertions.assertNotNull(lastCreatedAppointment);
        Assertions.assertEquals(lastCreatedAppointmentId, lastCreatedAppointment.getId());
    }

    @Test
    void test_appointmentController_getAppointment_not_found() {

        ResponseStatusException status = Assertions.assertThrows(
                ResponseStatusException.class, () -> appointmentController.getAppointment(bogusAppointmentId));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, status.getStatus());
    }

    @Test
    void test_appointmentController_deleteAppointment_found() {

        // Found a valid appointment to delete
        Assertions.assertDoesNotThrow(() ->
                appointmentController.deleteAppointment(lastCreatedAppointmentId));

        // Expect a 404 since appointment was deleted
        ResponseStatusException status = Assertions.assertThrows(
                ResponseStatusException.class, () -> appointmentController.getAppointment(lastCreatedAppointmentId));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, status.getStatus());
    }


    @Test
    void test_appointmentController_deleteAppointment_not_found() {

        ResponseStatusException status = Assertions.assertThrows(
                ResponseStatusException.class, () -> appointmentController.deleteAppointment(bogusAppointmentId));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, status.getStatus());
    }

    @Test
    void test_appointmentController_updateAppointment_found_statusCancelled() {

        // Get last scheduled appointment
        Appointment appointment = appointmentController.getAppointment(lastCreatedAppointmentId);

        // Status is currently 'SCHEDULED'
        Assertions.assertEquals(AppointmentStatus.SCHEDULED, appointment.getStatus());

        // Update the appointment status
        Appointment appointmentUpdate = Appointment.builder()
                .id(lastCreatedAppointmentId)
                .status(AppointmentStatus.CANCELLED)
                .build();

        Assertions.assertDoesNotThrow(() ->
                appointmentController.updateAppointmentStatus(appointmentUpdate));

        // Fetch the appointment again
        Appointment updatedAppointment = appointmentController.getAppointment(lastCreatedAppointmentId);

        // Status is now 'CANCELLED'
        Assertions.assertEquals(AppointmentStatus.CANCELLED, updatedAppointment.getStatus());
    }


    @Test
    void test_appointmentController_updateAppointment_not_found() {
        Appointment appointmentUpdate = Appointment.builder()
                .id(bogusAppointmentId)
                .status(AppointmentStatus.CANCELLED)
                .build();

        ResponseStatusException status = Assertions.assertThrows(
                ResponseStatusException.class, () -> appointmentController.updateAppointmentStatus(appointmentUpdate));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, status.getStatus());
    }

    @Test
    void test_appointmentController_getAppointmentsByFilters_found() {

        String startDateTime = CommonUtils.getLocalDateTimeOffset(-5).toString();
        String endDateTime = CommonUtils.getLocalDateTimeOffset(5).toString();

        AppointmentFilter appointmentFilter = new AppointmentFilter(
                startDateTime,
                endDateTime,
                "ASC",
                "totalPrice");

        List<Appointment> appointmentList = appointmentController.getAppointmentsByFilter(appointmentFilter);

        Assertions.assertTrue(appointmentList.size() > 0);

    }


    @Test
    void test_appointmentController_getAppointmentsByFilters_not_found() {

        // We don't have any test appointments a year from now
        String startDateTime = CommonUtils.getLocalDateTimeOffset(360).toString();
        String endDateTime = CommonUtils.getLocalDateTimeOffset(365).toString();

        AppointmentFilter appointmentFilter = new AppointmentFilter(
                startDateTime,
                endDateTime,
                "ASC",
                "totalPrice");

        List<Appointment> appointmentList = appointmentController.getAppointmentsByFilter(appointmentFilter);

        Assertions.assertEquals(0, appointmentList.size());
    }


    @Test
    void test_appointmentController_getAppointmentsByFilters_invalid_Filters() {

        AppointmentFilter appointmentFilter = new AppointmentFilter(
                "foo",
                "bar",
                null,
                null);

        ResponseStatusException status = Assertions.assertThrows(
                ResponseStatusException.class, () -> appointmentController.getAppointmentsByFilter(appointmentFilter));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, status.getStatus());


    }
}
