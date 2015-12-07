package com.metacube.metice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.dao.CompanyDao;
import com.metacube.metice.service.CompanyService;

@Service("companyService")
@Transactional
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	/** method to get company by company Id
	 * @param id : company Id
	 * @return : Company Object associated with that Id 
	 */
	public Company getCompanyById(int id) {
		return companyDao.getCompanyById(id);
	}
	
	/** method to create a Company
	 * @param company : company object to be created
	 */
	public void createCompany(Company company) {
		companyDao.createCompany(company);;
	}

	/** Method to return all companies
	 * @param
	 */
	public List<Company> getAllCompanies() {
		return companyDao.getAllCompanies();
	}

	public Company getCompanyByName(String name) {
		return companyDao.getCompanyByName(name);
	}

	public void deleteCompany(Company company) {
		companyDao.deleteCompany(company);
	}

}
