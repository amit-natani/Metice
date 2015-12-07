package com.metacube.metice.dao;

import java.util.List;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.Notice;
import com.metacube.metice.Entity.User;
/**
 * This is Interface which have all method declarations which operates on Notice Entity
 * 
 * @author Banwari kevat
 */
public interface NoticeDao {
	/**
	 * This method for getting notice for given id
	 * 
	 * @param id
	 *            : is the unique identity for notice
	 * @return object of Notice class
	 */
	public Notice getNoticeById(int id);

	/**
	 * This method for save a notice
	 * 
	 * @param notice
	 *            : is the object of Notice which have to be save
	 */
	public void saveNotice(Notice notice);

	/**
	 * This method for update a notice
	 * 
	 * @param notice
	 *            : is the object of Notice which have to be update
	 */
	public void updateNotice(Notice notice);

	/**
	 * This method for delete a notice
	 * 
	 * @param notice
	 *            : is the object of Notice which have to be delete
	 */
	public void deleteNotice(Notice notice);

	/**
	 * This method for getting all notices concern with given company
	 * 
	 * @param company
	 *            : is the object of company
	 * @return List of notices
	 */
	public List<Notice> getAllNoticesByCompany(Company company);

	/**
	 * This method for getting all notices posted by given user
	 * 
	 * @param user
	 *            : is the object of user
	 * @return List of notices
	 */
	public List<Notice> getAllNoticesByUser(User user);

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
	public List<Notice> getAllNoticesByTag(String tag, Company company);

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
	public List<Notice> getAllNoticesByTitle(String title, Company company);

	/**
	 * This method for getting all notices which are archived for given company
	 * 
	 * @param company
	 *            : is the object of Company
	 * @return list of archived notices
	 */
	List<Notice> getAllArchievedNoticesByCompany(Company company);
	
	
	/**
	 * This method for archive expired notices
	 */
	//public void archiveExpiredNotices();
}
