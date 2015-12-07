package com.metacube.metice.service;

import java.util.List;

import org.json.JSONObject;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.Notice;
import com.metacube.metice.Entity.User;

public interface NoticeService {

	Notice getNoticeById(int id);

	void saveNotice(Notice notice);

	void updateNotice(Notice notice);

	List<Notice> getAllNoticesByCompany(Company company);

	void deleteNotice(Notice notice);

	List<Notice> getAllNoticesByUser(User user);

	List<Notice> getAllArchievedNoticesByCompany(Company company);

	List<Notice> getAllNoticesByTag(String tag, Company company);

	List<Notice> getAllNoticesByTitle(String title, Company company);
	
	Notice saveNotice(JSONObject jsonObj, User user);
}
