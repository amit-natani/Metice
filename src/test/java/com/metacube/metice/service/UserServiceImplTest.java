package com.metacube.metice.service;

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
import com.metacube.metice.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext-servlet.xml"})
public class UserServiceImplTest {

	@Autowired
	private UserService userServiceImpl = new UserServiceImpl();
	Notice notice =new Notice();
	User user = new User();
	Company company = new Company();
	
	@Before
	public void setUp() throws Exception {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-mm");
		Date dob = null, doa = null, postDate = null, expireDate = null;
		try {
			dob = simpleDateFormat.parse("1993-02-08");
			doa = simpleDateFormat.parse("2015-01-12");
			postDate = simpleDateFormat.parse("2015-01-12");
			expireDate = simpleDateFormat.parse("2015-01-12");
			System.out.println("doa"+doa +" dob"+ dob);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		notice.setTitle("Diwali party");
		notice.setContent("Diwali party on 21 nov");
		notice.setPostDate(postDate);
		notice.setExpireDate(expireDate);
		company.setCompanyId(1);
		company.setName("metacube");
		user.setName("Amit Natani");
		user.setEmail("amit.natani@metacube.com");
		user.setDob(dob);
		user.setDoa(doa);
		user.setPicture("https://lh4.googleusercontent.com/-N5NiXjGy98Q/AAAAAAAAAAI/AAAAAAAAAB4/GhIWSa3iyR4/photo.jpg");
		user.setValid(true);
		user.setAdmin(true);
		user.setPermissions(0);
		notice.setPostedBy(user);
		notice.setCompany(company);
		notice.setTagList("party");
		company.setCompanyId(1);
		company.setName("metacube");
	}

	@Test
	@Rollback(true)
	@Transactional
	public void testSaveUser() {
	userServiceImpl.saveUser(user);
		assertEquals(user, userServiceImpl.getUserByEmail("amit.natani@metacube.com"));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetUserById() {
	userServiceImpl.saveUser(user);
		assertEquals(user, userServiceImpl.getUserById(user.getUserId()));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testUpdateUser() {
	userServiceImpl.saveUser(user);
		user.setName("Amit");
	userServiceImpl.updateUser(user);
		assertEquals(user.getName(), userServiceImpl.getUserById(user.getUserId()).getName());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDeleteUser() {
	userServiceImpl.saveUser(user);
	userServiceImpl.deleteUser(user);
		assertEquals(null, userServiceImpl.getUserById(user.getUserId()));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetUserByEmail() {
	userServiceImpl.saveUser(user);
		assertEquals(user, userServiceImpl.getUserByEmail("amit.natani@metacube.com"));
	}
	
	@After
	public void tearDown() {
		notice = null;
		user = null;
		company = null;
	}

}
