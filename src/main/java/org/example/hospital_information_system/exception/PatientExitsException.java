package org.example.hospital_information_system.exception;

import java.sql.SQLException;

public class PatientExitsException extends DatabaseException {
    public PatientExitsException(String message) {
        super(message);
    }
}
