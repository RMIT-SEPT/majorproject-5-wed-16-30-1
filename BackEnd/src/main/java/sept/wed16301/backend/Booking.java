package sept.wed16301.backend;

import java.time.LocalDateTime;

public class Booking {

    private String serviceID;
    private String customerUsername;
    private String workerName;
    private String serviceName;
    private LocalDateTime serviceDate;
    private int duration;

    public Booking(String serviceID, String customerUsername, String workerName, String serviceName, LocalDateTime serviceDate, int duration) {
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

    public int getDuration() { return duration; }

}
