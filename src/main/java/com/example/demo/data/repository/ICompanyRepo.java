package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.models.Company;

public interface ICompanyRepo extends JpaRepository<Company, Long> {

}
