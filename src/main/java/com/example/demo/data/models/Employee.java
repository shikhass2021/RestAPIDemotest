package com.example.demo.data.models;

import java.util.ArrayList;
import java.util.Random;

import com.itextpdf.text.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity

@Table(name = "Employees")

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    /// First Name validation

    @NotEmpty(message = "First name cannot be blank")
    @Pattern(regexp = "[A-Za-z.]+", message = "First name must contain only letters and dots")
    private String firstName;

    // Last Name validation

    @NotEmpty(message = "First name cannot be blank")
    @Pattern(regexp = "[a-zA-Z.]+", message = "Last name must contain only letters and dots")
    private String lastName;

    // Email validation

    @NotEmpty(message = "Email ID cannot be blank")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$", message = "Invalid email format")
    private String emailid;

    // Contact Number validation

    // @Size(min = 10, max = 10, message = "Contact number must be exactly 10
    // digits")
    // private String contactNo;
    // @Digits(min = 10, max = 0, message = "Contact number must be exactly 10
    // digits")
    @Min(value = 1000000000, message = "Contact number must be exactly 10 digits")
    @Max(value = 9999999999L, message = "Contact number must be exactly 10 digits")
    private int contactNo;

    // Constructor to generate random Employee ID

    private String employeeId;

    public Employee() {
        this.employeeId = generateRandomEmployeeID();
    }

    // Method to generate a random Employee ID

    private String generateRandomEmployeeID() {
        Random random = new Random();
        int randomID = random.nextInt(100) + 1; // Generates a random number between 1 and 100
        return "e" + randomID;
    }

    // Getter and Setter methods for attributes

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailid() {
        return this.emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public int getContactNo() {
        return this.contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

}
