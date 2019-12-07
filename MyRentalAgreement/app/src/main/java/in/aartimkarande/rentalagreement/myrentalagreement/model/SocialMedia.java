package in.aartimkarande.rentalagreement.myrentalagreement.model;

public class SocialMedia {

    private String name;
    private int img;

    public SocialMedia(String name, int img) {
        this.name = name;
        this.img = img;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
