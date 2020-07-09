package be.ehb.project.chippit.db;

import be.ehb.project.chippit.entity.Address;
import be.ehb.project.chippit.entity.Company;
import be.ehb.project.chippit.entity.Person;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class PersonDAO extends BaseDAO{
    public PersonDAO(){}


    //Create a user
    public boolean createUser(Person p) {
        if (p.checkEmailForUser(p.getEmail()) == false) {
            System.out.println(p.getEmail() + "is already used");
            return false;
        }
        if (p.getAddress().checkPostalCode(p.getAddress().getPostalCode()) == false) {
            System.out.println("Not valid Postal Code");
            return false;
        }
        if (p.checkEmailForUser(p.getEmail()) && p.getAddress().checkPostalCode(p.getAddress().getPostalCode())) {
            CompanyDAO companyDAO = new CompanyDAO();
            Company company = companyDAO.getCompany();
            try (Connection c = getConnection()) {
                PreparedStatement s = c.prepareStatement("insert into USERS values (NULL, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                s.setInt(1, company.getCompanyId());
                s.setString(2, p.getFirstName());
                s.setString(3, p.getLastName());
                s.setString(4, p.getPhone());
                s.setString(5, p.getGender());
                s.setString(6, p.getBirthdate());
                s.setString(7, p.getAddress().getCountry());
                s.setString(8, p.getAddress().getCity());
                s.setString(9, p.getAddress().getAddress1());
                s.setString(10, p.getAddress().getAddress2());
                s.setString(11, p.getAddress().getPostalCode());
                s.setString(12, p.getEmail());
                s.setString(13, p.getPassword());
                s.setInt(14, p.getPermission());
                s.setString(15, p.getTfa());
                int result = s.executeUpdate();
                if (result > 0)
                    return true;
                else
                    return false;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return false;
    }

    //Create a customer
    public boolean createCustomer(Person p){
        CompanyDAO companyDAO = new CompanyDAO();
        Company company = companyDAO.getCompany();

        try(Connection c = getConnection()){
            PreparedStatement s = c.prepareStatement("insert into CUSTOMER values (null, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            s.setInt(1, company.getCompanyId());
            s.setString(2, p.getFirstName());
            s.setString(3, p.getLastName());
            s.setString(4, p.getPhone());
            s.setString(5, p.getGender());
            s.setString(6, p.getBirthdate());
            s.setString(7, p.getAddress().getCity());
            s.setString(8, p.getAddress().getCity());
            s.setString(9, p.getAddress().getAddress1());
            s.setString(10, p.getAddress().getAddress2());
            s.setString(11, p.getAddress().getPostalCode());
            s.setString(12, p.getEmail());
            s.setString(13, p.getPassword());
            s.setInt(14, p.getPermission());
            s.setBoolean(15, p.isMarketing());
            int result = s.executeUpdate();
            if (result> 0)
                return true;
            else
                return false;
        } catch (SQLException throwables) {
        throwables.printStackTrace();
        }

        return false;
    }


    //Get a list of all Users/ Customers by giving a table name
    public ArrayList<Person> getListOfPersons(String table){
        table = table.toUpperCase();
        ArrayList<Person> allPersons = new ArrayList<>();
        try(Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("select * from " + table);
            ResultSet results = s.executeQuery();
            while(results.next())
            {
                if(table.equals("USERS")) {
                    Person p = getUserResults(results);
                    if (p != null)
                        allPersons.add(p);
                }
                if(table.equals("CUSTOMER"))
                {
                    Person p = getCustomerResults(results);
                    if (p != null)
                        allPersons.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPersons;
    }

    //Get all data about a user
    private Person getUserResults(ResultSet results) {
        Person p = null;
        try {
            p = new Person(
                    results.getInt(1),
                    results.getString(3),
                    results.getString(4),
                    results.getString(5),
                    results.getString(6),
                    results.getString(13),
                    results.getString(7),
                    results.getInt(15),
                    results.getString(14),
                    results.getInt(2),
                    new Address(results.getString(8),
                            results.getString(9),
                            results.getString(12),
                            results.getString(10),
                            results.getString(11)),
                    results.getString(16)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    //Get all data about a customer
    private Person getCustomerResults(ResultSet results) {
        Person p = null;
        try {
            p = new Person(
                    results.getInt(1),
                    results.getString(3),
                    results.getString(4),
                    results.getString(5),
                    results.getString(6),
                    results.getString(13),
                    results.getString(7),
                    results.getInt(15),
                    results.getString(14),
                    results.getInt(2),
                    new Address(results.getString(8),
                            results.getString(9),
                            results.getString(12),
                            results.getString(10),
                            results.getString(11)),
                    results.getBoolean(16)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    //Get a user by his last name
    public Person getUserByLastName(String lastname){
        Person p = null;
        try(Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("select * from USERS where last_name = ?");
            s.setString(1,lastname);
            ResultSet results = s.executeQuery();
            if(results.next())
            {
                p = getUserResults(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }


    //Get a user by ID
    public Person getUserByID(int ID){
        Person p = null;
        try(Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("select * from USERS where id = ?");
            s.setInt(1,ID);
            ResultSet results = s.executeQuery();
            if(results.next())
            {
                p = getUserResults(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }


    //Get a user by his email
    public Person getUserByEMail(String email){
        Person p = null;
        try(Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("select * from USERS where email = ?");
            s.setString(1,email);
            ResultSet results = s.executeQuery();
            if(results.next())
            {
                p = getUserResults(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }


    public HashSet<String> getEmails(String table){
        HashSet<String> emails = new HashSet<>();
        table = table.toUpperCase();
        if(table.equals("USERS")) {
            try (Connection c = getConnection()) {
                Statement s = c.createStatement();
                ResultSet results = s.executeQuery("select * from " + table);
                while (results.next()) {
                    String email = getEmailsResults(results);
                    if (email != null)
                        emails.add(email);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(table.equals("CUSTOMER")) {
            try (Connection c = getConnection()) {
                PreparedStatement s = c.prepareStatement("select * from " + table + " where marketing = ?");
                s.setInt(1,1);
                ResultSet results = s.executeQuery();
                while (results.next()) {
                    String email = getEmailsResults(results);
                    if (email != null)
                        emails.add(email);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return emails;
    }

    private  String getEmailsResults(ResultSet results)
    {
        String email = "";
        try {
            email = results.getString("email");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }


    //This will take the permission of the logged in user.
    public int getPermissionByEmail(String email){
        Integer permissionUser = null;
        try(Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("select permission from USERS where email = ?");
            s.setString(1,email);
            ResultSet results = s.executeQuery();
            if(results.next())
            {
                permissionUser = getPermission(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permissionUser;
    }

    private int getPermission(ResultSet results)
    {
        Integer permission = null;
        try {
            permission = results.getInt("permission");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permission;
    }


    //Count number customers
    public int countCustomers()  {

        int rows = 0;
        try (Connection c = getConnection()) {
            Statement s = c.createStatement();
            ResultSet results = s.executeQuery("SELECT COUNT(*) AS countCustomers FROM CUSTOMER");

            while (results.next()) {
                rows =  results.getInt("countCustomers");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    //Count number of men
    public int countMen() {
        ResultSet results = null;
        int rows = 0;
        try (Connection c = getConnection()) {
            Statement s = c.createStatement();
            results = s.executeQuery("SELECT COUNT(*) AS countMen FROM CUSTOMER where gender = 'M' ");
            while (results.next()) {
                rows =  results.getInt("countMen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    //Count number of women
    public int countWomen() {
        ResultSet results = null;
        int rows = 0;
        try (Connection c = getConnection()) {
            Statement s = c.createStatement();
            results = s.executeQuery("SELECT COUNT(*) AS countWomen FROM CUSTOMER where gender = 'F' ");

            while (results.next()) {
                rows =  results.getInt("countWomen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public boolean updateUser(int id, Person p){
        String sql="UPDATE USERS SET first_name = ? ," +
                "last_name = ? ," +
                "phone = ? ," +
                "gender = ? ," +
                "date_of_birth = ? ," +
                "country = ? , " +
                "city = ? , " +
                "address1 = ? ," +
                "address2 = ? , " +
                "postal_code = ? ," +
                "email = ? ," +
                "password = ? ," +
                "permission = ?, " +
                "tfa = ?" +
                " WHERE id = ?";

        try(Connection c= getConnection()) {
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1,p.getFirstName());
            s.setString(2,p.getLastName());
            s.setString(3,p.getPhone());
            s.setString(4,p.getGender());
            s.setString(5,p.getBirthdate());
            s.setString(6,p.getAddress().getCountry());
            s.setString(7,p.getAddress().getCity());
            s.setString(8,p.getAddress().getAddress1());
            s.setString(9,p.getAddress().getAddress2());
            s.setString(10,p.getAddress().getPostalCode());
            s.setString(11,p.getEmail());
            s.setString(12,p.getPassword());
            s.setInt(13,p.getPermission());
            s.setString(14, p.getTfa());
            s.setInt(15, id);
            int result = s.executeUpdate();
            if (result > 0)
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Delete a user by ID
    public boolean deleteUserById(int userId) {
        try (Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("DELETE from USERS where id = ?");
            s.setInt(1, userId);
            int result = s.executeUpdate();
            if (result > 0)
                return true;
            else return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}