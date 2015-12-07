package com.metacube.metice.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.Notice;
import com.metacube.metice.Entity.User;
import com.metacube.metice.dao.AbstractDao;
import com.metacube.metice.dao.CompanyDao;
import com.metacube.metice.dao.NoticeDao;
import com.metacube.metice.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	@Autowired
	SessionFactory sessionfactory;
	@Autowired
	CompanyDao companyDao;
	@Autowired
	NoticeDao noticeDao;

	/**
	 * This method for getting user for given id
	 * 
	 * @param id
	 *            : is the unique identity for user
	 * @return object of Notice class
	 */
	public User getUserById(int id) {
		return getByKey(id);
	}

	/**
	 * This method for save a user
	 * 
	 * @param user
	 *            : is the object of User which have to be save
	 */
	public void saveUser(User user) {
		saveOrUpdate(user);
	}

	/**
	 * This method for update a user
	 * 
	 * @param user
	 *            : is the object of User which have to be update
	 */
	public void updateUser(User user) {
		update(user);
	}

	/**
	 * This method for delete a user , while deleting first of all notices of
	 * this user will assign to admin of user company
	 * 
	 * @param user
	 *            : is the object of User which have to be delete
	 */
	public void deleteUser(User user) {
		/* Getting all notices of user which have to be delete*/
		List<Notice> userNoticesList = noticeDao.getAllNoticesByUser(user);
		if (!userNoticesList.isEmpty()) {
			/*if notices list is not empty then assign to admin*/
			User admin = getAllAdminsByCompany(user.getCompany()).get(0);
			for (Notice notice : userNoticesList) {
				/*set admin to this notice*/
				notice.setPostedBy(admin);
				/*update notice*/
				noticeDao.updateNotice(notice);
			}
		}
		delete(user);
	}

	/**
	 * This method for getting user for given email
	 * 
	 * @param email
	 *            : is the email of user
	 * @return User object
	 */
	public User getUserByEmail(String email) {
		/*getting criteria of User entity*/
		Criteria criteria = createEntityCriteria();
		/*Adding restriction for email*/
		criteria.add(Restrictions.eq("email", email));
	    /*Email is unique then it will return unique result*/
		return (User) criteria.uniqueResult();
	}

	/**
	 * This method for getting invalid user for given company
	 * 
	 * @param company
	 *            : is the Object of company
	 * @return list of users
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllInvalidUsersByCompany(Company company) {
		/*getting criteria of User entity*/
		Criteria criteria = createEntityCriteria();
		/*Adding restriction for company*/
		criteria.add(Restrictions.eq("company", company));
		/*Adding restriction for valid*/
		criteria.add(Restrictions.eq("valid", false));
		/*It will return list of user*/
		return criteria.list();
	}

	/**
	 * This method for getting all user which have birthday today
	 * 
	 * @return list of users
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUsersByDOB(Company company) {
		IntegerType type = new IntegerType();
		Calendar cal = Calendar.getInstance();
		/*Creating detached criteria for SQL restriction*/
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(User.class);
		
		detachedCriteria.add(Restrictions.sqlRestriction("Day(dob) = ?",
				cal.get(Calendar.DAY_OF_MONTH), type));
		
		detachedCriteria.add(Restrictions.sqlRestriction("Month(dob) = ?",
				cal.get(Calendar.MONTH) + 1, type));
		
		Criteria criteria = detachedCriteria
				.getExecutableCriteria(sessionfactory.getCurrentSession());
		/*adding restriction for company*/
		criteria.add(Restrictions.eq("company", company));
		List<User> users = criteria.list();
		return users;
	}

	/**
	 * This method for getting all user which have marriage anniversary today
	 * 
	 * @return list of users
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUsersByDOA(Company company) {
		IntegerType type = new IntegerType();
		Calendar cal = Calendar.getInstance();
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(User.class);
		detachedCriteria.add(Restrictions.sqlRestriction("Day(doa) = ?",
				cal.get(Calendar.DAY_OF_MONTH), type));
		detachedCriteria.add(Restrictions.sqlRestriction("Month(doa) = ?",
				cal.get(Calendar.MONTH) + 1, type));

		Criteria criteria = detachedCriteria
				.getExecutableCriteria(sessionfactory.getCurrentSession());
		criteria.add(Restrictions.eq("company", company));
		List<User> users = criteria.list();
		return users;
	}

	/**
	 * This method for getting valid user for given company
	 * 
	 * @param company
	 *            : is the Object of company
	 * @return list of users
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllValidUsersByCompany(Company company, User user) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("company", company));
		criteria.add(Restrictions.ne("userId", user.getUserId()));
		criteria.add(Restrictions.eq("valid", true));
		return criteria.list();
	}

	/**
	 * This method for getting admin user for given company
	 * 
	 * @param company
	 *            : is the Object of company
	 * @return list of users which are admin
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllAdminsByCompany(Company company) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("company", company));
		criteria.add(Restrictions.eq("admin", true));
		return criteria.list();
	}
}
