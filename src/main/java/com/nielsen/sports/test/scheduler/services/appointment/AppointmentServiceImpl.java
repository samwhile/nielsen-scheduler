package com.nielsen.sports.test.scheduler.services.appointment;

import com.google.common.collect.Lists;
import com.nielsen.sports.test.scheduler.model.Appointment;
import com.nielsen.sports.test.scheduler.repository.AppointmentRepository;
import com.nielsen.sports.test.scheduler.services.filters.AppointmentFilter;
import com.nielsen.sports.test.scheduler.services.filters.Filter;
import com.nielsen.sports.test.scheduler.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public void deleteAppointment(long appointmentId) {
        log.info("Deleting appointment {} ", appointmentId);
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public long createAppointment(Appointment appointment) {
        log.info("Saving appointment {} ", appointment); // In a production setting I might not log this if sensitive data
        return appointmentRepository.save(appointment).getId();
    }

    @Override
    public void updateAppointmentStatus(Appointment appointment) {
        // Maybe this could use a different model specifically for updating status
        // but using current model this request body is still perfectly acceptable
        // {
        //    "id": "51",
        //    "status": "CANCELLED"
        //}
        log.info("Updating appointment status {} = {}", appointment.getId(), appointment.getStatus());

        Appointment appointmentToUpdate = appointmentRepository.findById(appointment.getId());
        appointmentToUpdate.setStatus(appointment.getStatus());

        appointmentRepository.save(appointmentToUpdate);
    }

    @Override
    public Appointment getAppointment(long appointmentId) {
        log.info("Returning appointment {} ", appointmentId);
        return appointmentRepository.findById(appointmentId);
    }

    @Override
    public List<Appointment> getAppointmentByFilter(Filter... filter) throws IllegalArgumentException {

        if (filter[0] != null && filter[0] instanceof AppointmentFilter) {

            log.info("Returning all appointments by filters: {} ", Arrays.stream(filter).toArray());

            AppointmentFilter appointmentFilter = (AppointmentFilter) filter[0];

            LocalDateTime startDate = CommonUtils.getLocateDateTimeFrom(appointmentFilter.getStartDate());
            LocalDateTime endDate = CommonUtils.getLocateDateTimeFrom(appointmentFilter.getEndDate());

            // Could have better error handling depending on business need but let's see both of these are required
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("Invalid Start or End date");
            }

            Sort.Direction direction = Optional.ofNullable(appointmentFilter.getOrderBy()).orElse(Sort.Direction.ASC);
            String sortField = appointmentFilter.getSortBy().getSortField();

            return appointmentRepository.findAllByDateTimeBetween(startDate,endDate, Sort.by(direction, sortField));
        }
        return Collections.emptyList();
    }

    @Override
    public List<Appointment> getAllAppointments() {
        log.info("Returning all appointments..");
        return Lists.newArrayList(appointmentRepository.findAll());
    }

    @Override
    public boolean exists(long appointmentId) {
        return appointmentRepository.existsById(appointmentId);
    }
}
