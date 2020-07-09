package be.ehb.project.chippit.db;

import be.ehb.project.chippit.entity.Address;
import be.ehb.project.chippit.entity.Category;
import be.ehb.project.chippit.entity.Person;
import be.ehb.project.chippit.entity.Product;

import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO extends BaseDAO{
    public CategoryDAO(){}

    //Create a category
    public boolean createCategory(Category cat){
        try(Connection c = getConnection()){
            PreparedStatement s = c.prepareStatement("insert into CATEGORY values (?)");
            s.setString(1, cat.getCategoryName());
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

    //Get a list of all categories
    public ArrayList<Category> getListOfCategories(){
        ArrayList<Category> allCategories = new ArrayList<>();
        try(Connection c = getConnection()) {
            Statement s = c.createStatement();
            ResultSet results = s.executeQuery("select category_name from CATEGORY" );
            while(results.next())
            {
                Category category = getCategoryResult(results);
                if(c!=null)
                    allCategories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCategories;
    }

    //Get category results
    private Category getCategoryResult(ResultSet results) {
        Category c = null;
        try {
            c = new Category(
                    results.getString(1)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    //Get a category by name
    public Category getCategoryByName(String categoryName){
        Category category = null;
        try(Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("select category_name from CATEGORY where category_name = ?");
            s.setString(1,categoryName);
            ResultSet results = s.executeQuery();
            if(results.next())
            {
                category = getCategoryResult(results);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    public boolean updateCategory(String newCategory_name,String oldCategory_name) throws SQLException {
        try(Connection connection=getConnection()){
            PreparedStatement s=connection.prepareStatement("UPDATE CATEGORY SET category_name = ? WHERE category_name= ?");

            s.setString(1,newCategory_name);
            s.setString(2,oldCategory_name);

            int result = s.executeUpdate();
            if (result > 0)
                return true;
            else return false;

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("error");
        }
        return false;
    }

    //Delete a category
    public boolean deleteCategory(String categoryName) {
        try (Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("delete from CATEGORY where category_name = ?");
            s.setString(1, categoryName);
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