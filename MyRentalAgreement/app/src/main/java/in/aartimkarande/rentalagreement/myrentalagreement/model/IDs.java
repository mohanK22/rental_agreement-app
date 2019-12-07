package in.aartimkarande.rentalagreement.myrentalagreement.model;

public class IDs {
    private String propertyID;
    private String serviceType;
    private String ownerID;
    private String tenantID;
    private String witnessID;
    private String rentID;
    private String rentTransID;
    private String agreementID;
    private String tokenID;

    public IDs() {
    }

    public IDs(String propertyID, String serviceType, String ownerID, String tenantID, String witnessID, String rentID, String rentTransID, String agreementID, String tokenID) {
        this.propertyID = propertyID;
        this.serviceType = serviceType;
        this.ownerID = ownerID;
        this.tenantID = tenantID;
        this.witnessID = witnessID;
        this.rentID = rentID;
        this.rentTransID = rentTransID;
        this.agreementID = agreementID;
        this.tokenID = tokenID;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getWitnessID() {
        return witnessID;
    }

    public void setWitnessID(String witnessID) {
        this.witnessID = witnessID;
    }

    public String getRentID() {
        return rentID;
    }

    public void setRentID(String rentID) {
        this.rentID = rentID;
    }

    public String getRentTransID() {
        return rentTransID;
    }

    public void setRentTransID(String rentTransID) {
        this.rentTransID = rentTransID;
    }

    public String getAgreementID() {
        return agreementID;
    }

    public void setAgreementID(String agreementID) {
        this.agreementID = agreementID;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }
}

