package com.metacube.metice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.metacube.metice.Entity.Company;
import com.metacube.metice.service.CompanyService;
/**
 * @author Amit Natani
 * 
 *         Date created :
 *
 *         Description : This RestController is used for all the APIs related to
 *         Company management.
 */
@RestController
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	/** MEthod to get All companies List
	 * 
	 * @param request : Object of HttpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/getAllCompanies", method = RequestMethod.GET)	
	public ResponseEntity<List<Company>> getAllCompanies(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("googleUser") == null) {
			return new ResponseEntity<List<Company>>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<List<Company>>(companyService.getAllCompanies(), HttpStatus.OK);
	}
}
