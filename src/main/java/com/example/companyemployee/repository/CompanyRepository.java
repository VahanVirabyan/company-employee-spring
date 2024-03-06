package com.example.companyemployee.repository;

import com.example.companyemployee.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
  Company findCompanyByName(String name);
}