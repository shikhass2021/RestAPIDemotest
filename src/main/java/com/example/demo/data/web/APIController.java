package com.example.demo.data.web;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.validation.Valid;

import java.io.*;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import com.example.demo.data.models.Company;
import com.example.demo.data.models.Employee;
import com.example.demo.data.repository.ICompanyRepo;
import com.example.demo.data.repository.IEmployeeRepo;

import com.example.demo.data.NotFoundExceptionn;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class APIController {

    // private List<Employee> employees = new ArrayList<>();

    @Autowired
    ICompanyRepo companyrepo;

    // 1. Write a program to accept RestAPI inputs, run from Postman to add a
    // company with name and any 2 other details.

    @PostMapping("/AddCompanies")

    public Company addCompany(@RequestBody Company company) {
        return companyrepo.save(company);
    }

    @Autowired
    IEmployeeRepo employeerepo;

    // 2. Create an API which accepts 5 fields (mentioned below) and adds them to
    // Employee table/collection.

    @PostMapping("/AddEmp")

    public Employee addEmp(@Valid @RequestBody Employee employee) {
        return employeerepo.save(employee);

    }

    // 3. Create an API to Display all Employees.

    @GetMapping("/GetAllEmployee")
    public List<Employee> getAllEmployees() {
        return employeerepo.findAll();
    }

    // 4. Create an API to Edit an Employee based on Employee ID or Email ID.

    @PutMapping("/UpdateEmp/{id}")
    public ResponseEntity<String> editEmployeeById(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        try {
            if (!employeerepo.findById(id).isPresent()) {
                throw new NotFoundExceptionn("Resource not found for updation");
            }
            Employee existingEmployee = employeerepo.findById(id).orElse(null);
            updateEmployeeFields(existingEmployee, updatedEmployee);
            employeerepo.save(existingEmployee);
            return ResponseEntity.ok("Updated Successfully");
        } catch (NotFoundExceptionn ex) {
            return ResponseEntity.ok(ex.getMessage().toString());

        }
    }

    private void updateEmployeeFields(Employee existingEmployee, Employee updatedEmployee) {
        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setContactNo(updatedEmployee.getContactNo());
        existingEmployee.setEmailid(updatedEmployee.getEmailid());
        existingEmployee.setEmployeeId(updatedEmployee.getEmployeeId());
    }

    // 5. Create an API to delete an Employee from the file/database.

    @DeleteMapping("/DeleteEmp/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            if (!employeerepo.findById(id).isPresent()) {

                throw new NotFoundExceptionn("Resource not found");
            }
            employeerepo.deleteById(id);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        } catch (NotFoundExceptionn ex) {
            return new ResponseEntity<>(ex.getMessage().toString(), HttpStatus.NOT_FOUND);

        }

    }

    // 6. Create an for user to export all user details to excel or PDF(Mandatory
    // Feature)

    @GetMapping("/export/pdf")
    public ResponseEntity<Resource> exportToPDF() throws FileNotFoundException, IOException {
        List<Employee> employees = employeerepo.findAll();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            for (Employee employee : employees) {
                document.add(new Paragraph("First Name: " + employee.getFirstName()));
                document.add(new Paragraph("Last Name: " + employee.getLastName()));
                document.add(new Paragraph("Contact Number: " + employee.getContactNo()));
                document.add(new Paragraph("Email: " + employee.getEmailid()));
                document.add(new Paragraph("\n"));
            }

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        byte[] pdfBytes = outputStream.toByteArray();
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=employee_details.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);

    }

}
