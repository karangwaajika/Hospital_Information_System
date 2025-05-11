package org.example.hospital_information_system.database;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hospital_information_system.Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SchemaCreator {
    private final Connection connection;
    private static final Logger logger = LogManager.getLogger(SchemaCreator.class);

    public SchemaCreator(Connection connection){
        this.connection = connection;
    }

    public void createEmployeeTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS employee(" +
                    "id SERIAL PRIMARY KEY, " +
                    "employee_number VARCHAR(30) NOT NULL UNIQUE, " +
                    "first_name VARCHAR(15) NOT NULL, " +
                    "surname VARCHAR(15) NOT NULL, " +
                    "salary INT NOT NULL, " +
                    "telephone_number VARCHAR(20) NOT NULL, " +
                    "date_started DATE NOT NULL, " +
                    "sex CHAR(1) DEFAULT 'F', " +
                    "role VARCHAR(20) NOT NULL )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("Employee table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
