package com.nielsen.sports.test.scheduler.repository;

import com.nielsen.sports.test.scheduler.model.Appointment;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    Appointment findById(long id);

    List<Appointment> findAllByDateTimeBetween(
            LocalDateTime dateTimeStart,
            LocalDateTime dateTimeEnd
    );

    List<Appointment> findAllByDateTimeBetween(
            LocalDateTime dateTimeStart,
            LocalDateTime dateTimeEnd,
            Sort sort
    );


}
