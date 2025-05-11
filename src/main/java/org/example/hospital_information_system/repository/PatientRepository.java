package org.example.hospital_information_system.repository;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hospital_information_system.database.SchemaCreator;
import org.example.hospital_information_system.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientRepository {
    private Connection connection;
    private static final Logger logger = LogManager.getLogger(PatientRepository.class);

    public PatientRepository(Connection connection) {
        this.connection = connection;
    }

    public void insertPatient(Patient patient){
        String query = "INSERT INTO patient(first_name, surname, " +
                "telephone_number, sex, date_of_birth) values(?, ?, ?, ? ,?)";
        try(PreparedStatement statement = this.connection.prepareStatement(query)){
            statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getSurname());
            statement.setString(3, patient.getTelephoneNumber());
            statement.setString(4, String.valueOf(patient.getSex()));
            statement.setObject(5, patient.getDateOfBirth());

            statement.executeUpdate();

            System.out.println("Patient successfully inserted !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
