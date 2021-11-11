package com.nielsen.sports.test.scheduler.services.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.common.value.qual.MinLen;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
public class AppointmentFilter implements Filter {

    private String startDate;

    private String endDate;

    private Sort.Direction orderBy;

    // Default sort: Price
    private SortBy sortBy = SortBy.TOTAL_PRICE;

    public enum SortBy {
        TOTAL_PRICE("totalPrice"), DATE("dateTime");

        private final String sortField;

        SortBy(String sortField) {
            this.sortField = sortField;
        }

        public String getSortField() {
            return this.sortField;
        }

    }
}
