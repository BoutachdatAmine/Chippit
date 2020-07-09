package be.ehb.project.chippit.db;

import be.ehb.project.chippit.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseDAO {
    private Connection connection = null;
    public BaseDAO(Connection c)
    {
        this.connection = c;
    }
    public BaseDAO(){};


    public Connection getConnection()
    {
        try {
            if (connection == null || connection.isClosed())
                connection = DatabaseSingleton.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}