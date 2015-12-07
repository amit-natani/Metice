package com.metacube.metice.controller;

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

import com.metacube.metice.Entity.Company;
import com.metacube.metice.Entity.Notice;
import com.metacube.metice.Entity.User;
import com.metacube.metice.service.CompanyService;
import com.metacube.metice.service.NoticeService;
/**
 * @author Amit Natani
 * 
 *         Date created :
 *
 *         Description : This RestController is used for all the APIs related to
 *         Notice management.
 */
@RestController
public class NoticeController {

	@Autowired
	NoticeService noticeService;

	@Autowired
	CompanyService companyService;

	/** Method to get all notices of a company
	 * 
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<List<notice>> with status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if there are no notices of that company then return HttpStatus.No_CONTENT
	 * 			else return noticeList with HttpStatus.OK
	 */
	@RequestMapping(value = "/getAllNoticesByCompany", method = RequestMethod.GET)
	public ResponseEntity<List<Notice>> getAllNoticeByCompany(
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			return new ResponseEntity<List<Notice>>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		Company company = user.getCompany();
		List<Notice> noticeList = noticeService.getAllNoticesByCompany(company);
		if (noticeList.isEmpty()) {
			return new ResponseEntity<List<Notice>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Notice>>(noticeList, HttpStatus.OK);
	}

	/** Method to save a notice
	 * 
	 * @param noticeData : notice data in string format(JSON String)
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<Void> with status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if user is not valid or user is not admin or manager then return HttpStatus.UNAUTHORIZED
	 * 			else save the notice and return HttpStatus.OK
	 * 			in case of ParseException, return HttpStatus.BAD_REQUEST
	 * @throws JSONException
	 */
	@RequestMapping(value = "/saveNotice", method = RequestMethod.POST)
	public ResponseEntity<Void> saveNotice(@RequestBody String noticeData,
			HttpServletRequest request) throws JSONException {
		HttpSession session = request.getSession(false);
		System.out.println("Hello"+noticeData);
		JSONObject jsonObj = new JSONObject(noticeData);
		if (session == null || session.getAttribute("user") == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		if (!user.isValid() || user.getPermissions() == 1) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		} else {
			Notice notice = noticeService.saveNotice(jsonObj, user);
			if(notice == null) {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
		}
	}

	/** Method to delete a notice
	 * 
	 * @param id : id of notice
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<Void> with status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if user is not valid or user is not admin then return HttpStatus.UNAUTHORIZED
	 * 			else if the admin is deleting other company's notice then return HttpStatus.UNAUTHORIZED
	 * 			else delete the notice and return HttpStatus.OK
	 * @throws JSONException
	 */
	@RequestMapping(value = "/deleteNotice", method = RequestMethod.POST)
	public ResponseEntity<Void> deleteNotice(@RequestBody int id,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		int permissions = user.getPermissions();
		//Req
		if (!user.isValid() || permissions == 1 || permissions == 2) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		} else {
			Notice notice = noticeService.getNoticeById(id);
			if (!(user.getCompany().getCompanyId() == notice
					.getCompany().getCompanyId())) {
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
			} else {
				noticeService.deleteNotice(notice);
				return new ResponseEntity<Void>(HttpStatus.CREATED);
			}
		}
	}

	/** Method to archive a notice
	 *
	 * @param id : id of notice
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<Void> with status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if user is not valid or user is not admin then return HttpStatus.UNAUTHORIZED
	 * 			else if the admin is deleting other company's notice then return HttpStatus.UNAUTHORIZED
	 * 			else delete the notice and return HttpStatus.OK
	 */
	@RequestMapping(value = "/archiveNotice", method = RequestMethod.POST)
	public ResponseEntity<Void> archiveNotice(@RequestBody int id,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		int permissions = user.getPermissions();
		//Req
		if (!user.isValid() || permissions == 1 || permissions == 2) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		Notice notice = noticeService.getNoticeById(id);
		if (!(user.getCompany().getCompanyId() == notice
				.getCompany().getCompanyId())) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		} else {
			notice.setArchive(true);
			noticeService.updateNotice(notice);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
	}

	/** Method to delete a notice
	 * 
	 * @param id : id of notice to be unarchived
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<Void> with status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if user is not valid or user is not admin then return HttpStatus.UNAUTHORIZED
	 * 			else if the admin is deleting other company's notice then return HttpStatus.UNAUTHORIZED
	 * 			else delete the notice and return HttpStatus.OK
	 */
	@RequestMapping(value = "/unArchiveNotice", method = RequestMethod.POST)
	public ResponseEntity<Void> unArchiveNotice(@RequestBody int id,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		int permissions = user.getPermissions();
		//Req
		if (!user.isValid() || permissions == 1 || permissions == 2) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		} else {
			Notice notice = noticeService.getNoticeById(id);
			if (!(user.getCompany().getCompanyId() == notice
					.getCompany().getCompanyId())) {
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
			} else {
				notice.setArchive(false);
				noticeService.updateNotice(notice);
				return new ResponseEntity<Void>(HttpStatus.CREATED);
			}
		}
	}

