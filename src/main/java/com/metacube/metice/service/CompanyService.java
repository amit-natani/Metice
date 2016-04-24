package com.metacube.metice.service;

import java.util.List;

import com.metacube.metice.Entity.Company;

/**
 * @author Amit
 * 
 *         Date Created :
 * 
 *         Description : This class is a Service class for Company to provide
 *         methods to manipulate company Data
 */
public interface CompanyService {

	Company getCompanyById(int id);

	void createCompany(Company company);

	List<Company> getAllCompanies();

	Company getCompanyByName(String name);

	void deleteCompany(Company company);
	
	void updateCompany(Company company);
}
