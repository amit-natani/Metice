package com.metacube.metice.service;

import java.util.List;

import com.metacube.metice.Entity.Role;

public interface RoleService {

/**
* @param id id of a particular role.
* @return Role object of id in param.
*/
Role getRoleById(int id);

/**
* @param role Role to be created.
*/
void createRole(Role role);

/**
* @return List of all Roles.
*/
List<Role> getRoles();

Role getRoleByName(String roleName);
}