package be.ehb.project.chippit.db;

import be.ehb.project.chippit.entity.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFromExcel {

    //Users
    public void importUsersFromExcel(String path) throws IOException {

        //obtaining input bytes from a file
        FileInputStream fis = new FileInputStream(new File(path));
        //creating workbook instance that refers to .xls file
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        //creating a Sheet object to retrieve the object
        HSSFSheet sheet = wb.getSheetAt(0);
        //evaluating cell type
        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
        PersonDAO personDao = new PersonDAO();


        //get each row and cell from the excel and put into the db;
        //created an array for numeric values from the excel and strings values from the excel
        for (Row row : sheet)     //iteration over row using for each loop
        {
            if (row.getRowNum() == 0) {

            } else {
                ArrayList<String> stringsfromexcel = new ArrayList();
                ArrayList<Integer> integersfromexcel = new ArrayList();
                for (Cell cell : row)    //iteration over cell using for each loop
                {

                    switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:   //field that represents numeric cell type
                            //getting the value of the cell as a number
                            int number = (int) cell.getNumericCellValue();
                            integersfromexcel.add(number);
                            break;
                        case Cell.CELL_TYPE_STRING:    //field that represents string cell type
                            //getting the value of the cell as a string
                            String string = cell.getStringCellValue();
                            stringsfromexcel.add(string);
                            break;
                    }
                }


                String firstname = stringsfromexcel.get(0);
                String lastname = stringsfromexcel.get(1);
                String Phone = String.valueOf(integersfromexcel.get(1));
                String Gender = stringsfromexcel.get(2);
                String birthdate = stringsfromexcel.get(3);
                String country = stringsfromexcel.get(4);
                String city = stringsfromexcel.get(5);
                String address1 = stringsfromexcel.get(6);
                String address2 = String.valueOf(integersfromexcel.get(2));
                String postcode = String.valueOf(integersfromexcel.get(3));
                String email = stringsfromexcel.get(7);
                String password = stringsfromexcel.get(8);
                String tfa = stringsfromexcel.get(9);
                int companyId = integersfromexcel.get(0);
                int permission = integersfromexcel.get(4);

                Address a = new Address(country, city, postcode, address1, address2);
                Person p = new Person(firstname, lastname, Phone, Gender, email, birthdate, permission, password, companyId, a,tfa);
                personDao.createUser(p);

            }
        }
    }

    //Product
    public void importProductFromExcel(String path) throws IOException {

        ProductDAO productdao = new ProductDAO();
        //obtaining input bytes from a file
        FileInputStream fis = new FileInputStream(new File(path));
        //creating workbook instance that refers to .xls file
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        //creating a Sheet object to retrieve the object
        HSSFSheet sheet = wb.getSheetAt(0);
        //evaluating cell type
        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();


        for (Row row : sheet)     //iteration over row using for each loop
        {

            if (row.getRowNum() == 0) {

            } else {
                ArrayList<String> stringsfromexcel = new ArrayList();
                ArrayList<Double> doublesfromexcel = new ArrayList<>();
                boolean active = false;

                for (Cell cell : row)    //iteration over cell using for each loop
                {

                    switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:   //field that represents numeric cell type
                            //getting the value of the cell as a number
                            double doubles = (double) cell.getNumericCellValue();
                            doublesfromexcel.add(doubles);
                            break;
                        case Cell.CELL_TYPE_STRING:    //field that represents string cell type
                            //getting the value of the cell as a string
                            String string = cell.getStringCellValue();
                            stringsfromexcel.add(string);
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:    //field that represents boolean cell type
                            //getting the value of the cell as a boolean
                            active = cell.getBooleanCellValue();
                            break;
                    }
                }


                int productid = (int) Math.round(doublesfromexcel.get(0));
                int companyid = (int) Math.round(doublesfromexcel.get(1));
                double price = doublesfromexcel.get(2);
                String categoryname = stringsfromexcel.get(0);
                String productname = stringsfromexcel.get(1);
                int quantity = (int) Math.round(doublesfromexcel.get(3));
                String manufacturer = stringsfromexcel.get(2);
                String image1 = stringsfromexcel.get(3);
                String image2 = stringsfromexcel.get(4);
                String image3 = stringsfromexcel.get(5);
                String keyword1 = stringsfromexcel.get(6);
                String keyword2 = stringsfromexcel.get(7);
                String keyword3 = stringsfromexcel.get(8);
                String description = stringsfromexcel.get(9);

                Product product = new Product(productid, productname, price, manufacturer, quantity, description, keyword1, keyword2, keyword3, image1, image2, image3, active);

                CategoryDAO categoryDAO = new CategoryDAO();
                //  Make new String variable and initalize it with a categoryname (String) from CategoryDAO
                String category = categoryDAO.getCategoryByName(categoryname).getCategoryName();
                //  Call the createProduct function from productDAO and give next params: product & category(string)
                System.out.println(productdao.createProduct(product, category));
            }
        }
    }

}