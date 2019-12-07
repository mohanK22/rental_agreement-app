package in.aartimkarande.rentalagreement.myrentalagreement.model;

public class UserInfo {

    private String id;
    private String userFullName;
    private String userEmailID;
    private String userMobileNo;
    private String userProfession;
    private String userDOB;
    private String userType;
    private String userUsername;

    public UserInfo() {
    }

    public UserInfo(String id, String userFullName, String userUsername) {
        this.id = id;
        this.userFullName = userFullName;
        this.userUsername = userUsername;
    }

    public UserInfo(String userFullName, String userEmailID, String userMobileNo, String userProfession, String userDOB, String userType, String userUsername) {
        this.userFullName = userFullName;
        this.userEmailID = userEmailID;
        this.userMobileNo = userMobileNo;
        this.userProfession = userProfession;
        this.userDOB = userDOB;
        this.userType = userType;
        this.userUsername = userUsername;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmailID() {
        return userEmailID;
    }

    public void setUserEmailID(String userEmailID) {
        this.userEmailID = userEmailID;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getUserProfession() {
        return userProfession;
    }

    public void setUserProfession(String userProfession) {
        this.userProfession = userProfession;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(String userDOB) {
        this.userDOB = userDOB;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

}
