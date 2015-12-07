package com.metacube.metice.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.Notice;
import com.metacube.metice.Entity.User;
import com.metacube.metice.dao.AbstractDao;
import com.metacube.metice.dao.NoticeDao;

@Repository("noticeDao")
public class NoticeDaoImpl extends AbstractDao<Integer, Notice> implements
		NoticeDao {
	/**
	 * This method for getting notice for given id
	 * 
	 * @param id
	 *            : is the unique identity for notice
	 * @return object of Notice class
	 */
	public Notice getNoticeById(int id) {
		return getByKey(id);
	}

	/**
	 * This method for save a notice
	 * 
	 * @param notice
	 *            : is the object of Notice which have to be save
	 */
	public void saveNotice(Notice notice) {
		saveOrUpdate(notice);
	}

	/**
	 * This method for update a notice
	 * 
	 * @param notice
	 *            : is the object of Notice which have to be update
	 */
	public void updateNotice(Notice notice) {
		update(notice);
	}

	/**
	 * This method for delete a notice
	 * 
	 * @param notice
	 *            : is the object of Notice which have to be delete
	 */
	public void deleteNotice(Notice notice) {
		delete(notice);
	}

	/**
	 * This method for getting all notices concern with given company
	 * 
	 * @param company
	 *            : is the object of company
	 * @return List of notices
	 */
	@SuppressWarnings("unchecked")
	public List<Notice> getAllNoticesByCompany(Company company) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("company", company));
		criteria.add(Restrictions.eq("isArchive", false));
		Calendar cal = Calendar.getInstance();
		criteria.add(Restrictions.gt("expireDate", cal.getTime()));
		criteria.addOrder(Order.asc("postDate"));
		return criteria.list();
	}

	/**
	 * This method for getting all notices posted by given user
	 * 
	 * @param user
	 *            : is the object of user
	 * @return List of notices
	 */
	@SuppressWarnings("unchecked")
	public List<Notice> getAllNoticesByUser(User user) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("postedBy", user));
		criteria.add(Restrictions.eq("isArchive", false));
		Calendar cal = Calendar.getInstance();
		criteria.add(Restrictions.gt("expireDate", cal.getTime()));
		criteria.addOrder(Order.asc("postDate"));
		return criteria.list();
	}

	/**
	 * This method for getting all notices which are concern with given company
	 * and tag
	 * 
	 * @param tag
	 *            : is the tag for notice
	 * @param company
	 *            : is the object of Company
	 * @return list of notices
	 */
	@SuppressWarnings("unchecked")
	public List<Notice> getAllNoticesByTag(String tag, Company company) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("company", company));
		criteria.add(Restrictions.ilike("tagList", tag, MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("isArchive", false));
		Calendar cal = Calendar.getInstance();
		criteria.add(Restrictions.gt("expireDate", cal.getTime()));
		return criteria.list();
	}

	/**
	 * This method for getting all notices which are concern with given company
	 * and title
	 * 
	 * @param title
	 *            : is the tag for notice
	 * @param company
	 *            : is the object of Company
	 * @return list of notices
	 */
	@SuppressWarnings("unchecked")
	public List<Notice> getAllNoticesByTitle(String title, Company company) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("company", company));
		criteria.add(Restrictions.ilike("title", title));
		criteria.add(Restrictions.eq("isArchive", false));
		Calendar cal = Calendar.getInstance();
		criteria.add(Restrictions.gt("expireDate", cal.getTime()));
		return criteria.list();
	}

	/**
	 * This method for getting all notices which are archived for given company
	 * 
	 * @param company
	 *            : is the object of Company
	 * @return list of archived notices
	 */
	@SuppressWarnings("unchecked")
	public List<Notice> getAllArchievedNoticesByCompany(Company company) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("company", company));
		criteria.add(Restrictions.eq("isArchive", true));
		criteria.addOrder(Order.asc("postDate"));
		return criteria.list();
	}
	
	/**
	 * This method for archieve  expired notices
	 */
	/*@SuppressWarnings("unchecked")
	public void archiveExpiredNotices() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("isArchive", false));
		Calendar cal = Calendar.getInstance();
		criteria.add(Restrictions.lt("expireDate", cal.getTime()));
		List<Notice> listOfExpiredNotices = criteria.list();
		for(Notice notice : listOfExpiredNotices)
		{
			 notice.setArchive(true);
			 update(notice);
		}
	}*/
}
