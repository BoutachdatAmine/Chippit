package be.ehb.project.chippit.db;

import be.ehb.project.chippit.entity.Order;
import be.ehb.project.chippit.entity.OrderProduct;

import java.sql.*;
import java.util.ArrayList;

public class OrderProductDAO extends BaseDAO{
    public OrderProductDAO(){};

    //Create an order product
    public boolean createOrderProduct(int order_id, int product_id, int quantity){
        ProductDAO productDAO = new ProductDAO();
        double totalProductPrice = productDAO.getProductById(product_id).getPrice() * quantity;
        try(Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("insert into ORDER_PRODUCT values(NULL, ?,?,?,?)");
            s.setInt(1, order_id);
            s.setInt(2, product_id);
            s.setInt(3, quantity);
            s.setDouble(4, totalProductPrice);
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

    //Get a list of order products
    public ArrayList<OrderProduct> getListOfOrderProducts(){
        ArrayList<OrderProduct> allOrderProducts = new ArrayList<>();
        try(Connection c = getConnection()) {
            Statement s = c.createStatement();
            ResultSet results = s.executeQuery("select * from ORDER_PRODUCT" );
            while(results.next())
            {
                OrderProduct op = getOrderProductResults(results);
                if(op!=null)
                    allOrderProducts.add(op);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allOrderProducts;
    }

    public OrderProduct getOrderProductResults(ResultSet results){
        OrderProduct orderProduct = null;
        try{
            orderProduct = new OrderProduct(
                    results.getInt(1),
                    results.getInt(2),
                    results.getInt(3),
                    results.getInt(4)
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orderProduct;
    }

    //Get the totalPrice for amount by making a sum of (product_price*quantity)
    public double getOrderAmount(int orderId){
        double totalAmountOrder = 0;
        try(Connection c = getConnection()){
            PreparedStatement s = c.prepareStatement("SELECT total_price_product FROM ORDER_PRODUCT WHERE order_id = ?");
            s.setInt(1, orderId);
            ResultSet resultSet = s.executeQuery();
            while (resultSet.next()){
                totalAmountOrder += getOrderAmountResult(resultSet);
                System.out.println(totalAmountOrder);}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return totalAmountOrder;
    }

    public double getOrderAmountResult(ResultSet results){
        double orderAmount = 0;
        try{
            orderAmount = results.getDouble("total_price_product");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orderAmount;
    }

    //Delete an order by order ID
    public boolean deleteOrderProduct(int order_id){
        try(Connection c = getConnection()) {
            PreparedStatement s = c.prepareStatement("DELETE FROM ORDER_PRODUCT WHERE order_id = ?");
            s.setInt(1, order_id);
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
}
