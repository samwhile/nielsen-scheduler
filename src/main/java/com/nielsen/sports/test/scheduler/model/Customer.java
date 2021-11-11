package com.nielsen.sports.test.scheduler.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {

    @Id
    @GeneratedValue
    long id;

    String name;

    String phoneNumber;

    String address1;

    String address2;

    String state;

    String city;

    String zipCode;

    String vehicleLicensePlate;

    int vehicleModelYear;

    String vehicleMakeModel;

}
