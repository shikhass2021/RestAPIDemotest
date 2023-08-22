package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.models.Employee;

public interface IEmployeeRepo extends JpaRepository<Employee, Long> {

}
