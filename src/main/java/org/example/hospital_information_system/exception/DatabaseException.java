package org.example.hospital_information_system.exception;

import java.sql.SQLException;

public class DatabaseException extends SQLException {
    public DatabaseException(String message) {
        super(message);
    }
}
