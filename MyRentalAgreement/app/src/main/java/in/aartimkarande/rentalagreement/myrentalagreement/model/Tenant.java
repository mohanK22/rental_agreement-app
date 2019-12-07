package in.aartimkarande.rentalagreement.myrentalagreement.model;

public class Tenant extends User {

    public Tenant(String id, String propID, String fName, String mName, String lName, String photo, String age, String gender, String mobNo, String prof, String panNo, String panNoUpd, String aadharNo, String adharNoFrontUpd, String adharNoBackUpd, String bldg, String plot, String floorNo, String road, String area, String suburban, String city, String taluka, String state, String pcode, String noCo, String stat) {
        super(id, propID, fName, mName, lName, photo, age, gender, mobNo, prof, panNo, panNoUpd, aadharNo, adharNoFrontUpd, adharNoBackUpd, bldg, plot, floorNo, road, area, suburban, city, taluka, state, pcode, noCo, stat);
    }
}
