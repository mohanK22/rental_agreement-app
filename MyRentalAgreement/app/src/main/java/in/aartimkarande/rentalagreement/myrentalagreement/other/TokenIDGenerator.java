package in.aartimkarande.rentalagreement.myrentalagreement.other;

import java.util.Calendar;
import java.util.Date;

public class TokenIDGenerator {

    private String tokenID;
    private Calendar calendar;

    public TokenIDGenerator() {
        this.calendar = Calendar.getInstance();
        generateTokenID();
    }

    private void generateTokenID() {
        tokenID = calendar.getTimeInMillis() + "";
    }

    public String getTokenID(){
        return this.tokenID;
    }
}
