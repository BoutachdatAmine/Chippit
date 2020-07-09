package be.ehb.project.chippit.db;

import java.io.*;
import java.sql.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ReadFromDbToExcel extends BaseDAO {

    //Product
    public void productToExcel(String directory) throws SQLException{
        // Voorbeeld path
        String excelPath= directory + "\\ProductEXCEL.xls";

        // We willen in de database geraken
        try(Connection connection=getConnection()){
            //sql query statement
            String sql= "SELECT * FROM PRODUCT";

            Statement s = connection.createStatement();

            ResultSet r = s.executeQuery(sql);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet= workbook.createSheet("Product_Chippit");

            //methodes om data te schrijven in excel file
            writeHeaderProduct(sheet);
            writeProductData(r,sheet);

            FileOutputStream outputStream= new FileOutputStream(excelPath);
            workbook.write(outputStream);
            workbook.close();
            s.close();

        } catch (FileNotFoundException e) {
            System.out.println("Problem with DB");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Problem with Excel");
            e.printStackTrace();
        }
    }

    private void writeHeaderProduct(XSSFSheet sheet){
        Row headerRow= sheet.createRow(0);

        Cell headerCell= headerRow.createCell(0);
        headerCell.setCellValue("Product_ID");

        headerCell= headerRow.createCell(1);
        headerCell.setCellValue("Company_code");

        headerCell= headerRow.createCell(2);
        headerCell.setCellValue("Category_name");

        headerCell= headerRow.createCell(3);
        headerCell.setCellValue("Product_name");

        headerCell= headerRow.createCell(4);
        headerCell.setCellValue("Price");

        headerCell= headerRow.createCell(5);
        headerCell.setCellValue("Quantity");

        headerCell= headerRow.createCell(6);
        headerCell.setCellValue("Manufacturer");

        headerCell= headerRow.createCell(7);
        headerCell.setCellValue("Keyword1");

        headerCell= headerRow.createCell(8);
        headerCell.setCellValue("Keyword2");

        headerCell= headerRow.createCell(9);
        headerCell.setCellValue("Keyword3");

        headerCell= headerRow.createCell(10);
        headerCell.setCellValue("Description");

        headerCell= headerRow.createCell(11);
        headerCell.setCellValue("Active");
    }

    private void writeProductData(ResultSet result,XSSFSheet sheet)throws SQLException{
        // Eerste rij voor voor data insert
        int rowCounter= 1;

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

            Row row= sheet.createRow(rowCounter++);

            int columnCounter=0;

            Cell cell= row.createCell(columnCounter++);
            cell.setCellValue(productID);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(companyID);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(categoryName);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(productName);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(price);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(quantity);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(manufacturer);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(keyword1);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(keyword2);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(keyword3);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(description);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(active);

        }
    }

    //User
    public void usersToEXcel(String directory)throws SQLException{

        String excelPath= directory + "\\Users-export.xls";

        // We willen in de database geraken
        try(Connection connection=getConnection()){
            //sql query statement
            String sql= "SELECT * FROM USERS";

            Statement s = connection.createStatement();

            ResultSet r = s.executeQuery(sql);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet= workbook.createSheet("User_Chippit");

            //methodes om data te schrijven in excel file
            writeHeaderUsers(sheet);
            writeUserData(r,sheet);

            FileOutputStream outputStream= new FileOutputStream(excelPath);
            workbook.write(outputStream);
            workbook.close();
            s.close();

        } catch (FileNotFoundException e) {
            System.out.println("Problem with DB");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Problem with EXCEL");
            e.printStackTrace();
        }
    }

    private void writeHeaderUsers(XSSFSheet sheet){
        //We maken de eerste rij van onze excel sheet
        Row headerRow= sheet.createRow(0);

        Cell headerCell= headerRow.createCell(0);
        headerCell.setCellValue("ID");

        headerCell= headerRow.createCell(1);
        headerCell.setCellValue("Company_code");

        headerCell= headerRow.createCell(2);
        headerCell.setCellValue("First_name");

        headerCell= headerRow.createCell(3);
        headerCell.setCellValue("Last_name");

        headerCell= headerRow.createCell(4);
        headerCell.setCellValue("Phone");

        headerCell= headerRow.createCell(5);
        headerCell.setCellValue("Gender");

        headerCell= headerRow.createCell(6);
        headerCell.setCellValue("Date_of_birth");

        headerCell= headerRow.createCell(7);
        headerCell.setCellValue("Country");

        headerCell= headerRow.createCell(8);
        headerCell.setCellValue("City");

        headerCell= headerRow.createCell(9);
        headerCell.setCellValue("Address1");

        headerCell= headerRow.createCell(10);
        headerCell.setCellValue("Address2");

        headerCell= headerRow.createCell(11);
        headerCell.setCellValue("Postal_code");

        headerCell= headerRow.createCell(12);
        headerCell.setCellValue("E-mail");

        headerCell= headerRow.createCell(13);
        headerCell.setCellValue("Password");

        headerCell= headerRow.createCell(14);
        headerCell.setCellValue("Permission");

    }

    private void writeUserData(ResultSet result, XSSFSheet sheet) throws SQLException {
        // Eerste rij voor voor data insert
        int rowCounter= 1;
        while (result.next()){
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
            String password=result.getString("password");
            int permission= result.getInt("permission");
            String address1=result.getString("address1");
            String address2=result.getString("address2");

            Row row= sheet.createRow(rowCounter++);

            int columnCounter=0;

            Cell cell= row.createCell(columnCounter++);
            cell.setCellValue(ID);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(companyCode);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(firstname);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(lastname);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(phone);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(gender);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(birthday);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(country);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(city);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(address1);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(address2);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(postalcode);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(email);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(password);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(permission);

        }
    }

    //Order_product
    public void orderProductToExcel(String directory){
        String excelpath=directory+"\\OrderProductList.xls";
        // Try connect with database
        try(Connection connection= getConnection()){
            //sql query statement
            String sql= "SELECT * FROM ORDER_PRODUCT";

            Statement s = connection.createStatement();

            ResultSet r = s.executeQuery(sql);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet= workbook.createSheet("OrderProduct_Chippit");

            //methodes om data te schrijven in excel file
            writeHeaderOrderproducts(sheet);
            writeOrderProductData(r,sheet);

            FileOutputStream outputStream= new FileOutputStream(excelpath);
            workbook.write(outputStream);
            workbook.close();
            s.close();

        } catch (FileNotFoundException e) {
            System.out.println("Problem with DB");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Problem with EXCel");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void writeHeaderOrderproducts(XSSFSheet sheet){
        Row headerRow= sheet.createRow(0);

        Cell headerCell= headerRow.createCell(0);
        headerCell.setCellValue("Oder_Product_ID");

        headerCell= headerRow.createCell(1);
        headerCell.setCellValue("Order_id");

        headerCell= headerRow.createCell(2);
        headerCell.setCellValue("Product_ID");

        headerCell= headerRow.createCell(3);
        headerCell.setCellValue("Quantity");

        headerCell= headerRow.createCell(4);
        headerCell.setCellValue("Total_price_product");
    }

    public void writeOrderProductData(ResultSet result, XSSFSheet sheet) throws SQLException {

        int rowCounter= 1;

        while (result.next()){
            int order_product_id=result.getInt("order_product_id");
            int order_id=result.getInt("order_id");
            int product_id=result.getInt("product_id");
            int quantity=result.getInt("quantity");
            double total=result.getDouble("total_price_product");

            Row row= sheet.createRow(rowCounter++);

            int columnCounter=0;

            Cell cell= row.createCell(columnCounter++);
            cell.setCellValue(order_product_id);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(order_id);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(product_id);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(quantity);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(total);

        }
    }

    //Orders
    public void ordersToExcel(String directory) throws SQLDataException{
        String excelpath=directory +"\\OrdersList.xls";
        // Try connect with database
        try(Connection connection= getConnection()){
            //sql query statement
            String sql= "SELECT * FROM ORDERS";

            Statement s = connection.createStatement();

            ResultSet r = s.executeQuery(sql);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet= workbook.createSheet("Orders_Chippit");


            writeHeaderOrders(sheet);
            writeOrdersData(r,sheet);

            FileOutputStream outputStream= new FileOutputStream(excelpath);
            workbook.write(outputStream);
            workbook.close();
            s.close();

        } catch (FileNotFoundException | SQLException e) {
            System.out.println("Problem with DB");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Problem with EXCel");
            e.printStackTrace();
        }

    }

    public void writeHeaderOrders(XSSFSheet sheet){
        Row headerRow= sheet.createRow(0);

        Cell headerCell= headerRow.createCell(0);
        headerCell.setCellValue("Order_id");

        headerCell= headerRow.createCell(1);
        headerCell.setCellValue("Customer_id");

        headerCell= headerRow.createCell(2);
        headerCell.setCellValue("Total_price");

    }

    public void writeOrdersData(ResultSet result, XSSFSheet sheet) throws SQLException {

        int rowCounter= 1;

        while (result.next()){
            int order_id=result.getInt("order_id");
            int customer_id=result.getInt("customer_id");
            double total_price=result.getDouble("total_price");

            Row row= sheet.createRow(rowCounter++);

            int columnCounter=0;

            Cell cell= row.createCell(columnCounter++);
            cell.setCellValue(order_id);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(customer_id);

            cell=row.createCell(columnCounter++);
            cell.setCellValue(total_price);

        }
    }
}
