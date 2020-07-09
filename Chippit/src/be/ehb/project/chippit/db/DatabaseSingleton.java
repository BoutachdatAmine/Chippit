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
                connection = DriverManager.getConnection("jdbc:mysql://dt5.ehb.be/1920PROGPROJ_GR6", "1920PROGPROJ_GR6", "YWfgAwH");
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
