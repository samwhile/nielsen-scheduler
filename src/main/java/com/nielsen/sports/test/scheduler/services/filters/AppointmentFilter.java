package com.nielsen.sports.test.scheduler.services.filters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppointmentFilter implements Filter {

    private String startDate;

    private String endDate;

    private String orderBy;

    private String sortBy;
}
