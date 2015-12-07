package com.metacube.metice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
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
import com.metacube.metice.dao.impl.CompanyDaoImpl;
import com.metacube.metice.dao.impl.NoticeDaoImpl;
import com.metacube.metice.dao.impl.RoleDaoImpl;
import com.metacube.metice.dao.impl.UserDaoImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext-servlet.xml" })
@WebAppConfiguration
public class NoticeControllerTest {

	private MockMvc mockMvc;

	@Autowired
	MockHttpSession session;

	@Autowired
	private CompanyDaoImpl companyDaoImpl;

	@Autowired
	private NoticeDaoImpl noticeDaoImpl;

	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Autowired
	private RoleDaoImpl roleDaoImpl;

	Notice notice = new Notice();
	Notice notice1 = new Notice();
	User user = new User();
	Company company = new Company();
	Role role = new Role();

	@Autowired
	private WebApplicationContext webApplicationContext;

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
		} catch (ParseException e) {
			e.printStackTrace();
		}

		company.setCompanyId(1);
		company.setName("Company-Name");
		companyDaoImpl.createCompany(company);
		role.setName("Manager");
		roleDaoImpl.createRole(role);
		user.setRole(role);
		user.setUserId(2);
		user.setName("User Name");
		user.setEmail("User.name@metacube.com");
		user.setDob(dob);
		user.setDoa(doa);
		user.setPicture("https://lh4.googleusercontent.com/-N5NiXjGy98Q/AAAAAAAAAAI/AAAAAAAAAB4/GhIWSa3iyR4/photo.jpg");
		user.setValid(true);
		user.setAdmin(true);
		user.setCompany(company);
		user.setPermissions(3);
		userDaoImpl.saveUser(user);
		notice.setTitle("Diwali party");
		notice.setContent("Diwali party on 21 nov");
		notice.setPostDate(postDate);
		notice.setLastEditedDate(postDate);
		notice.setExpireDate(expireDate);
		notice.setPostedBy(user);
		notice.setLastEditedBy(user);
		notice.setCompany(company);
		notice.setTagList("party");
		notice1.setTitle("Diwali party");
		notice1.setContent("Diwali party on 21 nov");
		notice1.setPostDate(postDate);
		notice1.setExpireDate(expireDate);
		notice1.setPostedBy(user);
		notice1.setLastEditedBy(user);
		notice1.setLastEditedDate(postDate);
		notice1.setCompany(company);
		notice1.setTagList("party");
		

	}

	// Positive Test Case

	@Test
	@Rollback(true)
	@Transactional
	public void positiveTestGetAllNoticesByCompany() throws Exception {
		session.setAttribute("user", user);
		this.mockMvc.perform(
				get("/getAllNoticesByCompany").accept(MediaType.TEXT_HTML)
						.session(session)).andExpect(status().isNoContent());
	}

	// Negative Test Case
	@Test
	@Rollback(true)
	@Transactional
	public void negativeTestGetAllNoticesByCompany() throws Exception {
		this.mockMvc.perform(
				get("/getAllNoticesByCompany").accept(MediaType.TEXT_HTML)
						.session(session)).andExpect(status().isForbidden());
	}

	@Test
	@Rollback(true)
	@Transactional
	public void positiveTestGetAllArchivedNoticesByCompany() throws Exception {
		session.setAttribute("user", user);
		this.mockMvc.perform(
				get("/getAllArchivedNoticesByCompany").accept(
						MediaType.TEXT_HTML).session(session)).andExpect(
				status().isNoContent());
	}

	@Test
	@Rollback(true)
	@Transactional
	public void negativeTestGetAllArchivedNoticesByCompany() throws Exception {
		this.mockMvc.perform(
				get("/getAllArchivedNoticesByCompany").accept(
						MediaType.TEXT_HTML).session(session)).andExpect(
				status().isForbidden());
	}

	/*
	 * @Test public void saveNotice() { String content =
	 * "{'title':'sdfgsadg','expiryDate':'20/01/2017','tagList':'fsdpagoi,safdopdg','content':'<p>fsadgdgsdgsg</p>'}"
	 * ;
	 * 
	 * this.mockMvc.perform(post("/saveNotice")
	 * .contentType(MediaType.APPLICATION_JSON) .param("noticeData",content)
	 * .sessionAttr("todo", new Notice())) .andExpect(status().isOk()); }
	 */
	
	@After
	public void tearDown() {
		notice = null;
		notice1 = null;
		user = null;
		company = null;
	}

}
