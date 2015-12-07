package com.metacube.metice.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is entity class which maps Company entity into database table company
 * 
 * @name Company
 * @author Banwari Kevat
 */
@Entity
@Table(name = "company")
public class Company {
	/* primary key */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	int companyId;
	
	@Column(name = "name", unique = true, nullable = false)
	String name;

	/* default constructor */
	public Company() {
	}

	/**
	 * Method to get id of company
	 * 
	 * @name getCompanyId
	 * @return companyId : id of company
	 */
	public int getCompanyId() {
		return companyId;
	}

	/**
	 * Method to set id of company
	 * 
	 * @name setCompanyId
	 * @param companyId
	 *            : id of company
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	/**
	 * Method to get name of company
	 * 
	 * @name getName
	 * @return name : name of company
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set name of company
	 * 
	 * @name setName
	 * @param name
	 *            : name of company
	 */
	public void setName(String name) {
		this.name = name;
	}

}
