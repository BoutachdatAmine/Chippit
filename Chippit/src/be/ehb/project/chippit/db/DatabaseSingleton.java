package be.ehb.project.chippit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSingleton {
    private static DatabaseSingleton instance = null;
    private Connection connection = null;
    private DatabaseSingleton(){}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    };

    public static DatabaseSingleton getInstance()
    {
        if (instance == null)
        {
            instance = new DatabaseSingleton();
        }
        return instance;
    }

    public Connection getConnection()
    {
        try {
            if (connection == null || connection.isClosed()){
                connection = DriverManager.getConnection("CONNECTION URL", "USERNAME", "PASSWORD");
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
