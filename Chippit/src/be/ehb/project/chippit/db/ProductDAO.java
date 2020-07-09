package be.ehb.project.chippit.db;

import be.ehb.project.chippit.entity.*;

import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends BaseDAO {
    public ProductDAO() {
    }


    //Create a product
    public boolean createProduct(Product product, String categoryName) {
        CompanyDAO companyDAO = new CompanyDAO();
        Company company = companyDAO.getCompany();

        try (Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("insert into PRODUCT values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            s.setInt(1, product.getProductID());
            s.setInt(2, company.getCompanyId());
            s.setString(3, categoryName);
            s.setString(4, product.getProductName());
            s.setDouble(5, product.getPrice());
            s.setInt(6, product.getQuantity());
            s.setString(7, product.getManufacturer());
            s.setString(8, product.getImage1());
            s.setString(9, product.getImage2());
            s.setString(10, product.getImage3());
            s.setString(11, product.getKeyword1());
            s.setString(12, product.getKeyword2());
            s.setString(13, product.getKeyword3());
            s.setString(14, product.getDescription());
            s.setBoolean(15, product.getActive());
            int result = s.executeUpdate();
            if (result > 0)
                return true;
            else
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //Get a list of all products
    public ArrayList<Product> getListOfProducts(){
        ArrayList<Product> allProducts = new ArrayList<>();
        try(Connection c = getConnection()) {
            Statement s = c.createStatement();
            ResultSet results = s.executeQuery("select * from PRODUCT" );
            while(results.next())
            {
                Product p = getProductResults(results);
                if(p!=null)
                    allProducts.add(p);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allProducts;
    }

    //Get a product results
    private Product getProductResults(ResultSet results) {
        Product p = null;

        try {
            p = new Product(
                    results.getInt(1),
                    results.getString(4),
                    results.getDouble(5),
                    results.getString(7),
                    results.getInt(6),
                    results.getString(14),
                    results.getString(11),
                    results.getString(12),
                    results.getString(13),
                    results.getString(8),
                    results.getString(9),
                    results.getString(10),
                    results.getBoolean(15)
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    //Get a product by ID
    public Product getProductById(int ID){
        Product p = null;
        try (Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("select * from PRODUCT where product_id = ?");
            s.setInt(1,ID);
            ResultSet results = s.executeQuery();
            if(results.next())
            {
                p = getProductResults(results);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return p;
    }

    public void updateProduct(int product_id, Product p, String categoryName){
        String sql="UPDATE PRODUCT SET category_name = ? ," +
                "product_name = ? ," +
                "price = ? ," +
                "quantity = ? ," +
                "manufacturer = ? ," +
                "image1 = ? , " +
                "image2 = ? ," +
                "image3 = ? , " +
                "keyword1 = ? , " +
                "keyword2 = ? , " +
                "keyword3 = ? , " +
                "description = ? " +
                "WHERE product_id = ?";
        try(Connection connection=getConnection()) {
            PreparedStatement s =connection.prepareStatement(sql);
            s.setString(1,categoryName);
            s.setString(2,p.getProductName());
            s.setDouble(3,p.getPrice());
            s.setInt(4,p.getQuantity());
            s.setString(5,p.getManufacturer());
            s.setString(6,p.getImage1());
            s.setString(7,p.getImage2());
            s.setString(8,p.getImage3());
            s.setString(9,p.getKeyword1());
            s.setString(10,p.getKeyword2());
            s.setString(11,p.getKeyword3());
            s.setString(12,p.getDescription());
            s.setInt(13,product_id);
            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Delete a product
    public boolean deleteProduct(int id) {
        try (Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("delete from PRODUCT where product_id = ?");
            s.setInt(1, id);
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

