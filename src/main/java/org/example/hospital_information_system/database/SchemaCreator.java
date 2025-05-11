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

    public void createEmployeeAddressTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS employee_address(" +
                    "id SERIAL PRIMARY KEY, " +
                    "country VARCHAR(30) NOT NULL, " +
                    "province VARCHAR(15) NOT NULL, " +
                    "district VARCHAR(15) NOT NULL, " +
                    "sector VARCHAR(15) NOT NULL, " +
                    "cell VARCHAR(15) NOT NULL, " +
                    "village VARCHAR(15) NOT NULL, " +
                    "employee_id INT REFERENCES employee(id) )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("Address table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void createDoctorTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS doctor(" +
                    "id SERIAL PRIMARY KEY, " +
                    "license_number VARCHAR(20) NOT NULL, " +
                    "employee_id INT REFERENCES employee(id) )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("Doctor table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void createSpecializationTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS specialization(" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(15) NOT NULL, " +
                    "category VARCHAR(15) NOT NULL )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("Specialization table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void createDoctorSpecializationTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS doctor_specialization(" +
                    "id SERIAL PRIMARY KEY, " +
                    "doctor_id INT REFERENCES doctor(id), " +
                    "specialization_id INT REFERENCES specialization(id) )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("DoctorSpecialization table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void createNurseTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS nurse(" +
                    "id SERIAL PRIMARY KEY, " +
                    "license VARCHAR(15) NOT NULL, " +
                    "employee_id INT REFERENCES employee(id) )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("Nurse table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void createDepartmentTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS department(" +
                    "id SERIAL PRIMARY KEY, " +
                    "code VARCHAR(15) NOT NULL, " +
                    "name VARCHAR(15) NOT NULL, " +
                    "building VARCHAR(15) NOT NULL, " +
                    "supervisor_id INT REFERENCES nurse(id), " +
                    "director_id INT REFERENCES doctor(id) )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("Department table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void createNurseRotatingDepartmentTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS nurse_rotating_department(" +
                    "id SERIAL PRIMARY KEY, " +
                    "start_date DATE NOT NULL, " +
                    "end_date DATE NULL, " +
                    "nurse_id INT REFERENCES nurse(id), " +
                    "department_id INT REFERENCES department(id) )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("NurseRotatingDepartment table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void createWardTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS ward(" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(15), " +
                    "bed_number INT, " +
                    "nurse_id INT REFERENCES nurse(id), " +
                    "department_id INT REFERENCES department(id) )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("Ward table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void createDiagnosisTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS diagnosis(" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(15) NOT NULL )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("Diagnosis table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void createPatientTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS patient(" +
                    "id SERIAL PRIMARY KEY, " +
                    "first_name VARCHAR(15) NOT NULL, " +
                    "surname VARCHAR(15) NOT NULL, " +
                    "telephone_number VARCHAR(20) NOT NULL, " +
                    "date_of_birth DATE NOT NULL, " +
                    "sex CHAR(1) DEFAULT 'F')";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("Patient table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void createPatientAddressTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS patient_address(" +
                    "id SERIAL PRIMARY KEY, " +
                    "country VARCHAR(30) NOT NULL, " +
                    "province VARCHAR(15) NOT NULL, " +
                    "district VARCHAR(15) NOT NULL, " +
                    "sector VARCHAR(15) NOT NULL, " +
                    "cell VARCHAR(15) NOT NULL, " +
                    "village VARCHAR(15) NOT NULL, " +
                    "patient_id INT REFERENCES patient(id) )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("PatientAddress table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public void createHospitalizationTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS hospitalization(" +
                    "id SERIAL PRIMARY KEY, " +
                    "arrive_date DATE, " +
                    "leave_date DATE NULL, " +
                    "bed_number INT, " +
                    "patient_id INT REFERENCES patient(id), " +
                    "diagnosis_id INT REFERENCES diagnosis(id), " +
                    "ward_id INT REFERENCES ward(id), " +
                    "doctor_id INT REFERENCES doctor(id) )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("Hospitalization table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }


    public void createTransferTable(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS transfer(" +
                    "id SERIAL PRIMARY KEY, " +
                    "transfer_date DATE NOT NULL, " +
                    "reason TEXT, " +
                    "from_ward_id INT REFERENCES ward(id), " +
                    "to_ward_id INT REFERENCES ward(id), " +
                    "hospitalization_id INT REFERENCES hospitalization(id) )";
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            System.out.println("Transfer table created !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
