package in.aartimkarande.rentalagreement.myrentalagreement.model;

public class AgreementLists {
    private String agreementID;

    private String agmID;
    private String userID;
    private String userName;
    private String username;

    public AgreementLists() {
    }

    public AgreementLists(String agmID, String userID, String userName, String username) {
        this.agmID = agmID;
        this.userID = userID;
        this.userName = userName;
        this.username = username;
    }

    public AgreementLists(String agreementID) {
        this.agreementID = agreementID;
    }

    public String getAgreementID() {
        return agreementID;
    }

    public String getAgmID() {
        return agmID;
    }

    public void setAgreementID(String agreementID) {
        this.agreementID = agreementID;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUsername() {
        return username;
    }
}
