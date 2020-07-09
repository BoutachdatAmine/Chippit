package be.ehb.project.chippit.entity;

public class Company {
    private int companyId;
    private String companyName;
    private String logoPath;
    private String owner;
    private Address address;

    public Company(int companyId, String companyName, String logoPath, String owner, Address address) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.logoPath = logoPath;
        this.owner = owner;
        this.address = address;
    }

    public Company() {
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", logoPath='" + logoPath + '\'' +
                ", owner='" + owner + '\'' +
                ", address=" + address +
                '}' + "\n";
    }
}
