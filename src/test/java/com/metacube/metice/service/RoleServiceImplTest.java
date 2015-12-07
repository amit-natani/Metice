package com.metacube.metice.service;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.metice.Entity.Role;
import com.metacube.metice.service.impl.RoleServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext-servlet.xml"})
public class RoleServiceImplTest {

	@Autowired
	private RoleService roleServiceImpl = new RoleServiceImpl();
	
	Role role = new Role();
	
	@Before
	public void setUp() throws Exception {
		role.setName("TSE");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreateRole() {
		roleServiceImpl.createRole(role);
		assertEquals(role, roleServiceImpl.getRoleByName("TSE"));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetRoleById() {
		roleServiceImpl.createRole(role);
		assertEquals(role, roleServiceImpl.getRoleById(role.getRoleId()));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetRoleByName() {
		roleServiceImpl.createRole(role);
		assertEquals(role, roleServiceImpl.getRoleByName("TSE"));
	}
	
	@After
	public void tearDown() throws Exception {
		role = null;
	}


}
