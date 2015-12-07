package com.metacube.metice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.User;
/**
 * This is Interface which have all method declarations which operates on User Entity
 * 
 * @author Banwari Kevat
 */
@Repository
public interface UserDao {
	/**
	 * This method for getting user for given id
	 * 
	 * @param id
	 *            : is the unique identity for user
	 * @return object of Notice class
	 */
	User getUserById(int id);
	/**
	 * This method for save a user
	 * 
	 * @param user
	 *            : is the object of User which have to be save
	 */
	void saveUser(User user);
	/**
	 * This method for update a user
	 * 
	 * @param user
	 *            : is the object of User which have to be update
	 */
	void updateUser(User user);
	/**
	 * This method for getting user for given email
	 * @param email : is the email of user
	 * @return User object
	 */
	User getUserByEmail(String email);
	/**
	 * This method for getting invalid user for given company
	 * @param company : is the Object of company
	 * @return list of users
	 */
	List<User> getAllInvalidUsersByCompany(Company company);
	/**
	 * This method for delete a user
	 * 
	 * @param user
	 *            : is the object of User which have to be delete
	 */
	void deleteUser(User user);
	/**
	 * This method for getting all user which have birthday today
	 * @return list of users
	 */
	List<User> getUsersByDOB(Company company);
	/**
	 * This method for getting all user which have marriage anniversary today
	 * @return list of users
	 */
	List<User> getUsersByDOA(Company company);
	/**
	 * This method for getting valid user for given company
	 * @param company : is the Object of company
	 * @return list of users
	 */
	List<User> getAllValidUsersByCompany(Company company, User user);
	/**
	 * This method for getting admin user for given company
	 * @param company : is the Object of company
	 * @return list of users which are admin
	 */
	List<User> getAllAdminsByCompany(Company company);
	
}
