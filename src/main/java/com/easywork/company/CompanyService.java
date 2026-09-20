package com.easywork.company;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CompanyService {
	
	private final CompanyRepository companyRepository;
	
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public Company createCompany(Company company) {
		return companyRepository.save(company);
		
	}

	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public Company getCompany(Long id) {
		return companyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Company Not Found"));
	}
	
	

}
