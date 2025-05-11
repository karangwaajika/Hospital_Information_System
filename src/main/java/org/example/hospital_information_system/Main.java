package org.example.hospital_information_system;

import org.example.hospital_information_system.database.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hospital_information_system.database.SchemaCreator;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
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
