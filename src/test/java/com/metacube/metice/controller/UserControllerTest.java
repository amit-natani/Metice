package com.metacube.metice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.event.spi.PostCollectionRecreateEvent;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.User;
import com.metacube.metice.dao.impl.CompanyDaoImpl;
import com.metacube.metice.dao.impl.UserDaoImpl;
import com.metacube.metice.service.CompanyService;
import com.metacube.metice.service.impl.CompanyServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext-servlet.xml" })
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	MockHttpSession session;

	@Autowired
	private CompanyDaoImpl companyDaoImpl;

	@Autowired
	private CompanyService companyServiceImpl = new CompanyServiceImpl();

	@Autowired
	private UserDaoImpl userDaoImpl;

	User user = new User();
	User inValidUser = new User();
	Company company = new Company();
	String userJson = null;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	@Rollback(true)
	@Transactional
	public void setUp() throws java.text.ParseException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(
				this.webApplicationContext).build();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date dob = null, doa = null;
		try {
			dob = simpleDateFormat.parse("1993-07-12");
			doa = simpleDateFormat.parse("2015-01-12");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		user.setName("User Name");
		user.setEmail("User.name@metacube.com");
		user.setDob(dob);
		user.setDoa(doa);
		user.setPicture("https://lh4.googleusercontent.com/-N5NiXjGy98Q/AAAAAAAAAAI/AAAAAAAAAB4/GhIWSa3iyR4/photo.jpg");
		user.setValid(true);
		user.setAdmin(true);
		user.setPermissions(1);
		company.setName("CompanyName");
		companyServiceImpl.createCompany(company);
		user.setCompany(company);
		inValidUser.setName("Invalid User");
		inValidUser.setEmail("invalid.user@metacube.com");
		inValidUser.setDob(dob);
		inValidUser.setDoa(doa);
		inValidUser.setPicture("https://lh4.googleusercontent.com/-N5NiXjGy98Q/AAAAAAAAAAI/AAAAAAAAAB4/GhIWSa3iyR4/photo.jpg");
		inValidUser.setValid(false);
		inValidUser.setAdmin(false);
		inValidUser.setPermissions(1);
		inValidUser.setCompany(company);
		userJson = "{'userId':8,'name',:'Girdhari Agrawal','email':'girdhari258@gmail.com','role':{'roleId':7,'name':'Project Manager'},'dob':'1994-12-07','doa':'2017-02-22','picture':'https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg','valid':true,'admin':true,'permissions':3,'company':{'companyId':3,'name':'Apperio'}}"; 
		

	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void positiveTestGetGoogleUserInfo() throws Exception {
		session.setAttribute("googleUser", user);
		this.mockMvc.perform(
				get("/getGoogleUserInfo").accept(MediaType.ALL)
						.session(session)).andExpect(status().isOk());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeTestGetGoogleUserInfo() throws Exception {
		this.mockMvc.perform(
				get("/getGoogleUserInfo").accept(MediaType.ALL)
						.session(session)).andExpect(status().isForbidden());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeSessionNullTestGetGoogleUserInfo() throws Exception {
		this.mockMvc.perform(
				get("/getGoogleUserInfo").accept(MediaType.ALL))
						.andExpect(status().isForbidden());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void positiveTestGetCurrentUserInfo() throws Exception {
		session.setAttribute("user", user);
		this.mockMvc.perform(
				get("/getCurrentUser").accept(MediaType.ALL)
						.session(session)).andExpect(status().isOk());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeTestGetCurrentUserInfo() throws Exception {
		this.mockMvc.perform(
				get("/getCurrentUser").accept(MediaType.ALL)
						.session(session)).andExpect(status().isForbidden());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeSessionNullTestGetCurrentUserInfo() throws Exception {
		this.mockMvc.perform(
				get("/getCurrentUser").accept(MediaType.ALL))
						.andExpect(status().isForbidden());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void positiveTestGetAllInvalidUserRequests() throws Exception {

		userDaoImpl.saveUser(inValidUser);
		session.setAttribute("user", user);
		this.mockMvc.perform(
				get("/getAllInvalidUserRequests").accept(MediaType.ALL)
						.session(session)).andExpect(status().isOk());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeTestGetAllInvalidUserRequests() throws Exception {

		session.setAttribute("user", user);
		this.mockMvc.perform(
				get("/getAllInvalidUserRequests").accept(MediaType.ALL)
						.session(session)).andExpect(status().isNoContent());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeSessionNullTestGetAllInvalidUserRequests() throws Exception {
		this.mockMvc.perform(
				get("/getAllInvalidUserRequests").accept(MediaType.ALL))
						.andExpect(status().isForbidden());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void positiveTestGetTodaysBirthday() throws Exception {
		session.setAttribute("user", user);
		this.mockMvc.perform(
				get("/getTodayBirthdays").accept(MediaType.ALL)
						.session(session)).andExpect(status().isNoContent());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeTestGetTodaysBirthday() throws Exception {
		this.mockMvc.perform(
				get("/getTodayBirthdays").accept(MediaType.ALL)
						.session(session)).andExpect(status().isForbidden());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeSessionNullTestGetTodaysBirthday() throws Exception {
		this.mockMvc.perform(
				get("/getTodayBirthdays").accept(MediaType.ALL))
						.andExpect(status().isForbidden());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void positiveTestGetTodayAnniversaries() throws Exception {
		session.setAttribute("user", user);
		this.mockMvc.perform(
				get("/getTodayAnniversaries").accept(MediaType.ALL)
						.session(session)).andExpect(status().isNoContent());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeTestGetTodayAnniversaries() throws Exception {
		this.mockMvc.perform(
				get("/getTodayAnniversaries").accept(MediaType.ALL)
						.session(session)).andExpect(status().isForbidden());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeSessionNullTestGetTodayAnniversaries() throws Exception {
		this.mockMvc.perform(
				get("/getTodayAnniversaries").accept(MediaType.ALL))
						.andExpect(status().isForbidden());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void positiveTestLogout() throws Exception {
		session.setAttribute("user", user);
		this.mockMvc.perform(
				get("/logout").accept(MediaType.ALL)
						.session(session)).andExpect(status().isOk());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void positiveTestGetAllValidUserRequests() throws Exception {
		session.setAttribute("user", user);
		this.mockMvc.perform(
				get("/getAllValidUsersByCompany").accept(MediaType.ALL)
						.session(session)).andExpect(status().isOk());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeTestGetAllValidUserRequests() throws Exception {
		this.mockMvc.perform(
				get("/getAllValidUsersByCompany").accept(MediaType.ALL)
						.session(session)).andExpect(status().isForbidden());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeSessionNullTestGetAllValidUserRequests() throws Exception {
		this.mockMvc.perform(
				get("/getAllValidUsersByCompany").accept(MediaType.ALL))
						.andExpect(status().isForbidden());
	}
	
}
