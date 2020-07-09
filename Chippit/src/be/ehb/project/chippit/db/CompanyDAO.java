package be.ehb.project.chippit.db;

import be.ehb.project.chippit.entity.Address;
import be.ehb.project.chippit.entity.Company;

import java.sql.*;

public class CompanyDAO extends BaseDAO{
    public CompanyDAO(){}


    //Create a company
    public boolean createCompany(Company company){
        try(Connection c = getConnection()){
            PreparedStatement s = c.prepareStatement("insert into COMPANY values(NULL, ?, NULL, ?, ?, ?, ?, ?, ?, ?)");
            s.setString(1, company.getCompanyName());
            s.setString(2, company.getAddress().getCountry());
            s.setString(3, company.getAddress().getCity());
            s.setString(4, company.getAddress().getAddress1());
            s.setString(5, company.getAddress().getAddress2());
            s.setString(6, company.getAddress().getPostalCode());
            s.setString(7, company.getOwner());
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

    //Get results of a company
    private Company getCompanyResults(ResultSet results){
        Company c = null;
        try {
            c = new Company(
                    results.getInt(1),
                    results.getString(2),
                    results.getString(3),
                    results.getString(9),
                    new Address(
                            results.getString(4),
                            results.getString(5),
                            results.getString(6),
                            results.getString(7),
                            results.getString(8)
                    )
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }


    //Get data of a company
    public Company getCompany(){
        Company company = null;
        try(Connection c = getConnection()){
            Statement s = c.createStatement();
            ResultSet results = s.executeQuery("select * from COMPANY");
            if(results.next()){
                company = getCompanyResults(results);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return company;
    }
}