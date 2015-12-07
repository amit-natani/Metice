package com.metacube.metice.dao;

import java.util.List;

import com.metacube.metice.Entity.Company;

/**
 * This is Interface which have all method declarations which operates on Company Entity
 * 
 * @author Banwari kevat
 */
public interface CompanyDao {

	/**
	 * This method for getting company for given id
	 * 
	 * @param id
	 *            : is the unique identity for company
	 * @return object of company
	 */
	Company getCompanyById(int id);

	/**
	 * This method for create a company
	 * 
	 * @param company
	 *            : is the object of company which have to be create
	 * @return List of companies
	 */
	void createCompany(Company company);

	/**
	 * This methood for getting all companies
	 *
	 * @return List of companies
	 */
	List<Company> getAllCompanies();

	/**
	 * This method for getting company for given name
	 * 
	 * @param name
	 *            : is the unique name for company
	 * @return object of company
	 */
	Company getCompanyByName(String name);

	/**
	 * This method for delete a company
	 * 
	 * @param company
	 *            : is the object of company which have to be delete
	 */
	void deleteCompany(Company company);

}
