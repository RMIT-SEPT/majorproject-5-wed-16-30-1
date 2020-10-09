package sept.wed16301.backend.booking;

public class BookingRequest {

    private String serviceID;
    private String customerUsername;
    private String workerName;
    private String serviceName;
    private String serviceDate;

    public BookingRequest(String serviceID, String customerUsername, String workerName, String serviceName, String serviceDate) {
        this.serviceID = serviceID;
        this.customerUsername = customerUsername;
        this.workerName = workerName;
        this.serviceName = serviceName;
        this.serviceDate = serviceDate;
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

    public String getServiceDate() {
        return serviceDate;
    }

}
