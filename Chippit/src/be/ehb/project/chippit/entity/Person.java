package be.ehb.project.chippit.entity;

import be.ehb.project.chippit.db.PersonDAO;

public class Person {
    private int personID;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    private String email;
    private String birthdate;
    private int permission;
    private String password;
    private int companyID;
    private Address address;
    private boolean marketing;
    private String tfa;

    // Constructor for CUSTOMERS
    public Person(int personID, String firstName, String lastName, String phone, String gender, String email, String birthdate, int permission, String password, int companyID, Address address, boolean marketing) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.birthdate = birthdate;
        this.permission = permission;
        this.password = password;
        this.companyID = companyID;
        this.address = address;
        this.marketing = marketing;
    }

    // Constructor for USERS
    public Person(int personID, String firstName, String lastName, String phone, String gender, String email, String birthdate, int permission, String password, int companyID, Address address) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.birthdate = birthdate;
        this.permission = permission;
        this.password = password;
        this.companyID = companyID;
        this.address = address;
    }


    // Constructor for USERS
    public Person(String firstName, String lastName, String phone, String gender, String email, String birthdate, int permission, String password, int companyID, Address address, String tfa) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.birthdate = birthdate;
        this.permission = permission;
        this.password = password;
        this.companyID = companyID;
        this.address = address;
        this.tfa = tfa;
    }

    // With TFA
    public Person(int personID, String firstName, String lastName, String phone, String gender, String email, String birthdate, int permission, String password, int companyID, Address address, String tfa) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.birthdate = birthdate;
        this.permission = permission;
        this.password = password;
        this.companyID = companyID;
        this.address = address;
        this.tfa = tfa;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public boolean checkEmailForUser(String email)
    {
        PersonDAO personDAO = new PersonDAO();
        if(personDAO.getEmails("USERS").contains(email))
            return false;
        else
            return true;

    }

    public boolean checkEmailForCustomer(String email)
    {
        PersonDAO personDAO = new PersonDAO();
        if(personDAO.getEmails("CUSTOMER").contains(email))
            return false;
        else
            return true;

    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isMarketing() {
        return marketing;
    }

    public void setMarketing(boolean marketing) {
        this.marketing = marketing;
    }

    public String getTfa() {
        return tfa;
    }


    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", permission=" + permission +
                ", password='" + password + '\'' +
                ", companyID=" + companyID +
                ", address=" + address +
                ", marketing=" + marketing +
                ", tfa='" + tfa + '\'' +
                '}';
    }
}