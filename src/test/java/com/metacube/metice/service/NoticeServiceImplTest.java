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
import com.metacube.metice.Entity.Role;
import com.metacube.metice.Entity.User;
import com.metacube.metice.service.impl.CompanyServiceImpl;
import com.metacube.metice.service.impl.NoticeServiceImpl;
import com.metacube.metice.service.impl.RoleServiceImpl;
import com.metacube.metice.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext-servlet.xml"})
public class NoticeServiceImplTest {

	@Autowired
	private NoticeService noticeServiceImpl = new NoticeServiceImpl();
	@Autowired
	private UserService userServiceImpl = new UserServiceImpl();
	@Autowired
	private CompanyService companyServiceImpl = new CompanyServiceImpl();
	@Autowired
	private RoleService roleServiceImpl = new RoleServiceImpl();
	Notice notice = new Notice();
	Notice notice1 = new Notice();
	User user = new User();
	Company company=new Company();
	Role role = new Role();
	
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
		
		company.setCompanyId(1);
		company.setName("Company-Name");
		companyServiceImpl.createCompany(company);
		role.setName("Manager");
		roleServiceImpl.createRole(role);
		user.setRole(role);
		user.setUserId(2);
		user.setName("User Name");
		user.setEmail("User.name@metacube.com");
		user.setDob(dob);
		user.setDoa(doa);
		user.setPicture("https://lh4.googleusercontent.com/-N5NiXjGy98Q/AAAAAAAAAAI/AAAAAAAAAB4/GhIWSa3iyR4/photo.jpg");
		user.setValid(true);
		user.setAdmin(true);
		user.setPermissions(3);
		userServiceImpl.saveUser(user);
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

	@Test
	@Rollback(true)
	@Transactional
	public void testSaveNotice() {
		noticeServiceImpl.saveNotice(notice);
		assertEquals(notice.getTitle(), noticeServiceImpl.getNoticeById(notice.getNoticeId()).getTitle());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetNoticeById() {
		noticeServiceImpl.saveNotice(notice);
		assertEquals(notice.getTitle(), noticeServiceImpl.getNoticeById(notice.getNoticeId()).getTitle());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testUpdateNotice() {
		noticeServiceImpl.saveNotice(notice);
		notice.setTitle("Diwali party BOOM");
		noticeServiceImpl.updateNotice(notice);
		assertEquals(notice.getTitle(), noticeServiceImpl.getNoticeById(notice.getNoticeId()).getTitle());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testDeleteNotice() {
		noticeServiceImpl.saveNotice(notice);
		System.out.println("\n size in deletebefore" + noticeServiceImpl.getAllNoticesByCompany(company).size());
		noticeServiceImpl.deleteNotice(notice);
		assertEquals(null, noticeServiceImpl.getNoticeById(notice.getNoticeId()));
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetAllNoticeByCompany() {

		int expected =noticeServiceImpl.getAllNoticesByCompany(company).size();
		noticeServiceImpl.saveNotice(notice);
		int actual = noticeServiceImpl.getAllNoticesByCompany(company).size();
		assertEquals(expected, actual);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetAllNoticeByUser() {
		noticeServiceImpl.saveNotice(notice);
		assertEquals(0, noticeServiceImpl.getAllNoticesByUser(user).size());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetAllNoticeByTag() {
		noticeServiceImpl.saveNotice(notice);
		noticeServiceImpl.saveNotice(notice1);
		assertEquals(0, noticeServiceImpl.getAllNoticesByTag(notice.getTagList(),company).size());
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testGetAllNoticeByTitle() {
		noticeServiceImpl.saveNotice(notice);
		noticeServiceImpl.saveNotice(notice1);
		assertEquals(0, noticeServiceImpl.getAllNoticesByTitle(notice.getTitle(),company).size());
	}
	
	@After
	public void tearDown() {
		notice = null;
		user = null;
		company = null;
	}

}
