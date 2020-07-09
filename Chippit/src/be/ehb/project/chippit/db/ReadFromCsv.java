package be.ehb.project.chippit.db;

import be.ehb.project.chippit.entity.Address;
import be.ehb.project.chippit.entity.Company;
import be.ehb.project.chippit.entity.Person;

import java.io.*;
import java.sql.*;


public class ReadFromCsv {
    String jdbcURL = "jdbc:mysql://dt5.ehb.be/1920PROGPROJ_GR6";
    String username = "1920PROGPROJ_GR6";
    String password = "YWfgAwH";

    //User
    public void importUsersFromCSV(String path) {

        int batchSize = 20;

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO USERS VALUES (NULL, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement s = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(path));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] strings = lineText.split(",");

                int company_id = Integer.parseInt(strings[0]);
                String firstname = strings[1];
                String lastname = strings[2];
                String Phone = strings[3];
                String Gender = strings[4];
                String birthdate = strings[5];
                String country = strings[6];
                String city = strings[7];
                String address1 = strings[8];
                String address2 = strings[9];
                String postcode = strings[10];
                String email = strings[11];
                String password = strings[12];
                int permission = Integer.parseInt(strings[13]);
                String tfa = strings[14];





                Address a = new Address(country, city, postcode, address1, address2);
                Person p = new Person(firstname, lastname, Phone, Gender, email, birthdate, permission, password, company_id, a,tfa);
                if (p.checkEmailForUser(email) == false) {
                    System.out.println(p.getEmail() + " Is already used");
                }
                if (p.getAddress().checkPostalCode(p.getAddress().getPostalCode()) == false) {
                    System.out.println("Not valid Postal Code");
                }
                if (p.checkEmailForUser(p.getEmail()) && p.getAddress().checkPostalCode(p.getAddress().getPostalCode())) {

                    CompanyDAO companyDAO = new CompanyDAO();
                    Company company = companyDAO.getCompany();

                    s.setInt(1, company.getCompanyId());
                    s.setString(2, firstname);
                    s.setString(3, lastname);
                    s.setString(4, Phone);
                    s.setString(5, Gender);
                    s.setString(6, birthdate);
                    s.setString(7, country);
                    s.setString(8, city);
                    s.setString(9, address1);
                    s.setString(10, address2);
                    s.setString(11, postcode);
                    s.setString(12, email);
                    s.setString(13, password);
                    s.setInt(14, permission);
                    s.setString(15, tfa);

                    s.addBatch();

                    if (count % batchSize == 0) {
                        s.executeBatch();
                    }
                }
            }

            lineReader.close();

            // execute the remaining queries
            s.executeBatch();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Product
    public void importProductsFromCSV(String path) {

        int batchSize = 20;

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "insert into PRODUCT values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement s = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(path));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] strings = lineText.split(",");

                int productid = Integer.parseInt(strings[0]);
                int companyid = Integer.parseInt(strings[1]);
                String categoryname = strings[2];
                String productname = strings[3];
                double price = Double.parseDouble(strings[4]);
                int quantity = Integer.parseInt(strings[5]);
                String manufacturer = strings[6];
                String keyword1 = strings[7];
                String keyword2 = strings[8];
                String keyword3 = strings[9];
                String image1 = strings[10];
                String image2 = strings[11];
                String image3 = strings[12];
                String description = strings[13];
                boolean active = Boolean.parseBoolean(strings[14]);





                s.setInt(1, productid);
                s.setInt(2, companyid);
                s.setString(3, categoryname);
                s.setString(4, productname);
                s.setDouble(5,price);
                s.setInt(6, quantity);
                s.setString(7,manufacturer);
                s.setString(8,keyword1);
                s.setString(9,keyword2);
                s.setString(10,keyword3);
                s.setString(11,image1);
                s.setString(12,image2);
                s.setString(13,image3);
                s.setString(14,description);
                s.setBoolean(15,active);






                s.addBatch();

                if (count % batchSize == 0) {
                    s.executeBatch();
                }
            }

            lineReader.close();

            // execute the remaining queries
            s.executeBatch();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}