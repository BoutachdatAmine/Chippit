package be.ehb.project.chippit.db;

import be.ehb.project.chippit.entity.*;

import java.sql.*;
import java.util.ArrayList;

public class OrderDAO extends BaseDAO{
    public OrderDAO(){}

    //Create an order
    public boolean createOrder(int customer_id){
        OrderProductDAO orderProductDAO = new OrderProductDAO();
        try(Connection c = getConnection()){
            PreparedStatement s = c.prepareStatement("insert into ORDERS values(NULL, ?, 0)");
            s.setInt(1, customer_id);
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

    //Get a list with all orders
    public ArrayList<Order> getListOfOrders(){
        ArrayList<Order> allOrders = new ArrayList<>();
        try(Connection c = getConnection()) {
            Statement s = c.createStatement();
            ResultSet results = s.executeQuery("select * from ORDERS" );
            while(results.next())
            {
                Order o = getOrderResults(results);
                if(o!=null)
                    allOrders.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allOrders;
    }


    private Order getOrderResults(ResultSet results) {
        Order o = null;

        try {
            o = new Order(
                    results.getInt(1),
                    results.getInt(2),
                    results.getDouble(3)
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    //Set an orders total price
    public boolean setTotalPrice(double totalPrice, int order_id){
        try(Connection c = getConnection()){
            PreparedStatement s = c.prepareStatement("UPDATE ORDERS SET total_price = ? WHERE ORDERS.order_id = ?");
            s.setDouble(1,  totalPrice);
            s.setInt(2, order_id);
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
