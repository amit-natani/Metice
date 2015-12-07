package com.metacube.metice.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is entity class which maps Role entity into database table Role
 * 
 * @name Role
 * @author Banwari Kevat
 **/
@Entity
@Table(name = "role")
public class Role {
	/* primary key */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	int roleId;
	
	@Column(name = "name", unique = true, nullable = false)
	String name;

	/* default constructor */
	public Role() {
	}

	/**
	 * Method to get id of role
	 * 
	 * @name getRoleId
	 * @return roleId : id of role
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * Method to set id of role
	 * 
	 * @name setRoleId
	 * @param roleId
	 *            : id of role
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * Method to get name of role
	 * 
	 * @name getName
	 * @return name : name of role
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set name of role
	 * 
	 * @name setName
	 * @param name
	 *            : name of role
	 */
	public void setName(String name) {
		this.name = name;
	}

}
