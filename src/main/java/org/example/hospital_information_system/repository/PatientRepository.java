package org.example.hospital_information_system.repository;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hospital_information_system.database.SchemaCreator;
import org.example.hospital_information_system.exception.PatientExitsException;
import org.example.hospital_information_system.model.Patient;

import java.sql.*;

public class PatientRepository {
    private Connection connection;
    private static final Logger logger = LogManager.getLogger(PatientRepository.class);

    public PatientRepository(Connection connection) {
        this.connection = connection;
    }

    public void insertPatient(Patient patient){
        String query = "INSERT INTO patient(first_name, surname, " +
                "telephone_number, sex, date_of_birth, national_id) values(?, ?, ?, ? , ?, ?)";
        try(PreparedStatement statement = this.connection.prepareStatement(query)){
            statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getSurname());
            statement.setString(3, patient.getTelephoneNumber());
            statement.setString(4, String.valueOf(patient.getSex()));
            statement.setObject(5, patient.getDateOfBirth());
            statement.setObject(6, patient.getNationalId());

            statement.executeUpdate();

            // Get generated ID
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    patient.setId(rs.getInt(1)); // update patient object id.
                }
            }

            System.out.println("Patient successfully inserted !!!");

        }catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    public boolean checkPatientExists(String patientNationalID) throws SQLException{
        String sql = "SELECT COUNT(*) FROM patient WHERE national_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patientNationalID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                //throw new PatientExitsException("National ID provided exist already: "+ patientNationalID);
                return count > 0;
            }

        }catch (PatientExitsException e){
            logger.log(Level.ERROR, e.getMessage());
        }
        return false;
    }
}
