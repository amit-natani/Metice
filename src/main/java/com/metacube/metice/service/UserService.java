package com.metacube.metice.service;

import java.util.List;

import org.json.JSONObject;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.User;

public interface UserService {

	User getUserById(int id);

	void saveUser(User user);

	void updateUser(User user);

	User getUserByEmail(String email);

	List<User> getAllInvalidUsersByCompany(Company company);

	List<User> getAllValidUsersByCompany(Company company, User user);

	List<User> getAllAdminsByCompany(Company company);

	void deleteUser(User user);

	List<User> getUsersByDOB(Company company);
	
	List<User> getUsersByDOA(Company company);
	
	User updateUserByAdmin(JSONObject jsonObj, User user, User loggedInUser);
	
	User updateUser(JSONObject jsonObject, User user);

}
