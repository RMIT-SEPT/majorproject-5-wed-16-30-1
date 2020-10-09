package sept.wed16301.backend;

public class Booking {

    private String serviceID;
    private String workerName;
    private String serviceName;
    private String serviceDate;

    public Booking(String serviceID, String workerName, String serviceName, String serviceDate) {
        this.serviceID = serviceID;
        this.workerName = workerName;
        this.serviceName = serviceName;
        this.serviceDate = serviceDate;
    }

    public String getServiceID() {
        return serviceID;
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
