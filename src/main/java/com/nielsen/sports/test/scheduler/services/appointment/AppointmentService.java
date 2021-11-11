package com.nielsen.sports.test.scheduler.services.appointment;

import com.nielsen.sports.test.scheduler.model.Appointment;
import com.nielsen.sports.test.scheduler.services.filters.Filter;

import java.util.List;

public interface AppointmentService {

    void deleteAppointment(long appointmentId);

    long createAppointment(Appointment appointment);

    void updateAppointmentStatus(Appointment appointment);

    Appointment getAppointment(long appointmentId);

    List<Appointment> getAppointmentByFilter(Filter...filter) throws IllegalArgumentException;

    List<Appointment> getAllAppointments();

    boolean exists(long appointmentId);
}
