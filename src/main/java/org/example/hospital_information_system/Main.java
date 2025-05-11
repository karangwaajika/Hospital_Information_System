package org.example.hospital_information_system;

import org.example.hospital_information_system.database.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hospital_information_system.exception.PatientExitsException;
import org.example.hospital_information_system.model.Patient;
import org.example.hospital_information_system.repository.PatientRepository;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        LocalDateTime date = LocalDateTime.now();
        LocalDate today = date.toLocalDate();
        Patient p = new Patient("1199670054907072","Joel", "Karangwa", "0782983266", 'F', today);
        try (Connection conn = DBConnection.getConnection()) {
            //insert patient
            PatientRepository patientRepo = new PatientRepository(conn);
            if(!patientRepo.checkPatientExists(p.getNationalId())){
                patientRepo.insertPatient(p);

            }else{
                System.out.println("user exist!");
                System.out.println(patientRepo.getAllPatients().toString());
                patientRepo.getAllPatients()
                  .forEach(n -> System.out.println(n.getId()));
            }

            // update user
            Patient p2 = new Patient(1, "1199670054907072","Change", "Karangwa",
                    "0782983266", 'F', today);
            patientRepo.updateUser(p2);

        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
