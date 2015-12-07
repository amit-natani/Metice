/**
 *This is the CompanyDaoImpl class for database CRUD operation on Company
 *@Author banwari kevat 
 */
package com.metacube.metice.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.dao.AbstractDao;
import com.metacube.metice.dao.CompanyDao;

@Repository("companyDao")
public class CompanyDaoImpl extends AbstractDao<Integer, Company> implements
		CompanyDao {
	/**
	 * This method for getting company for given id
	 * 
	 * @param id
	 *            : is the unique identity for company
	 * @return object of company
	 */

	public Company getCompanyById(int id) {
		return getByKey(id);
	}

	/**
	 * This method for create a company
	 * 
	 * @param company
	 *            : is the object of company which have to be create
	 * @return List of companies
	 */
	public void createCompany(Company company) {
		saveOrUpdate(company);
	}

	/**
	 * This method for getting all companies
	 *
	 * @return List of companies
	 */
	@SuppressWarnings("unchecked")
	public List<Company> getAllCompanies() {
		Criteria criteria = createEntityCriteria();
		List<Company> companyList = criteria.list();
		System.out.println(companyList.size());
		Iterator<Company> itr = companyList.iterator();
		while (itr.hasNext()) {
			Company company = itr.next();
			System.out.println(company);
		}
		return companyList;
	}

	/**
	 * This method for getting company for given name
	 * 
	 * @param name
	 *            : is the unique name for company
	 * @return object of company
	 */
	public Company getCompanyByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (Company) criteria.uniqueResult();
	}

	/**
	 * This method for delete a company
	 * 
	 * @param company
	 *            : is the object of company which have to be delete
	 */
	public void deleteCompany(Company company) {
		delete(company);
	}

}
