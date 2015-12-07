package com.metacube.metice.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.User;
import com.metacube.metice.service.CompanyService;
import com.metacube.metice.service.RoleService;
import com.metacube.metice.service.UserService;

/**
 * @author Amit Natani
 * 
 * Date created : 
 *
 * Description : This RestController is used for all the APIs related to user management.
 */

@RestController
public class UserController {

	/* Object of UserService */
	@Autowired
	UserService userService;

	/* Object of CompanyService */
	@Autowired
	CompanyService companyService;

	/* Object of RoleService */
	@Autowired
	RoleService roleService;


	/** This REST API is getting user details from google and then according to the details
	 * redirects to appropriate pages.
	 * @param request : Object of HttpServletRequest
	 * @param response : Object of HttpServletResponse
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public void verify(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			// Object of HttpSession
			HttpSession session = request.getSession(false);
			if(session != null) {
				User user = (User)session.getAttribute("user");
				if(user != null) {
					if(user.isAdmin()) {
						response.sendRedirect("adminconsole.html");
					}
					else if(user.getPermissions() == 2) {
						response.sendRedirect("manager.html");
					}
					else if(user.getPermissions() == 1 && user.isValid()){
						response.sendRedirect("user.html");
					} else {
						response.sendRedirect("wait.html");
					}
				}
			}

			// code will hold the data returned from google OAuth
			String code = request.getParameter("code");

			/* If code is null i.e. user has clicked on deny button,
			 *  then again redirect to index page */
			if (code == null)
				response.sendRedirect("index.html");

			// format parameters to post
			String urlParameters = "code="
					+ code
					+ "&client_id=977970780268-cq6q6hdc18f22shjpa25sn5v4d6da8a3.apps.googleusercontent.com"
					+ "&client_secret=YvQVn-gGiCge7tTtVn3b-qFZ"
					+ "&redirect_uri=http://localhost:8080/Metice/verify"
					+ "&grant_type=authorization_code";

			// posting parameters
			URL url = new URL("https://accounts.google.com/o/oauth2/token");

			// opening a connection with the given URL 
			URLConnection urlConn = url.openConnection();
			urlConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(
					urlConn.getOutputStream());
			writer.write(urlParameters);
			writer.flush();

