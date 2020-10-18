package sept.wed16301.backend.booking;

import java.time.LocalDateTime;

public class BookingRequest {

    private String serviceID;
    private String customerUsername;
    private String workerName;
    private String serviceName;
    private LocalDateTime serviceDate;
    private int duration;

    public BookingRequest(String serviceID, String customerUsername, String workerName, String serviceName, LocalDateTime serviceDate, int duration) {
        this.serviceID = serviceID;
        this.customerUsername = customerUsername;
        this.workerName = workerName;
        this.serviceName = serviceName;
        this.serviceDate = serviceDate;
        this.duration = duration;
    }

    public String getServiceID() {
        return serviceID;
    }

    public int getDuration() { return duration; }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public LocalDateTime getServiceDate() {
        return serviceDate;
    }

}
