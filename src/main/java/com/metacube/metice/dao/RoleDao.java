package com.metacube.metice.dao;

import java.util.List;

import com.metacube.metice.Entity.Role;
/**
 * This is Interface which have all method declarations which operates on Role Entity
 * 
 * @author Banwari kevat
 */
public interface RoleDao {
	void createRole(Role role);
	List<Role> getRoles();
	/**
	 * This methood for getting role for given id
	 * 
	 * @param id
	 *            : is the unique identity for role
	 * @return object of company
	 */
	Role getRoleById(int roleId);
	Role getRoleByName(String roleName);
}
