package com.nielsen.sports.test.scheduler.services.appointment;

import com.nielsen.sports.test.scheduler.model.Appointment;
import com.nielsen.sports.test.scheduler.services.filters.AppointmentFilter;
import com.nielsen.sports.test.scheduler.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping(value = "/appointment")
    @ResponseStatus(HttpStatus.CREATED)
    long createAppointment(@RequestBody Appointment appointment) {
        if (appointmentService.exists(appointment.getId())) {
            error_409();
        }
        return appointmentService.createAppointment(appointment);
    }

    @PutMapping(value = "/appointment")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void updateAppointmentStatus(@RequestBody Appointment appointment) {
        if (!appointmentService.exists(appointment.getId())) {
            error_404();
        }
        appointmentService.updateAppointmentStatus(appointment);
    }

    @GetMapping(
            value = "/appointment/{appointmentId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Appointment getAppointment(@PathVariable long appointmentId) {
        if (!appointmentService.exists(appointmentId)) {
            error_404();
        }
        return appointmentService.getAppointment(appointmentId);
    }

    @GetMapping(
            value = "/appointments",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Appointment> getAppointmentsByFilter(AppointmentFilter appointmentFilter) {
        try {
            return appointmentService.getAppointmentByFilter(appointmentFilter);
        } catch (IllegalArgumentException e) {
            error_403();
            return null;
        }
    }


    @DeleteMapping(value = "/appointment/{appointmentId}")
    void deleteAppointment(@PathVariable long appointmentId) {
        if (!appointmentService.exists(appointmentId)) {
            error_404();
        }
        appointmentService.deleteAppointment(appointmentId);
    }

    private void error_403() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.APPOINTMENT_NOT_FOUND);
    }

    private void error_404() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.APPOINTMENT_NOT_FOUND);
    }

    private void error_409() {
        throw new ResponseStatusException(HttpStatus.CONFLICT, Constants.APPOINTMENT_DUPLICATE);
    }

    // TODO: For testing purposes.
    @GetMapping(value = "/test/appointments")
    List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
}
