package com.metacube.metice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.metacube.metice.Entity.User;
import com.metacube.metice.service.CompanyService;
import com.metacube.metice.service.impl.CompanyServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext-servlet.xml" })
@WebAppConfiguration
public class CompanyControllerTest {

	private MockMvc mockMvc;

	@Autowired
	MockHttpSession session;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CompanyService companyServiceImpl = new CompanyServiceImpl();

	User user = new User();

	@Before
	public void setUp() throws java.text.ParseException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(
				this.webApplicationContext).build();
		user.setName("User Name");
		user.setEmail("User.name@metacube.com");
		user.setPicture("https://lh4.googleusercontent.com/-N5NiXjGy98Q/AAAAAAAAAAI/AAAAAAAAAB4/GhIWSa3iyR4/photo.jpg");

	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void positiveTestGetAllCompanies() throws Exception {
		session.setAttribute("googleUser", user);
		this.mockMvc.perform(
				get("/getAllCompanies").accept(MediaType.ALL).session(session))
				.andExpect(status().isOk());
	}

	@Test
	@Rollback(true)
	@Transactional
	public void negativeTestGetAllCompanies() throws Exception {
		this.mockMvc.perform(
				get("/getAllCompanies").accept(MediaType.ALL).session(session))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void negativeGoogleUserTestGetAllCompanies() throws Exception {
		
		session.setAttribute("googleUser", null);
		this.mockMvc.perform(
				get("/getAllCompanies").accept(MediaType.ALL).session(session))
				.andExpect(status().isUnauthorized());
	}

	

}
