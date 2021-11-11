package com.nielsen.sports.test.scheduler.model;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Appointment {

    @Id
    @GeneratedValue
    private long id;

    private AppointmentStatus status;

    @Column(name = "date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTime;

    @OneToOne
    private Customer customer;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vehicle_services", joinColumns = @JoinColumn(name = "appointment_id"))
    private List<VehicleService> vehicleServices;

    /**
     * This query basically sums up the vehicle services associated with this appointment.
     */
    @Formula("(SELECT SUM(v.price)" +
            " FROM appointment a" +
            " INNER JOIN vehicle_services v" +
            " ON a.id = v.appointment_id" +
            " WHERE v.appointment_id = id)")
    private double totalPrice;

}
