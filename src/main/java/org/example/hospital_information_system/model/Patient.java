package org.example.hospital_information_system.model;

import java.time.LocalDate;

public class Patient {
    private int id;
    private String firstName;
    private String nationalId;
    private String surname;
    private String telephoneNumber;
    private char sex;
    private LocalDate dateOfBirth;

    public Patient(String nationalId, String firstName, String surname,
                   String telephoneNumber, char sex, LocalDate dateOfBirth){
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.surname = surname;
        this.telephoneNumber = telephoneNumber;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
    }

    public Patient(int id, String nationalId, String firstName, String surname,
                   String telephoneNumber, char sex, LocalDate dateOfBirth){
        this.id = id;
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.surname = surname;
        this.telephoneNumber = telephoneNumber;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
}
