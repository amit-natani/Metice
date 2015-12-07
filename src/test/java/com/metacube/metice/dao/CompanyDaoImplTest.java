package com.metacube.metice.dao;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.dao.impl.CompanyDaoImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext-servlet.xml"})
public class CompanyDaoImplTest {

	@Autowired
	private CompanyDaoImpl companyDaoImpl;
	Company company = new Company();
	
	
	@Before
	public void setUp() throws Exception {
		company.setName("Company Name pvt. ltd.");
	}

	
	@Test
	@Rollback(true)
	@Transactional
	public void testCreateCompany() {
		companyDaoImpl.createCompany(company);
		assertEquals(company, companyDaoImpl.getCompanyByName("Company Name pvt. ltd."));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetCompanyById() {
		companyDaoImpl.createCompany(company);
		assertEquals(company, companyDaoImpl.getCompanyById(company.getCompanyId()));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetCompanyByName() {
		companyDaoImpl.createCompany(company);
		assertEquals(company, companyDaoImpl.getCompanyByName("Company Name pvt. ltd."));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDeleteCompany() {
		companyDaoImpl.createCompany(company);
		companyDaoImpl.deleteCompany(company);
		assertEquals(null, companyDaoImpl.getCompanyByName("Company Name pvt. ltd."));
	}

	@After
	public void tearDown() throws Exception {
		company = null;
	}
}
