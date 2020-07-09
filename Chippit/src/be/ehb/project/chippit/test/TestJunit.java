package be.ehb.project.chippit.test;

import be.ehb.project.chippit.db.*;
import be.ehb.project.chippit.entity.*;
import org.junit.Assert;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class TestJunit {
    //Check all the comments before running
    //Some Tests needs to view the DB before

    private Address adres1,adres2,adres3;
    private Person p1,p2;
    private Company company1;
    private Category category1;
    private Product product;
    private Order order;
    private OrderProduct orderproduct;
    private PersonDAO persondao;
    private LoginDAO logindao;
    private CompanyDAO companydao;
    private CategoryDAO categorydao;
    private ProductDAO productdao;
    private OrderDAO orderdao;
    private OrderProductDAO orderproductdao;

    @Before
    public void setUp() throws Exception
    {
        //Addresses
        adres1 = new Address("Belgium", "Brussels", "1190", "AAAA", "AAAA");
        adres2 = new Address("Mexico", "Mexico", "-123", "AAAA", "AAAA");
        adres3 = new Address("Belgium", "Brussels", "28739731", "AAAA", "AAAA");

        //Persons
        p1 = new Person(2,"Anas", "a", "000", "M", "rayan@chippit.com",
                null, 5, null, 2, adres1);
        p2 = new Person(201, "Omer", "Oz", "0009898", "M", "emailnotuse101@chippit.com",
                "02/20/2000", 5, "password", 2, adres1);

        //Company's
        company1 = new Company(1, "ANASTEST", "nhdezlj", "Anas", adres1);

        //Category's
        category1 = new Category("Test2");

        //Products
        product = new Product(190,"ad",12.0,"a",4,"Testss","a","a","a","a","a","a",true);

        //Orders
        order = new Order(3, 8 ,2039.00);

        //OrderProduct
        orderproduct = new OrderProduct(30,20,1);


        //DAOS
        persondao = new PersonDAO();
        logindao = new LoginDAO();
        companydao = new CompanyDAO();
        categorydao = new CategoryDAO();
        productdao = new ProductDAO();
        orderdao = new OrderDAO();
        orderproductdao = new OrderProductDAO();



    }

    //Address Test
    @Test
    public void testCheckPostalCode1() {

        boolean expected = true;


        boolean actual = adres1.checkPostalCode(adres1.getPostalCode());

        Assert.assertEquals("Valid Postal Code",expected, actual);
    }

    @Test
    public void testCheckPostalCode2() {

        boolean actual = adres2.checkPostalCode(adres2.getPostalCode());

        Assert.assertFalse("Not Valid Postal Code" ,actual);
    }

    @Test
    public void testCheckPostalCode3() {

        boolean actual = adres3.checkPostalCode(adres3.getPostalCode());

        Assert.assertFalse("Not Valid Postal Code" , actual);
    }

    //Person entity test
    @Test
    public void testCheckforEmails1() {
        //email already in Database, expected false

        boolean actual = p1.checkEmailForUser(p1.getEmail());
        Assert.assertFalse("Not Valid Postal Code" , actual);

    }

    @Test
    public void testCheckforEmails2() {
        //email not used,expected true
        boolean expected = true;

        boolean actual = p2.checkEmailForUser(p2.getEmail());
        Assert.assertEquals(expected, actual);

    }

    //Person tests

    @Test
    public void testCreatePersonInDB1()
    {
        //change the email if its already in DB before Run
        boolean expected = true;


        boolean actual = persondao.createUser(p2);
        Assert.assertEquals(expected,actual);

    }


    @Test
    public void testCreatePersonInDB2()
    {
        //email Already used, couldn't be saved in DB


        boolean actual = persondao.createUser(p1);
        Assert.assertFalse("Email already in DB, Person not added to DB",actual);

    }



    @Test
    public void testGetUserByLastName() {

        String expected = "Rayan";
        Person p = persondao.getUserByLastName("El-Koukouchi");
        String actual = p.getFirstName();
        Assert.assertEquals("First name of the user we get is what we expected",expected, actual);
    }

    @Test
    public void testGetUserByLastName2() {

        String expected = "Notfirstname";
        Person p = persondao.getUserByLastName("El-Koukouchi");
        String actual = p.getFirstName();
        Assert.assertFalse("First name of the user we get is not equal to what expected ",expected.equals(actual));
    }

    @Test
    public void testUpdateUser() {
        //Userid exists in the DB, Updated Person
        boolean expected = true;

        boolean actual = persondao.updateUser(112,p1);
        Assert.assertEquals("Userid exists in DB -> updated",expected,actual);
    }

    @Test
    public void testUpdateUser2() {
        //Userid don't exists in DB, No Updates;


        boolean actual = persondao.updateUser(219,p1);
        Assert.assertFalse("UserID dont exists in DB",actual);
    }

    @Test
    public void testDeleteUserById1() {
        //Enter a UserId that exists in DB
        //User exists in the DB,Expected true
        boolean expected = true;

        boolean actual = persondao.deleteUserById(167);
        Assert.assertEquals("If the id you choose exists in DB, person will be deleted from DB",expected, actual);

    }

    @Test
    public void testDeleteUserById2() {
        //Enter a False UserID;
        //User dont exists in DB, expected False


        boolean actual = persondao.deleteUserById(987);
        Assert.assertFalse("ID don't exists in DB" , actual);

    }


    //Login test

    @Test
    public void testCheckLogin1() {
        //Password is correct
        boolean expected = true;

        boolean actual = logindao.checkPassword("rayan@chippit.com", "azerty");
        Assert.assertEquals("email and password are correct",expected, actual);

    }

    @Test
    public void testCheckLogin2() {
        //Password is incorrect


        boolean actual = logindao.checkPassword("rayan@chippit.com", "incorrect");
        Assert.assertFalse("Password or email is incorrect", actual);

    }

    //Product test

    @Test
    public void testCreateProduct()
    {
        //valid Product,it will be saved in DB
        boolean expected = true;


        boolean actual = productdao.createProduct(product,"Burberry");
        Assert.assertEquals("Categoryname exusts in DB, product will be saved in DB",expected,actual);
    }

    @Test
    public void testCreateProduct2()
    {
        //valid Product,it will be saved in DB
        boolean expected = false;


        boolean actual = productdao.createProduct(product,"FalseCategory");
        Assert.assertEquals("Categoryname don't exists in DB, product not saved in DB",expected,actual);
    }

    @Test
    public void testDeleteProduct()
    {
        //Choose a Valid productid,it will be deleted
        boolean expected = true;

        boolean actual = productdao.deleteProduct(190);

        Assert.assertEquals("Productid exists, it will be deleted from DB",expected,actual);
    }

    @Test
    public void testDeleteProduct2()
    {
        //Productname not in DB


        boolean actual = productdao.deleteProduct(987);

        Assert.assertFalse("Productid not in DB, couldn't delete it",actual);
    }

    //Category tests

    @Test
    public void testCreateCategory() {
        boolean expected = true;


        boolean actual = categorydao.createCategory(category1);

        Assert.assertEquals("Categoryname don't exists in DB, it will be created in DB",expected, actual);
    }

    @Test
    public void testDeleteCategory() {
        //Category exists, it will be deleted
        boolean expected = true;

        CategoryDAO categorydao = new CategoryDAO();
        boolean actual = categorydao.deleteCategory("Test2");
        Assert.assertEquals("Categoryname exists, it will be deleted from DB",expected, actual);
    }

    @Test
    public void testGetAllCategories()
    {
        //compare the first element of the categories with the expected element
        String expected = "Burberry";

        ArrayList categories = categorydao.getListOfCategories();
        String actual = String.valueOf(categories.get(0));
        Assert.assertEquals("First element of category's equals to what expected",expected,actual);

    }

    @Test
    public void testGetAllCategories2()
    {
        //compare the first element of the categories with the expected element
        String expected = "NotFirstElement";

        ArrayList categories = categorydao.getListOfCategories();
        String actual = String.valueOf(categories.get(0));
        Assert.assertFalse("First element of category's don't equals to what expected",expected.equals(actual));

    }

    //Order tests

    @Test
    public void testCreateOrder() {
        //customerid exists in Db -> true
        boolean expected = true;

        boolean actual = orderdao.createOrder(8);

        Assert.assertEquals("Customerid exists in DB, order will be created in DB",expected,actual);


    }

    @Test(expected = SQLIntegrityConstraintViolationException.class)
    public void testCreateOrder2()
    {
        //customerid don't exists in DB -> exception


        assertThrows(SQLIntegrityConstraintViolationException.class,() -> orderdao.createOrder(12));

    }

    @Test
    public void testSetTotalPrice()
    {
        boolean expected = true;

        boolean actual = orderdao.setTotalPrice(200.0,23);
        Assert.assertEquals("Orderid exists, set total price is done",actual,expected);

    }

    @Test
    public void testSetTotalPrice2()
    {
        boolean expected = false;

        boolean actual = orderdao.setTotalPrice(200.0,97);
        Assert.assertEquals("Orderid don't exists in DB, set total price cannot be done",actual,expected);

    }
    //OrderProduct tests

    @Test
    public void testCreateOrderProduct()
    {
        //orderproduct will be created
        boolean expected = true;

        boolean actual = orderproductdao.createOrderProduct(24,20,1);
        Assert.assertEquals("order_id and product_id exists in DB, OrderProduct will be created in DB",actual,expected);

    }

    @Test
    public void testCreateOrderProduct2()
    {
        //orderproduct not created


        boolean actual = orderproductdao.createOrderProduct(27,20,1);
        Assert.assertFalse("productid or order_id don't exists in DB, can't be created",actual);

    }

    @Test
    public void testDeleteOrderProduct()
    {
        //Choose a orderid in the DB
        boolean expected = true;

        boolean actual = orderproductdao.deleteOrderProduct(24);
        Assert.assertEquals("Orderid exists in DB, it will be created in DB",actual,expected);

    }

    @Test
    public void testDeleteOrderProduct2()
    {
        //Orderid don't exists in DB
        boolean expected = false;

        boolean actual = orderproductdao.deleteOrderProduct(101);
        Assert.assertEquals("Orderid don't exists in DB, it will not be deleted",actual,expected);

    }

}