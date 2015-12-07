package com.metacube.metice.dao;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.Notice;
import com.metacube.metice.Entity.User;
import com.metacube.metice.dao.impl.CompanyDaoImpl;
import com.metacube.metice.dao.impl.UserDaoImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext-servlet.xml"})
public class UserDaoImplTest {

	@Autowired
	private UserDaoImpl userDaoImpl;
	@Autowired
	private CompanyDaoImpl companyDaoImpl;
	Notice notice =new Notice();
	User user = new User();
	Company company = new Company();
	
	@Before
	public void setUp() throws Exception {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date dob = null, doa = null;
		try {
			dob = simpleDateFormat.parse("1993-02-08");
			doa = simpleDateFormat.parse("2015-01-12");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		company.setName("Company-Name");
		user.setName("User Name");
		user.setEmail("user.name@metacube.com");
		user.setDob(dob);
		user.setDoa(doa);
		user.setPicture("https://lh4.googleusercontent.com/-N5NiXjGy98Q/AAAAAAAAAAI/AAAAAAAAAB4/GhIWSa3iyR4/photo.jpg");
		user.setValid(true);
		user.setAdmin(true);
		user.setPermissions(0);
	}

	@Test
	@Rollback(true)
	@Transactional
	public void testSaveUser() {
		userDaoImpl.saveUser(user);
		assertEquals(user, userDaoImpl.getUserByEmail("user.name@metacube.com"));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetUserById() {
		userDaoImpl.saveUser(user);
		assertEquals(user, userDaoImpl.getUserById(user.getUserId()));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testUpdateUser() {
		userDaoImpl.saveUser(user);
		user.setName("Amit");
		userDaoImpl.updateUser(user);
		assertEquals(user.getName(), userDaoImpl.getUserById(user.getUserId()).getName());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDeleteUser() {
		userDaoImpl.saveUser(user);
		userDaoImpl.deleteUser(user);
		assertEquals(null, userDaoImpl.getUserById(user.getUserId()));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetUserByEmail() {
		userDaoImpl.saveUser(user);
		assertEquals(user, userDaoImpl.getUserByEmail("user.name@metacube.com"));
	}
	
	@After
	public void tearDown() {
		notice = null;
		user = null;
		company = null;
	}

}