			String line, outputString = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));

			// Reading the output in outputString
			while ((line = reader.readLine()) != null) {
				outputString += line;
			}

			// getting Access Token
			JsonObject json = (JsonObject) new JsonParser().parse(outputString);
			String access_token = json.get("access_token").getAsString();

			// getting User Info from that access token
			url = new URL(
					"https://www.googleapis.com/oauth2/v1/userinfo?access_token="
							+ access_token);
			urlConn = url.openConnection();
			outputString = "";
			reader = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));

			// getting user data in output string as JSON
			while ((line = reader.readLine()) != null) {
				outputString += line;
			}

			// Convert JSON response into User class Object
			User user = new Gson().fromJson(outputString, User.class);

			// retrieving email from the created user object
			String email = user.getEmail();

			/* Checking whether the user is already exist in database or not */
			if (userService.getUserByEmail(email) == null) { // If user down not exist, then

				// creating session
				session = request.getSession();

				// setting attribute of session
				session.setAttribute("googleUser", user);

				// redirecting to signup.html
				response.sendRedirect("signup.html");
			} else { // if user already exist, then

				// getting user object from database by using email
				user = userService.getUserByEmail(user.getEmail());
				//creating session
				session = request.getSession();
				// setting user object as session attribute
				session.setAttribute("user", user);

				/* If user is not valid, then redirect himj to wait.html,
				 * otherwise if he is admin, then send him to adminconsole.html,
				 * else if he is manager, redirect him to manager.html
				 * else redirect him to user.html
				 */
				if (!userService.getUserByEmail(email).isValid()) {
					response.sendRedirect("wait.html");
				} else {
					if (userService.getUserByEmail(email).isAdmin()) {
						response.sendRedirect("adminconsole.html");
					} else {
						if(user.getPermissions() == 2)
							response.sendRedirect("manager.html");
						else
							response.sendRedirect("user.html");
					}
				}
			}
		} catch (Exception e) {
			/* In case of any exception redirect again to index.html */
			e.printStackTrace();

		}
	}


	/** Method to save the user details in database
	 * 
	 * @param userData : User data in string format(JSON String)
	 * @param response : Object of HttpServletResponse
	 * @return : ResponseEntity<Void> ( It returns a Http Status code)
	 * @throws JSONException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ResponseEntity<Void> saveUser(@RequestBody String userData,
			HttpServletRequest request)
					throws JSONException, ParseException {

		// getting current session
		HttpSession session = request.getSession(false);

		// if current session is null, then return HttpStatus.FORBIDDEN, else proceeds further
		if (session == null || session.getAttribute("googleUser") == null) {
			System.out.println("There is an error");
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		} else {
			// creating JSON object from the given String data
			JSONObject jsonObj = new JSONObject(userData);
			System.out.println();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date dateOfBirth = null, dateOfAnniversary = null;

			// getting data from JSON obj
			String roleName = jsonObj.getString("role");
			String companyName = jsonObj.getString("company");
			dateOfBirth = formatter.parse(jsonObj.getString("dob"));
			String doa = jsonObj.getString("doa");
			if(!doa.equals("null")) {
				dateOfAnniversary = formatter.parse(doa);
			}
			// getting company object by given company name
			Company company = companyService.getCompanyByName(companyName);
			User user = (User) session.getAttribute("googleUser");

			/* if returned Company object is null, then create a new Company with
			 * given company name and then create user object and make the user admin of that
			 * company. On successful creation send HttpStatus.CREATED, and if there is any problem in creating 
			 * the user object, then send HttpStatus.SERVICE_UNAVAILABLE
			 * if company object is not null then create a new object and set the valid to false
			 * if successfully created, then send HttpStatus.NO_CONTENT, and if not created successfully
			 * then send HttpStatus.SERVICE_UNAVAILABLE
			 */
			if (company == null) {
				company = new Company();
				company.setName(companyName);
				companyService.createCompany(company);
				user.setAdmin(true);
				user.setValid(true);
				user.setDoa(dateOfAnniversary);
				user.setDob(dateOfBirth);
				user.setCompany(company);
				user.setPermissions(3);
				user.setRole(roleService.getRoleByName(roleName));
				userService.saveUser(user);
				if (userService.getUserByEmail(user.getEmail()) == null) {
					session.invalidate();
					companyService.deleteCompany(company);
					return new ResponseEntity<Void>(
							HttpStatus.SERVICE_UNAVAILABLE);
				} else {
					session.setAttribute("googleUser", null);
					return new ResponseEntity<Void>(HttpStatus.CREATED);
				}
			} else {
				user.setAdmin(false);
				user.setValid(false);
				user.setDoa(dateOfAnniversary);
				user.setDob(dateOfBirth);
				user.setCompany(company);
				user.setPermissions(1);
				user.setRole(roleService.getRoleByName(roleName));
				userService.saveUser(user);
				if (userService.getUserByEmail(user.getEmail()) == null) {
					session.invalidate();
					return new ResponseEntity<Void>(
							HttpStatus.SERVICE_UNAVAILABLE);
				} else {
					session.setAttribute("googleUser", null);
					return new ResponseEntity<Void>(
							HttpStatus.NO_CONTENT);
				}
			}
		}
	}

	/** Method to get user info required for signup.html page (Name and email of user
	 * returned from google)
	 * @param request : Object of HttpServletRequest
	 * @return : User object with HttpStatus Code
	 * 			(if user is not logged in, then return HttpStatus.FORBIDDEN,
	 * 			otherwise return user Object with HttpStatus.OK
	 */
	@RequestMapping(value = "/getGoogleUserInfo", method = RequestMethod.GET)
	public ResponseEntity<User> getGoogleUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("googleUser");
		if(user == null) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/** Method to get info of currently logged in user
	 * 
	 * @param request : Object of HttpServletRequest
	 * @return : User object with HttpStatus Code
	 * 			(if user is not logged in, then return HttpStatus.FORBIDDEN,
	 * 			otherwise return user Object with HttpStatus.OK
	 */
	@RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
	public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/** Method to get All user requests of a particular company
	 * 
	 * @param request : object of HttpServletRequest
	 * @param response : object of HttpServletResponse
	 * @return UserList with HttpStatus code,
	 * 					If user is not logged in then, return HttpStatus.FORBIDDEN
	 * 					else if user is not admin then return HttpStatus.UNAUTHORIZED
	 * 					else if list is empty then return HttpStatus.NO_CONTENT
	 * 					else return invalidUserList with HttpStatus.OK
	 */
	@RequestMapping(value = "/getAllInvalidUserRequests", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllInvalidUserRequests(
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ResponseEntity<List<User>>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return new ResponseEntity<List<User>>(HttpStatus.FORBIDDEN);
		}
		if (!(user.isAdmin())) {
			return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
		}
		Company company = user.getCompany();
		List<User> invalidUsersList = userService
				.getAllInvalidUsersByCompany(company);
		if (invalidUsersList.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(invalidUsersList, HttpStatus.OK);
	}

	/** Method to accept a user by Admin
	 * 
	 * @param userData : data of user to be accepted
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<Void>
	 *			if user is not logged in or user is not admin then return HttpStatus.UNAUTHORIZED
	 *			else change the user data according to the data received and returns HttpStatus.OK
	 * @throws JSONException
	 */
	@RequestMapping(value = "/acceptUser", method = RequestMethod.POST)
	public ResponseEntity<Void> acceptUser(@RequestBody String userData,
			@PathVariable("id") int id, HttpServletRequest request) throws JSONException {
		HttpSession session = request.getSession(false);
		JSONObject jsonObj = new JSONObject(userData);
		if (session == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		if (user == null || !(user.isAdmin())) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		} else {
			User newUser = userService.getUserById(jsonObj.getInt("userId"));
			newUser.setValid(true);
			int permissions = jsonObj.getInt("permissions");
			newUser.setPermissions(permissions);
			if(permissions == 3) {
				user.setAdmin(true);
			}
			userService.updateUser(newUser);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	/** Method to decline a user by Admin
	 * 
	 * @param userData : data of user to be accepted
	 * @param id : id of user
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<Void>
	 *			if user is not logged in or user is not admin then return HttpStatus.UNAUTHORIZED
	 *			else if the user to be deleted is last admin of company then return HttpStatus.BAD_REQUEST
	 *			else delete the user and returns HttpStatus.OK
	 * @throws JSONException
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public ResponseEntity<Void> getUserInfo(@RequestBody String email,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		if (user == null || !(user.isAdmin())) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		} else {
			User newUser = userService.getUserByEmail(email);
			List<User> adminList = userService.getAllAdminsByCompany(user.getCompany());
			if(adminList.size() == 1 && newUser.isAdmin()) {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			userService.deleteUser(newUser);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	/** method to get userList who has birthdays on today's date
	 * 
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<List<User>> with Http Status code
	 * 			return HttpStatus.FORBIDDEN if the user is not logged in
	 * 			else if user is not valid then return HttpStatus.UNAUTHORIZED
	 * 			else return userListBirthday with HttpStatus.OK 		
	 */
	@RequestMapping(value = "/getTodayBirthdays", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getTodayBirthdays(
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ResponseEntity<List<User>>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return new ResponseEntity<List<User>>(HttpStatus.FORBIDDEN);
		}
		if (!user.isValid()) {
			return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
		}
		List<User> userListBirthday = userService.getUsersByDOB(user.getCompany());
		if (userListBirthday.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(userListBirthday, HttpStatus.OK);
	}

	/** method to get userList who has anniversary on today's date
	 * 
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<List<User>> with Http Status code
	 * 			return HttpStatus.FORBIDDEN if the user is not logged in
	 * 			else if user is not valid then return HttpStatus.UNAUTHORIZED
	 * 			else return userListBirthday with HttpStatus.OK 		
	 */
	@RequestMapping(value = "/getTodayAnniversaries", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getTodayAnniversaries(
			HttpServletRequest request, HttpServletResponse response)
					throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ResponseEntity<List<User>>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return new ResponseEntity<List<User>>(HttpStatus.FORBIDDEN);
		}
		if (!user.isValid()) {
			return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
		}
		List<User> userListAnniversary = userService.getUsersByDOA(user.getCompany());
		if (userListAnniversary.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(userListAnniversary,
				HttpStatus.OK);
	}

	/** Method to update a user data
	 * 
	 * @param userData : user data in string format(JSON string)
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<Void> with http status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if ParseException Arises then return HttpStatus.BAD_REQUEST
	 * 			else update user data and return HttpStatus.OK
	 * @throws JSONException
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public ResponseEntity<Void> updateUser(@RequestBody String userData,
			HttpServletRequest request) throws JSONException {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		if(userData == null || userData.trim().equals("")) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		JSONObject jsonObj = new JSONObject(userData);
		user = userService.updateUser(jsonObj, user);
		if(user == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		userService.updateUser(user);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	/** method to logout from system
	 * 
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<Void> with http status code
	 * 			return HttpStatus.OK after successful logout
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<Void> logoutUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.setAttribute("user", null);
			session.setAttribute("googleUser", null);
			session.invalidate();
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/** method to get All valid users by Company
	 * 
	 * @param request : object of HttpServletRequest
	 * @return ResponseBody<List<User>> with http status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if user is not admin then return HttpStatus.UNAUTHORIZED
	 * 			else if userList is empty then return HttpStatus.NO_CONTENT
	 * 			else return userList with HttpStatus.OK 
	 */
	@RequestMapping(value = "/getAllValidUsersByCompany", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllValidUsersByCompany(
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ResponseEntity<List<User>>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return new ResponseEntity<List<User>>(HttpStatus.FORBIDDEN);
		}		
		if (!(user.isAdmin())) {
			return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
		}
		Company company = user.getCompany();
		List<User> userList = userService.getAllValidUsersByCompany(company, user);
		if(userList == null) {
			return new ResponseEntity<List<User>>(userList, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}

	/** Method to update a user by company Admin
	 * 
	 * @param userData : user data to be updated
	 * @param id : id of user to be updated
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<Void> with http status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if user is not admin the return HttpStatus.UNAUTHORIZED
	 * 			else if the user does not belongs to the admin's company then return HttpStatus.UNAUTHORIZED
	 * 			else update the user and return HttpStatus.OK
	 * 			In case of any Exception or improper data, return HttpStatus.BAD_REQUEST
	 * @throws JSONException
	 */
	@RequestMapping(value = "/updateUserByAdmin", method = RequestMethod.POST)
	public ResponseEntity<Void> updateUserByAdmin(@RequestBody String userData,
			HttpServletRequest request) throws JSONException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		if(userData == null || userData.trim().equals("")) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		JSONObject jsonObj = new JSONObject(userData);
		
		if (!user.isAdmin()) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		} else {
			User newUser = userService.getUserById(jsonObj.getInt("userId"));
			if(newUser == null) {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
			if(!(newUser.getCompany().getName().equalsIgnoreCase(user.getCompany().getName()))) {
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
			}
			newUser =  userService.updateUserByAdmin(jsonObj, user, newUser);
			if(newUser == null) {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			userService.updateUser(newUser);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
}
