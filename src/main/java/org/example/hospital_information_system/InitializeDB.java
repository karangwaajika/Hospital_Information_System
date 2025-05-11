package org.example.hospital_information_system;

import org.example.hospital_information_system.database.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hospital_information_system.database.SchemaCreator;


public class InitializeDB {
    private static final Logger logger = LogManager.getLogger(InitializeDB.class);
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement()) {
            String sql = "ALTER TABLE patient " +
                    "ADD COLUMN national_id VARCHAR(16) UNIQUE,"+
                    "ADD CONSTRAINT national_id_length CHECK (char_length(national_id) = 16);";
            stmt.executeUpdate(sql);

            SchemaCreator createTable = new SchemaCreator(conn);
            createTable.createEmployeeTable();
            createTable.createEmployeeAddressTable();
            createTable.createDoctorTable();
            createTable.createSpecializationTable();
            createTable.createDoctorSpecializationTable();
            createTable.createNurseTable();
            createTable.createDepartmentTable();
            createTable.createNurseRotatingDepartmentTable();
            createTable.createWardTable();
            createTable.createDiagnosisTable();
            createTable.createPatientTable();
            createTable.createPatientAddressTable();
            createTable.createHospitalizationTable();
            createTable.createTransferTable();

            System.out.println("DB connected");

        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
