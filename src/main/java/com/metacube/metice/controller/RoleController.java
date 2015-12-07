package com.metacube.metice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.metacube.metice.Entity.Role;
import com.metacube.metice.Entity.User;
import com.metacube.metice.service.RoleService;

/**
 * @author Amit Natani
 * 
 *         Date created :
 *
 *         Description : This RestController is used for all the APIs related to
 *         role management.
 */
@RestController
public class RoleController {

	@Autowired
	RoleService roleService;

	/**
	 * Method to get role by roleId
	 * 
	 * @param id
	 *            : id of role
	 * @return ResponseEntity<Void> with status code if user is not logged in
	 *         then return HttpStatus.FORBIDDEN else if user is not Admin then
	 *         return HttpStatus.UNAUTHORIZED else return Role with
	 *         HttpStatus.OK
	 */
	@RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
	public ResponseEntity<Role> getRoleById(@PathVariable("id") int id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			return new ResponseEntity<Role>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		if (!user.isAdmin()) {
			return new ResponseEntity<Role>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Role>(roleService.getRoleById(id), HttpStatus.OK);
	}

	/**
	 * Method to get all roles
	 * 
	 * @param request
	 *            : object of HttpServletRequest
	 * @return ResponseEntity<List<Role>> with status code if user is not logged
	 *         in then return HttpStatus.FORBIDDEN else if roleList is empty
	 *         then return HttpStatus.No_CONTENT else return roleList with
	 *         HttpStatus.OK
	 */
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public ResponseEntity<List<Role>> getRoles(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ResponseEntity<List<Role>>(HttpStatus.FORBIDDEN);
		}
		List<Role> roleList = roleService.getRoles();
		if (roleList.isEmpty()) {
			return new ResponseEntity<List<Role>>(roleList, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Role>>(roleList, HttpStatus.OK);
	}

	/**
	 * Method to create a role
	 * 
	 * @param roleData : data of role in String(JSON String)
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<Void> with http status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if user is not Admin the return HttpStatus.UNAUTHORIZED
	 * 			else if role already exists then return HttpStatus.CONFLICT
	 * 			else return HttpStatus.OK
	 * @throws JSONException
	 */
	@RequestMapping(value = "/createRole", method = RequestMethod.POST)
	public ResponseEntity<Void> saveRole(@RequestBody String roleData, HttpServletRequest request)
			throws JSONException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		if(roleData == null){
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		JSONObject jsonObj = new JSONObject(roleData);
		User user = (User) session.getAttribute("user");
		if (user.isAdmin()) {
			String roleName = jsonObj.getString("name");
			Role role = roleService.getRoleByName(roleName);
			if (role == null) {
				role = new Role();
				role.setName(roleName);
				roleService.createRole(role);
				return new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
	}
}