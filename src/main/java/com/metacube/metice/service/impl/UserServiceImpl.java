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
import com.metacube.metice.Entity.User;
import com.metacube.metice.dao.UserDao;
import com.metacube.metice.service.CompanyService;
import com.metacube.metice.service.RoleService;
import com.metacube.metice.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private RoleService roleService;

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

	public List<User> getAllInvalidUsersByCompany(Company company) {
		return userDao.getAllInvalidUsersByCompany(company);
	}

	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}

	public List<User> getUsersByDOB(Company company) {
		return userDao.getUsersByDOB(company);
	}

	public List<User> getUsersByDOA(Company company) {
		return userDao.getUsersByDOA(company);
	}

	public List<User> getAllValidUsersByCompany(Company company, User user) {
		return userDao.getAllValidUsersByCompany(company, user);
	}

	public List<User> getAllAdminsByCompany(Company company) {
		return userDao.getAllAdminsByCompany(company);
	}

	public User updateUserByAdmin(JSONObject jsonObj, User loggedInUser, User user2) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateOfBirth = null, dateOfAnniversary = null;
		try {
			String name = jsonObj.getString("name");
			dateOfBirth = formatter.parse((String) jsonObj.get("dob"));
			String doa = jsonObj.getString("doa");
			if( !doa.equals("null")) {
				dateOfAnniversary = formatter
					.parse(doa);
			}
			String roleName = jsonObj.getString("role");
			int permissions = jsonObj.getInt("permissions");
			if(loggedInUser.getUserId() == user2.getUserId()) {
				if(getAllAdminsByCompany(loggedInUser.getCompany()).size() == 1) {
					if(permissions != 3) {
						return null;
					}
				}
			}
			user2.setDoa(dateOfAnniversary);
			user2.setDob(dateOfBirth);
			user2.setName(name);
			user2.setPermissions(permissions);
			user2.setRole(roleService.getRoleByName(roleName));
			user2.setValid(true);
			System.out.println("Hello");
			System.out.println(user2.getName());
			System.out.println(user2.getUserId());
			if(permissions == 3) {
				System.out.println("3");
				user2.setAdmin(true);
			} else {
				System.out.println("21");
				user2.setAdmin(false);
			}
			return user2;
		}
		catch(ParseException e) {
			return null;
		} catch(JSONException e) {
			return null;
		}
	}

	public User updateUser(JSONObject jsonObj, User user) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateOfBirth = null, dateOfAnniversary = null;
		try {
			String name = (String) jsonObj.get("name");
			dateOfBirth = formatter.parse((String) jsonObj.get("dob"));
			String doa = jsonObj.getString("doa");
			if( !doa.equals("null")) {
				dateOfAnniversary = formatter
					.parse(doa);
			}
			if(user.isAdmin()) {
				if(jsonObj.getString("company") != null) {
					int companyId = user.getCompany().getCompanyId();
					Company company = companyService.getCompanyById(companyId);
					company.setName(jsonObj.getString("company"));
				}
			}
			user.setDoa(dateOfAnniversary);
			user.setDob(dateOfBirth);
			user.setName(name);
			return user;
		} catch (ParseException e) {
			return null;
		} catch (JSONException e) {
			return null;
		}
	}
}
