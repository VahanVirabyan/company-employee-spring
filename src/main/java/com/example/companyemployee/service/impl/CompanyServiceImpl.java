package com.example.companyemployee.service.impl;

import com.example.companyemployee.entity.Category;
import com.example.companyemployee.entity.Company;
import com.example.companyemployee.repository.CompanyRepository;
import com.example.companyemployee.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company save(Company company) {
        List<Category> categories = company.getCategories();
        List<Category> finalCategories = new ArrayList<>();
        for (Category category : categories) {
            if (category.getId() != 0) {
                finalCategories.add(category);
            }
        }
        company.setCategories(finalCategories);
        return companyRepository.save(company);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public void delete(int companyId) {
        companyRepository.deleteById(companyId);
    }
}
