package in.aartimkarande.rentalagreement.myrentalagreement.model;

public class FormStatus {

    private String own_stat= null;
    private String tent_stat= null;
    private String wit_stat= null;
    private String prop_pay_stat= null;
    private String rent_trans_stat= null;

    public FormStatus(String own_stat, String tent_stat, String wit_stat, String prop_pay_stat, String rent_trans_stat) {
        this.own_stat = own_stat;
        this.tent_stat = tent_stat;
        this.wit_stat = wit_stat;
        this.prop_pay_stat = prop_pay_stat;
        this.rent_trans_stat = rent_trans_stat;
    }

    public String getOwn_stat() {
        return own_stat;
    }

    public void setOwn_stat(String own_stat) {
        this.own_stat = own_stat;
    }

    public String getTent_stat() {
        return tent_stat;
    }

    public void setTent_stat(String tent_stat) {
        this.tent_stat = tent_stat;
    }

    public String getWit_stat() {
        return wit_stat;
    }

    public void setWit_stat(String wit_stat) {
        this.wit_stat = wit_stat;
    }

    public String getProp_pay_stat() {
        return prop_pay_stat;
    }

    public void setProp_pay_stat(String prop_pay_stat) {
        this.prop_pay_stat = prop_pay_stat;
    }

    public String getRent_trans_stat() {
        return rent_trans_stat;
    }

    public void setRent_trans_stat(String rent_trans_stat) {
        this.rent_trans_stat = rent_trans_stat;
    }
}