	/** Method to get all notices of a particular User
	 * 
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<List<Notice>> with status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if noticeList is empty them return HttpStatus.NO_CONTENT
	 * 			else return noticeList with status HttpStatus.OK
	 */
	@RequestMapping(value = "/getAllNoticeByUser", method = RequestMethod.GET)
	public ResponseEntity<List<Notice>> getAllNoticeByUser(
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			return new ResponseEntity<List<Notice>>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		List<Notice> noticeList = noticeService.getAllNoticesByUser(user);
		if(noticeList.isEmpty()) {
			return new ResponseEntity<List<Notice>>(noticeList, HttpStatus.NO_CONTENT);	
		}
		return new ResponseEntity<List<Notice>>(noticeList, HttpStatus.OK);
	}

	/** Method to get all archived notices of a particular Company
	 * 
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<List<Notice>> with status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if noticeList is empty them return HttpStatus.NO_CONTENT
	 * 			else return noticeList with status HttpStatus.OK
	 */
	@RequestMapping(value = "/getAllArchivedNoticesByCompany", method = RequestMethod.GET)
	public ResponseEntity<List<Notice>> getAllArchievedNoticeByCompany(
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			return new ResponseEntity<List<Notice>>(HttpStatus.FORBIDDEN);
		}
		User user = (User) session.getAttribute("user");
		Company company = user.getCompany();
		List<Notice> noticeList = noticeService.getAllArchievedNoticesByCompany(company);
		if (noticeList.isEmpty()) {
			return new ResponseEntity<List<Notice>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Notice>>(noticeList, HttpStatus.OK);
	}

	/** Method to get all archived notices of a particular Company
	 * 
	 * @param tag : tag
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<List<Notice>> with status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if noticeList is empty them return HttpStatus.NO_CONTENT
	 * 			else return noticeList with status HttpStatus.OK
	 */
	@RequestMapping(value = "/getAllNoticeByTag/{tag}", method = RequestMethod.POST)
	public ResponseEntity<List<Notice>> getAllNoticeByTag (
			HttpServletRequest request, @PathVariable("tag") String tag) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			return new ResponseEntity<List<Notice>>(HttpStatus.FORBIDDEN);
		}
		Company company = ((User)session.getAttribute("user")).getCompany();
		List<Notice> noticeList = noticeService.getAllNoticesByTag(tag, company);
		if(noticeList.isEmpty()) {
			return new ResponseEntity<List<Notice>>(noticeList, HttpStatus.NO_CONTENT);	
		}
		return new ResponseEntity<List<Notice>>(noticeList, HttpStatus.OK);
	}

	/** Method to get all archived notices of a particular Company
	 * 
	 * @param title : title of notice
	 * @param request : object of HttpServletRequest
	 * @return ResponseEntity<List<Notice>> with status code
	 * 			if user is not logged in then return HttpStatus.FORBIDDEN
	 * 			else if noticeList is empty them return HttpStatus.NO_CONTENT
	 * 			else return noticeList with status HttpStatus.OK
	 */
	/*@RequestMapping(value = "/getAllNoticeByTitle/{title}", method = RequestMethod.POST)
	public ResponseEntity<List<Notice>> getAllNoticeByTitle (
			HttpServletRequest request, HttpServletResponse response, @PathVariable("title") String title) throws IOException{
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			return new ResponseEntity<List<Notice>>(HttpStatus.FORBIDDEN);
		}
		Company company = ((User)session.getAttribute("user")).getCompany();
		List<Notice> noticeList = noticeService.getAllNoticesByTitle(title, company);
		if(noticeList.isEmpty()) {
			return new ResponseEntity<List<Notice>>(noticeList, HttpStatus.NO_CONTENT);	
		}
		return new ResponseEntity<List<Notice>>(noticeList, HttpStatus.OK);
	}*/

}