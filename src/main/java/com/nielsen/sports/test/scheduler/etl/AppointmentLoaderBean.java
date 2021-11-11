package com.nielsen.sports.test.scheduler.etl;

import com.nielsen.sports.test.scheduler.model.Appointment;
import com.nielsen.sports.test.scheduler.model.AppointmentStatus;
import com.nielsen.sports.test.scheduler.model.Customer;
import com.nielsen.sports.test.scheduler.model.VehicleService;
import com.nielsen.sports.test.scheduler.repository.AppointmentRepository;
import com.nielsen.sports.test.scheduler.repository.CustomerRepository;
import com.nielsen.sports.test.scheduler.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
@Slf4j
@ConditionalOnProperty("scheduler.etl.enabled")
@RequiredArgsConstructor
public class AppointmentLoaderBean implements AppointmentLoader{

    private final AppointmentRepository appointmentRepository;

    private final CustomerRepository customerRepository;

    @Value("${scheduler.etl.generate.customers:50}")
    private int customerSize;

    @Value("${scheduler.etl.generate.appointments:10}")
    private int appointmentSize;

    @Value("${scheduler.etl.generate.services}")
    private final String[] vehicleServiceNames;

    @Value("${scheduler.etl.generate.vehicles}")
    private final String[] vehicles;

    @PostConstruct
    @Override
    public void load() {
        log.info("Creating some appointments on start up");

        generateCustomers().forEach(customerRepository::save);
        generateAppointments().forEach(appointmentRepository::save);
    }

    /**
     * Helper method to generate random customers
     * @return list of random customers
     */
    private List<Customer> generateCustomers() {

        List<Customer> customerList = new ArrayList<>();

        int carMakerLength = vehicles.length;

        for (int i = 0; i < customerSize; i++) {
            Customer customer = Customer.builder()
                    .name("Customer " + i)
                    .address1("123 Main Street")
                    .city("Nowhere")
                    .state("NY")
                    .zipCode("12345")
                    .vehicleLicensePlate(CommonUtils.getRandomUUID(5).toUpperCase(Locale.ROOT))
                    .vehicleMakeModel(vehicles[(i % carMakerLength)])
                    .vehicleModelYear(CommonUtils.random(1999, 2021))
                    .build();

            log.info("Generating customer {}", customer);
            customerList.add(customer);
        }

        return customerList;
    }

    /**
     * Helper method to generate random appointments
     * @return list of random appointments
     */
    private List<Appointment> generateAppointments() {

        List<Appointment> appointmentList = new ArrayList<>();
        for (int i = 0; i < appointmentSize; i++) {

            LocalDateTime startDateTime = CommonUtils.getLocalDateTimeOffset(CommonUtils.random(7,30));

            List<VehicleService> vehicleServices = new ArrayList<>();


            Set<String> servicesRequested = new HashSet<>();

            for (int j = 0; j < CommonUtils.random(1, 4); j++) {
                int randomService = CommonUtils.random(0, vehicleServiceNames.length - 1);
                String serviceName = vehicleServiceNames[randomService];

                if (!servicesRequested.contains(serviceName)) {
                    VehicleService vehicleService = VehicleService.builder()
                            .serviceName(serviceName)
                            .price(CommonUtils.random(100, 300))
                            .serviceId(CommonUtils.getRandomUUID(4))
                            .build();

                    // Don't allow duplicate services to be requsted
                    servicesRequested.add(serviceName);

                    vehicleServices.add(vehicleService);
                }
            }

            String findRandomCustomer = "Customer " + CommonUtils.random(0, customerSize);
            Appointment appointment = Appointment.builder()
                    .dateTime(startDateTime)
                    .status(AppointmentStatus.SCHEDULED)
                    .customer(customerRepository.findByName(findRandomCustomer))
                    .vehicleServices(vehicleServices)
                    .build();


            appointmentList.add(appointment);
            log.info("Generating appointment {}", appointment);
        }

        return appointmentList;
    }

}
