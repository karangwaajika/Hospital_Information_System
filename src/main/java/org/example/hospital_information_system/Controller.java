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

    // table fields
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> nationalIdColumn;
    @FXML
    private TableColumn<Patient, String> firstNameColumn;
    @FXML
    private TableColumn<Patient, String> surnameColumn;
    @FXML
    private TableColumn<Patient, String> telephoneColumn;
    @FXML
    private TableColumn<Patient, Character> sexColumn;
    @FXML
    private TableColumn<Patient, LocalDate> dateColumn;


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
                        "Please provide a valid National ID with exactly 16 digits.");
                return;
            }

            // add patient to Database
            Patient patient = new Patient(nationalId,firstName, surname, telephone, sex.charAt(0), dateOfBirth);
            PatientRepository patientRepo = new PatientRepository(conn);
            if(!patientRepo.checkPatientExists(patient.getNationalId())){
                patientRepo.insertPatient(patient); // insert

                //display to the table
                ObservableList<Patient> patientList = FXCollections
                        .observableArrayList(patientRepo.getAllPatients());
                patientTable.setItems(patientList); // retrieve patients
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
        nationalIdColumn.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        try(Connection conn = DBConnection.getConnection()){
            PatientRepository patientRepo = new PatientRepository(conn);
            //display to the table
            System.out.println(patientRepo.getAllPatients());
            ObservableList<Patient> patientList = FXCollections
                    .observableArrayList(patientRepo.getAllPatients());
            patientTable.setItems(patientList);
        }catch(SQLException e) {
            System.out.println("⚠️ Error: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error",
                    "⚠️ Error: " + e.getMessage());
        }

        dateOfBirthField.setDayCellFactory(getPastOnlyFactory()); //validate date
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

    @FXML
    private void handleDeletePatient() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();

        if (selectedPatient == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an patient to delete.");
            return;
        }

        // Confirm before deleting
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this woman?");

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try(Connection conn = DBConnection.getConnection()){
                PatientRepository patientRepo = new PatientRepository(conn);

                if(patientRepo.checkPatientExists(selectedPatient.getNationalId())){
                    boolean isDeleted = patientRepo.deletePatient(selectedPatient.getId());
                    if(isDeleted){
                        ObservableList<Patient> patientList = FXCollections
                                .observableArrayList(patientRepo.getAllPatients());
                        patientTable.setItems(patientList);
                    }else{
                        showAlert(Alert.AlertType.WARNING, "Error", "Deletion error occured.");
                    }

                }else{
                    showAlert(Alert.AlertType.WARNING, "No Patient", "Patient doesn not exist.");
                }

            }catch(SQLException e) {
                System.out.println("⚠️ Error: " + e.getMessage());
                showAlert(Alert.AlertType.ERROR, "Error",
                        "⚠️ Error: " + e.getMessage());
            }

        }
    }



}