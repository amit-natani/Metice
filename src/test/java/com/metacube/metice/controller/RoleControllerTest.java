package com.metacube.metice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.springframework.web.context.WebApplicationContext;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.Notice;
import com.metacube.metice.Entity.Role;
import com.metacube.metice.Entity.User;
import com.metacube.metice.dao.impl.UserDaoImpl;
import com.metacube.metice.service.CompanyService;
import com.metacube.metice.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext-servlet.xml" })
@WebAppConfiguration
public class RoleControllerTest {

	private MockMvc mockMvc;

	@Autowired
	MockHttpSession session;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CompanyService companyServiceImpl;

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserDaoImpl userDaoImpl;

	Notice notice = new Notice();
	Notice notice1 = new Notice();
	User user = new User();
	Company company = new Company();
	Role role = new Role();

	@Before
	public void setUp() throws java.text.ParseException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(
				this.webApplicationContext).build();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date dob = null, doa = null, postDate = null, expireDate = null;
		try {
			dob = simpleDateFormat.parse("1993-02-08");
			doa = simpleDateFormat.parse("2015-01-12");
			postDate = simpleDateFormat.parse("2015-01-12");
			expireDate = simpleDateFormat.parse("2015-01-12");
			System.out.println("doa" + doa + " dob" + dob);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		role.setName("role1");
		roleService.createRole(role);
		company.setName("CompanyName");
		companyServiceImpl.createCompany(company);
		user.setName("User Name");
		user.setEmail("User.name@metacube.com");
		user.setDob(dob);
		user.setDoa(doa);
		user.setPicture("https://lh4.googleusercontent.com/-N5NiXjGy98Q/AAAAAAAAAAI/AAAAAAAAAB4/GhIWSa3iyR4/photo.jpg");
		user.setValid(true);
		user.setAdmin(true);
		user.setPermissions(1);
		user.setRole(role);
		user.setCompany(company);
	}

	@Test
	@Rollback(true)
	@Transactional
	public void negativeTestGetAllRoles() throws Exception {
		this.mockMvc.perform(
				get("/roles").accept(MediaType.ALL))
				.andExpect(status().isForbidden());
	}

	@Test
	@Rollback(true)
	@Transactional
	public void positiveTestGetAllCompanies() throws Exception {
		session.setAttribute("googleUser", user);
		this.mockMvc.perform(
				get("/roles").accept(MediaType.ALL).session(session))
				.andExpect(status().isOk());
	}
	

}
