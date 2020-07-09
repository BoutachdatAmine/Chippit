package be.ehb.project.chippit.entity;

public class Address {
    private String country;
    private String city;
    private String postalCode;
    private String address1;
    private String address2;

    public Address(String country, String city, String postalCode, String address1, String address2) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.address1 = address1;
        this.address2 = address2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }


    //Check a Belgian postal code
    public boolean checkPostalCode(String postalCode){
        int toPos = Math.abs(Integer.parseInt(postalCode));
        postalCode = String.valueOf(toPos);
        if(postalCode.length()==4){
            return true;
        }
        else return false;

    }

    @Override
    public String toString() {
        return "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", Address1'" + address1 + '\'' +
                ", Address2'" + address2 + "\n";
    }
}
