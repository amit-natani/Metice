package com.metacube.metice.Entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This is entity class which maps Role entity into database table role
 * 
 * @name Role
 * @author Banwari Kevat
 **/
@Entity
@Table(name = "user")
public class User {
	/* primary key */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	int userId;

	@Column(name = "name", nullable = false)
	String name;

	@Column(name = "email", nullable = false, unique = true)
	String email;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "role_id")
	Role role;

	@Temporal(TemporalType.DATE)
	@Column(name = "dob", nullable = false)
	Date dob;

	@Temporal(TemporalType.DATE)
	@Column(name = "doa")
	Date doa;

	@Column(name = "profile_pic")
	String picture;

	@Column(name = "valid", nullable = false)
	boolean valid;

	@Column(name = "admin", nullable = false)
	boolean admin;

	@Column(name = "permissions", nullable = false, columnDefinition = "int default 1" )
	int permissions;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "compnay_id")
	Company company;

	/* default constructor */
	public User() {
	}

	/**
	 * Method to get id of user
	 * 
	 * @name getUserId
	 * @return userId : id of user
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Method to set id of user
	 * 
	 * @name setUserId
	 * @return userId : id of user
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Method to get name of user
	 * 
	 * @name getName
	 * @return name : name of user
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set name of user
	 * 
	 * @name setName
	 * @param name
	 *            : name of user
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method to get email id of user
	 * 
	 * @name getEmail
	 * @return email : email id of user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Method to set email id of user
	 * 
	 * @name setEmail
	 * @param email
	 *            : email id of user
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Method to get role of user
	 * 
	 * @name getRole
	 * @return role : role of user
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Method to set role of user
	 * 
	 * @name setRole
	 * @param role
	 *            : role of user
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Method to get date of birth of user
	 * 
	 * @name getDob
	 * @return dob : date of birth of user
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Method to set date of birth of user
	 * 
	 * @name setDob
	 * @param dob
	 *            : date of birth of user
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Method to get date of marriage anniversary of user
	 * 
	 * @name getDoa
	 * @return doa : date of marriage anniversary of user
	 */
	public Date getDoa() {
		return doa;
	}

	/**
	 * Method to set date of marriage anniversary of user
	 * 
	 * @name setDoa
	 * @param doa
	 *            : date of marriage anniversary of user
	 */
	public void setDoa(Date doa) {
		this.doa = doa;
	}

	/**
	 * Method to get url of user picture
	 * 
	 * @name getPicture
	 * @return picture : url of user picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * Method to set url of user picture
	 * 
	 * @name setPicture
	 * @param picture
	 *            : url of user picture
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}

	/**
	 * Method to get status of user in term of valid
	 * 
	 * @name isValid
	 * @return valid : boolean value
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Method to set status of user in term of valid
	 * 
	 * @name setValid
	 * @param valid
	 *            : boolean value
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * Method to get status of user in term of admin
	 * 
	 * @name isAdmin
	 * @return admin : boolean value
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Method to set status of user in term of admin
	 * 
	 * @name setAdmin
	 * @param admin
	 *            : boolean value
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * Method to get permissions of user
	 * 
	 * @name getPermissions
	 * @return permissions : permissions of user
	 */
	public int getPermissions() {
		return permissions;
	}

	/**
	 * Method to set permissions of user
	 * 
	 * @name setPermissions
	 * @param permissions
	 *            : permissions of user
	 */
	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}

	/**
	 * Method to get concern company to user
	 * 
	 * @name getCompany
	 * @param company
	 *            : concern company to user
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Method to set concern company to user
	 * 
	 * @name setCompany
	 * @param company
	 *            : concern company to user
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

}
