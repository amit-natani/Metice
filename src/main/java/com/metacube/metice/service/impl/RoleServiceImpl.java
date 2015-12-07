package com.metacube.metice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.metice.Entity.Role;
import com.metacube.metice.dao.RoleDao;
import com.metacube.metice.service.RoleService;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	public Role getRoleById(int id) {
		return roleDao.getRoleById(id);
	}

	public void createRole(Role role) {
		roleDao.createRole(role);
	}

	public List<Role> getRoles() {
		return roleDao.getRoles();
	}

	public Role getRoleByName(String roleName) {
		return roleDao.getRoleByName(roleName);
	}


}