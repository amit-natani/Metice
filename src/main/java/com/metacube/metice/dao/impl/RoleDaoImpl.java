package com.metacube.metice.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.metacube.metice.Entity.Role;
import com.metacube.metice.dao.AbstractDao;
import com.metacube.metice.dao.RoleDao;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements RoleDao {

	/**
	 * This method for create role
	 * @param  role: role is the object of Role
	 */
	public void createRole(Role role) {
		persist(role);
	}
	/**
	 * This method for getting all roles
	 * @return  list of roles
	 */
	@SuppressWarnings("unchecked")
	public List<Role> getRoles() {
		Criteria criteria = createEntityCriteria();
		List<Role> roleList = criteria.list();
		return roleList;
	}
	/**
	 * This method for get role for given id
	 * @param roleId: is the unique identity for role
	 * @return object of Role
	 */
	public Role getRoleById(int roleId) {
		return getByKey(roleId);
	}
	/**
	 * This method for get role for given name
	 * @param roleName: is the unique name for role
	 * @return object of Role
	 */
	public Role getRoleByName(String roleName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", roleName));
		return (Role) criteria.uniqueResult();
	}
}
