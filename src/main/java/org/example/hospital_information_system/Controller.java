package org.example.hospital_information_system;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.util.Callback;
import org.example.hospital_information_system.database.DBConnection;
import org.example.hospital_information_system.model.Patient;
import org.example.hospital_information_system.repository.PatientRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;

public class Controller {
    // input field
    @FXML
    private TextField firstNameField;
    @FXML
    private ComboBox<String> sexComboBox;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField nationalIdField;
    @FXML
    private TextField surnameField;
    @FXML
    public DatePicker dateOfBirthField;



    @FXML
    private void handleSubmit() {
        try(Connection conn = DBConnection.getConnection()) {
            String firstName = firstNameField.getText();
            String nationalId = nationalIdField.getText();
            String surname = surnameField.getText();
            String telephone = telephoneField.getText();
            String sex = sexComboBox.getValue();
            LocalDate dateOfBirth = dateOfBirthField.getValue();


            if (sex == null || sex.isBlank()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Error", "Please select a sex.");
                return;
            }
            if(firstName.isEmpty()){
                showAlert(Alert.AlertType.ERROR, "Invalid Firstname",
                        "Firstname cannot be empty.");
                return;
            }
            if (!firstName.matches("^[a-zA-Z\\s]+$")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Firstname",
                        "Firstname must only contain letters and spaces.");
                return;
            }
            if(surname.isEmpty()){
                showAlert(Alert.AlertType.ERROR, "Invalid Surname",
                        "Surname cannot be empty.");
                return;
            }
            if (!surname.matches("^[a-zA-Z\\s]+$")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Surname",
                        "Surname must only contain letters and spaces.");
                return;
            }
            if(telephone.isEmpty()){
                showAlert(Alert.AlertType.ERROR, "Invalid Phone",
                        "Phone cannot be empty.");
                return;
            }
            if (!(Pattern.matches("\\d+", telephone))) {
                showAlert(Alert.AlertType.ERROR, "Invalid Phone",
                        "Please provide a valid Phone.");
                return;
            }
            if (!(telephone.length() == 10)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Phone",
                        "Please provide a valid Phone with 10 digits only.");
                return;
            }
            if(nationalId.isEmpty()){
                showAlert(Alert.AlertType.ERROR, "Invalid National ID",
                        "National ID cannot be empty.");
                return;
            }
            if (!(Pattern.matches("\\d+", nationalId))) {
                showAlert(Alert.AlertType.ERROR, "Invalid ID",
                        "Please provide a valid National ID.");
                return;
            }
            if (!(nationalId.length() == 16)) {
                showAlert(Alert.AlertType.ERROR, "Invalid ID",
                        "Please provide a valid Phone with exactly 16 digits.");
                return;
            }

            // add patient to Database
            Patient patient = new Patient(nationalId,firstName, surname, telephone, sex.charAt(0), dateOfBirth);
            PatientRepository patientRepo = new PatientRepository(conn);
            if(!patientRepo.checkPatientExists(patient.getNationalId())){
                patientRepo.insertPatient(patient); // insert 
            }else{
                showAlert(Alert.AlertType.ERROR, "Patient Error",
                        "Patient with the provided national ID exists !!");
                return;
            }

            showAlert(Alert.AlertType.CONFIRMATION, "Success",
                    "✅ Patient added Successfully: ");
            System.out.println("✅ Patient added: ");
            clearForm();

        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error",
                    "⚠️ Error: " + e.getMessage());
            return;
        }
    }

    private void clearForm() {
        firstNameField.clear();
        sexComboBox.getSelectionModel().clearSelection();
        nationalIdField.clear();
        surnameField.clear();
        telephoneField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        dateOfBirthField.setDayCellFactory(getPastOnlyFactory());
    }

    // disable future dates
    private Callback<DatePicker, DateCell> getPastOnlyFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                // Disable all future dates
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        };
    }



}