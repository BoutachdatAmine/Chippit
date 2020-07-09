package be.ehb.project.chippit.db;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ReadFromDbToCsv extends BaseDAO {

    //Product
    public void productsToCSV(String directory){
        // Voorbeeld path
        String csvPath= directory +"\\ProductList.csv";

        try (Connection connection=getConnection()){

            String sql = "SELECT * FROM PRODUCT";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvPath));

            //We willen eerst in de header schrijven
            fileWriter.write("PRODUCT_ID,COMPANY_CODE,CATEGORY_NAME,PRODUCT_NAME,PRICE,QUANTITY,MANUFACTURER,KEYWORD1,KEYWORD2,KEYWORD3,DESCRIPTION,ACTIVE");
            while(result.next()){
                int productID=result.getInt("product_id");
                int companyID=result.getInt("company_code");
                String categoryName=result.getString("category_name");
                String productName=result.getString("product_name");
                double price= result.getDouble("price");
                int quantity=result.getInt("quantity");
                String manufacturer=result.getString("manufacturer");
                String keyword1=result.getString("keyword1");
                String keyword2=result.getString("keyword2");
                String keyword3=result.getString("keyword3");
                String description=result.getString("description");
                int active=result.getInt("active");
                fileWriter.newLine();
                fileWriter.write(productID+", "+companyID+", "+categoryName+", "+productName+", "+price+", "+quantity+", "+manufacturer+", "+keyword1+", "+keyword2+", "+keyword3+", "+description+", "+active);

            }
            statement.close();
            fileWriter.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problem with DB");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem with CSV file");
        }
    }

    //User
    public void usersToCSV(String directory){
        // Voorbeeld path
        String csvPath= directory + "\\UsersList.csv";

        try (Connection connection=getConnection()){

            String sql = "SELECT * FROM USERS";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvPath));

            //We willen eerst in de header schrijven
            fileWriter.write("ID,Company_code,First_name,Last_name,Phone,Gender,Date_of_birth,Country,City,Address1,Address2,Postal_code,Email,Password,Permission");
            while(result.next()){
                int ID= result.getInt("id");
                int companyCode= result.getInt("company_code");
                String firstname=result.getString("first_name");
                String lastname=result.getString("last_name");
                String phone=result.getString("phone");
                String gender=result.getString("gender");
                String birthday=result.getString("date_of_birth");
                String country=result.getString("country");
                String city=result.getString("city");
                String email=result.getString("email");
                String postalcode=result.getString("postal_code");
                String passwordcsv=result.getString("password");
                int permission= result.getInt("permission");
                String address1=result.getString("address1");
                String address2=result.getString("address2");

                fileWriter.newLine();
                fileWriter.write(ID+", "+companyCode+", "+firstname+", "+ lastname+", "+phone+", "+gender+", "+birthday+", "+country+", "+city+", "+address1+", "+address2+", "+postalcode+","+email+","+passwordcsv+","+permission);

            }
            statement.close();
            fileWriter.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problem with DB");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem with CSV file");
        }
    }

    //Orders
    public void ordersToCSV(String directory){
        String csvPath= directory + "\\OrdersList.csv";
        try (Connection connection=getConnection()){
            String sql = "SELECT * FROM ORDERS";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvPath));

            //We willen eerst in de header schrijven
            fileWriter.write("Order_id, Customer_id, Total_price");
            while(result.next()){
                int order_id=result.getInt("order_id");
                int customer_id=result.getInt("customer_id");
                int total_price=result.getInt("total_price");
                fileWriter.newLine();
                fileWriter.write(order_id+", "+customer_id+", "+total_price);

            }
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problem with DB");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem with CSV file");
        }
    }

    //OrderProducts
    public void orderProductToCSV(String directory){
        String csvPath= directory + "\\OrdersProductList.csv";
        try(Connection connection=getConnection()){
            String sql = "SELECT * FROM ORDER_PRODUCT";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvPath));

            //We willen eerst in de header schrijven
            fileWriter.write("Order_product_id, Order_id, Product_id,Quantity, Total_price_product");
            while(result.next()){
                int order_product_id=result.getInt("order_product_id");
                int order_id=result.getInt("order_id");
                int product_id=result.getInt("product_id");
                int quantity=result.getInt("quantity");
                double total=result.getInt("total_price_product");
                fileWriter.newLine();
                fileWriter.write(order_product_id+", "+order_id+", "+product_id+", "+quantity+", "+total);
            }
            statement.close();
            fileWriter.close();
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problem with DB");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem with CSV file");
        }
    }

}