package com.metacube.metice.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.Notice;
import com.metacube.metice.Entity.User;
import com.metacube.metice.dao.NoticeDao;
import com.metacube.metice.service.NoticeService;

@Service("noticeService")
@Transactional
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	public Notice getNoticeById(int id) {
		return noticeDao.getNoticeById(id);
	}

	public void saveNotice(Notice notice) {
		noticeDao.saveNotice(notice);
	}

	public void updateNotice(Notice notice) {
		/*Notice noticeEntity = noticeDao.getNoticeById(notice.getNoticeId());
		if (noticeEntity != null) {
			noticeEntity.setTitle(notice.getTitle());
			noticeEntity.setContent(notice.getContent());
			noticeEntity.setExpireDate(notice.getExpireDate());
			noticeEntity.setTagList(notice.getTagList());
			noticeEntity.setTitle(notice.getTitle());
		}*/
		noticeDao.updateNotice(notice);
	}

	public List<Notice> getAllNoticesByCompany(Company company) {
		return noticeDao.getAllNoticesByCompany(company);
	}

	public void deleteNotice(Notice notice) {
		noticeDao.deleteNotice(notice);
	}

	public List<Notice> getAllNoticesByUser(User user) {
		return noticeDao.getAllNoticesByUser(user);
	}

	public List<Notice> getAllNoticesByTitle(String title, Company company) {
		return noticeDao.getAllNoticesByTitle(title, company);
	}

	public List<Notice> getAllNoticesByTag(String tag, Company company) {
		return noticeDao.getAllNoticesByTag(tag, company);
	}

	public List<Notice> getAllArchievedNoticesByCompany(Company company) {
		return noticeDao.getAllArchievedNoticesByCompany(company);
	}

	public Notice saveNotice(JSONObject jsonObj, User user) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String title = jsonObj.getString("title");
			String content = jsonObj.getString("content");
			String tagList = jsonObj.getString("tagList");
			System.out.println(title);
			System.out.println(title.length());
			String expireDate = jsonObj.getString("expireDate");
			if(title == null || content == null || tagList == null || expireDate == null) {
				return null;
			}
			Notice notice = new Notice();
			int noticeId;
			Date date = new Date();
			if(!jsonObj.has("noticeId")) {
				notice = new Notice();
				notice.setContent(content);
				notice.setTitle(title);
				notice.setTagList(tagList);
				notice.setExpireDate(formatter.parse(expireDate));
				notice.setLastEditedBy(user);
				notice.setLastEditedDate(date);
				notice.setPostedBy(user);
				notice.setPostDate(date);
				notice.setCompany(user.getCompany());
				return notice;
			}
			else {
				noticeId = jsonObj.getInt("noticeId");
				notice = getNoticeById(noticeId);
				if (!(user.getCompany().getCompanyId() == notice
						.getCompany().getCompanyId())) {
					return null;
				} else {
					notice.setContent(content);
					notice.setTitle(title);
					notice.setTagList(tagList);
					notice.setExpireDate(formatter.parse(expireDate));
					notice.setLastEditedBy(user);
					notice.setLastEditedDate(date);
					updateNotice(notice);
					return notice;
				}
			}
		} catch (ParseException e) {
			return null;
		} catch (JSONException e) {
			return null;
		}
	}

}
