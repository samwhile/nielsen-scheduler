package com.nielsen.sports.test.scheduler.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleService {

    double price;

    String serviceId;

    String serviceName;
}
