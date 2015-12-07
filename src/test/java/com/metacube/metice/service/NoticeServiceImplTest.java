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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.Notice;
import com.metacube.metice.Entity.User;
import com.metacube.metice.service.impl.NoticeServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext-servlet.xml"})
public class NoticeServiceImplTest {

	@Autowired
	private NoticeService noticeServiceImpl = new NoticeServiceImpl();
	Notice notice =new Notice();
	Notice notice1 =new Notice();
	User user=new User();
	Company company=new Company();
	
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
		user.setUserId(2);
		user.setName("Khemanshu Rao");
		user.setEmail("khemanshu.rao@metacube.com");
		user.setDob(dob);
		user.setDoa(doa);
		user.setPicture("https://lh4.googleusercontent.com/-N5NiXjGy98Q/AAAAAAAAAAI/AAAAAAAAAB4/GhIWSa3iyR4/photo.jpg");
		user.setValid(true);
		user.setAdmin(true);
		user.setPermissions(0);
		notice.setLastEditedDate(postDate);
		notice.setLastEditedBy(user);
		notice.setArchive(false);
		notice.setPostedBy(user);
		notice.setCompany(company);
		notice.setTagList("party");
		notice1.setNoticeId(3);
		notice1.setTitle("Diwali party Boom");
		notice1.setContent("Diwali party on 21 nov");
		notice1.setPostDate(postDate);
		notice1.setExpireDate(expireDate);
		notice1.setPostedBy(user);
		notice1.setCompany(company);
		notice1.setTagList("party");
		notice1.setLastEditedDate(postDate);
		notice1.setLastEditedBy(user);
		notice1.setArchive(false);
	}

	@Test
	public void testSaveNotice() {
		noticeServiceImpl.saveNotice(notice);
		assertEquals(notice.getTitle(), noticeServiceImpl.getNoticeById(notice.getNoticeId()).getTitle());
	}
	
	@Test
	public void testGetNoticeById() {
		noticeServiceImpl.saveNotice(notice);
		assertEquals(notice.getTitle(), noticeServiceImpl.getNoticeById(notice.getNoticeId()).getTitle());
	}
	
	@Test
	public void testUpdateNotice() {
		noticeServiceImpl.saveNotice(notice);
		notice.setTitle("Diwali party BOOM");
		noticeServiceImpl.updateNotice(notice);
		assertEquals(notice.getTitle(), noticeServiceImpl.getNoticeById(notice.getNoticeId()).getTitle());
	}
	
	@Test
	public void testDeleteNotice() {
		noticeServiceImpl.saveNotice(notice);
		System.out.println("\n size in deletebefore" + noticeServiceImpl.getAllNoticesByCompany(company).size());
		noticeServiceImpl.deleteNotice(notice);
		assertEquals(null, noticeServiceImpl.getNoticeById(notice.getNoticeId()));
	}
	
	@Test
	public void testGetAllNoticeByCompany() {

		int expected =noticeServiceImpl.getAllNoticesByCompany(company).size();
		noticeServiceImpl.saveNotice(notice);
		int actual = noticeServiceImpl.getAllNoticesByCompany(company).size();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAllNoticeByUser() {
		noticeServiceImpl.saveNotice(notice);
		assertEquals(0, noticeServiceImpl.getAllNoticesByUser(user).size());
	}
	
	@After
	public void tearDown() {
		notice = null;
		user = null;
		company = null;
	}

}
