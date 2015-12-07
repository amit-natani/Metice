package com.metacube.metice.service;

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
import com.metacube.metice.service.impl.CompanyServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext-servlet.xml"})
public class CompanyServiceImplTest {

	@Autowired
	private CompanyService companyServiceImpl = new CompanyServiceImpl();
	Company company = new Company();
	
	
	@Before
	public void setUp() throws Exception {
		company.setName("CompanyName");
	}

	
	@Test
	@Rollback(true)
	@Transactional
	public void testCreateCompany() {
		companyServiceImpl.createCompany(company);
		assertEquals(company, companyServiceImpl.getCompanyByName("CompanyName"));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetCompanyById() {
		companyServiceImpl.createCompany(company);
		assertEquals(company, companyServiceImpl.getCompanyById(company.getCompanyId()));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetCompanyByName() {
		companyServiceImpl.createCompany(company);
		assertEquals(company, companyServiceImpl.getCompanyByName("CompanyName"));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDeleteCompany() {
		companyServiceImpl.createCompany(company);
		companyServiceImpl.deleteCompany(company);
		assertEquals(null, companyServiceImpl.getCompanyByName("CompanyName"));
	}

	@After
	public void tearDown() throws Exception {
		company = null;
	}
}
